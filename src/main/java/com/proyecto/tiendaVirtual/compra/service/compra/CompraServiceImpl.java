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
import com.proyecto.tiendaVirtual.user.model.User;
import com.proyecto.tiendaVirtual.user.service.UserService;
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
    UserService userService;
    @Autowired
    CarroDeCompraService carroService;
    @Autowired
    DetalleCompraService detalleService;
    @Autowired
    BilleteraService billeteraService;

    public List<Compra> getCompraByCliente(String email){
        return this.compraRepository.findByCliente_Email(email);
    }

    @Transactional
    public void createCompra(String email)throws ElementoNoEncontradoException{
        Optional<User> clienteOptional=userService.getByEmail(email);
        if (clienteOptional.isEmpty()){
            throw new ElementoNoEncontradoException("No se encontro un perfil con el email:"+email);
        }
        User cliente=clienteOptional.get();
        List<CarroDeCompras>carroDeComprasList=this.carroService.getCarroByCliente_Email(cliente.getEmail());

        DecimalFormat decimalFormat=new DecimalFormat("0.00",new DecimalFormatSymbols(Locale.US));// Formatea para no pasarse de decimales. LocalUs es para no generar problema con el "."
        decimalFormat.setRoundingMode(RoundingMode.HALF_UP);

        double total=carroDeComprasList.stream().mapToDouble(item->item.getJuego().getPrecio()* item.getCantidad()).sum();
        double saldoActual = billeteraService.consultarSaldo(cliente.getId());
        if (saldoActual < total) {
            throw new NumeroInvalidoException("No cuenta con el saldo suficiente para realizar la compra (Saldo actual: "
                    + saldoActual + ", Total requerido: " + total + ").");
        }

        Compra compra=new Compra(Double.parseDouble(decimalFormat.format(total)), LocalDate.now(),cliente);

        Perfil perfil=cliente.getPerfil();
        if (perfil == null) {
            throw new ElementoNoEncontradoException("El cliente no tiene perfil asociado.");
        }
        billeteraService.descontarSaldo(total, perfil.getId());
        Compra compraSave=this.compraRepository.save(compra);

        for (CarroDeCompras carroDeCompras : carroDeComprasList) {
            if (carroDeCompras.getJuego() == null) {
                throw new ElementoNoEncontradoException("Carrito con juego nulo. ID Carrito: " + carroDeCompras.getId());
            }
            DetalleCompra detalle = new DetalleCompra();
            detalle.setJuego(carroDeCompras.getJuego());
            detalle.setCantidad(carroDeCompras.getCantidad());
            detalle.setCompra(compraSave);
            this.detalleService.crear(detalle);
        }
        this.carroService.clearCarroDeComprasByCliente_Id(cliente.getId());
    }


    }

