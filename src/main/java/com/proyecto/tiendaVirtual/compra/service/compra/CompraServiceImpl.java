package com.proyecto.tiendaVirtual.compra.service.compra;

import com.proyecto.tiendaVirtual.billetera.service.BilleteraService;
import com.proyecto.tiendaVirtual.compra.model.CarroDeCompras;
import com.proyecto.tiendaVirtual.compra.model.Compra;
import com.proyecto.tiendaVirtual.compra.model.DetalleCompra;
import com.proyecto.tiendaVirtual.compra.repository.CompraRepository;
import com.proyecto.tiendaVirtual.compra.service.carritodecompra.CarroDeCompraService;
import com.proyecto.tiendaVirtual.compra.service.detallecompra.DetalleCompraService;
import com.proyecto.tiendaVirtual.exceptions.ElementoNoEncontradoException;
import com.proyecto.tiendaVirtual.exceptions.NumeroInvalidoException;
import com.proyecto.tiendaVirtual.perfil.model.Perfil;
import com.proyecto.tiendaVirtual.perfil.service.PerfilService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class CompraServiceImpl implements CompraService{
    @Autowired
    CompraRepository compraRepository;
    @Autowired
    PerfilService perfilService;
    @Autowired
    CarroDeCompraService carroService;
    @Autowired
    DetalleCompraService detalleService;
    @Autowired
    BilleteraService billeteraService;

    public List<Compra> getCompraByCliente(String nickName){
        return this.compraRepository.findByCliente_nickName(nickName);
    }

    @Transactional
    public void createCompra(String nickName)throws ElementoNoEncontradoException{
        Optional<Perfil> clienteOptional= this.perfilService.getByNickName(nickName);
        if (clienteOptional.isEmpty()){
            throw new ElementoNoEncontradoException("No se encontro un perfil con el nickname:"+nickName);
        }
        Perfil cliente=clienteOptional.get();
        List<CarroDeCompras>carroDeComprasList=this.carroService.getCarroByCliente_nickName(cliente.getNickName());

        DecimalFormat decimalFormat=new DecimalFormat("0.00",new DecimalFormatSymbols(Locale.US));// Formatea para no pasarse de decimales. LocalUs es para no generar problema con el "."
        decimalFormat.setRoundingMode(RoundingMode.HALF_UP);

        double total=carroDeComprasList.stream().mapToDouble(item->item.getJuego().getPrecio()* item.getCantidad()).sum();
        if (billeteraService.consultarSaldo(cliente.getId())<total){
            throw new NumeroInvalidoException("No cuenta con el saldo suficiente para realizar la compra (Saldo actual: "
                    + billeteraService.consultarSaldo(cliente.getId()) + ", Total requerido: "
                    + total + ").");
        }

        Compra compra=new Compra(Double.parseDouble(decimalFormat.format(total)), LocalDate.now(),cliente);

        billeteraService.descontarSaldo(total,cliente.getId());
        Compra compraSave=this.compraRepository.save(compra);

        for (CarroDeCompras carroDeCompras : carroDeComprasList) {
            DetalleCompra detalle = new DetalleCompra();
            detalle.setJuego(carroDeCompras.getJuego());
            detalle.setCantidad(carroDeCompras.getCantidad());
            detalle.setCompra(compraSave);
            this.detalleService.crear(detalle);
        }
        this.carroService.clearCarroDeComprasByCliente_Id(cliente.getId());
    }


    }

