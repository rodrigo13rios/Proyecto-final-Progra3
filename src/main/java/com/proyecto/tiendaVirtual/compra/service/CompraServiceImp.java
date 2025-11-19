package com.proyecto.tiendaVirtual.compra.service;

import com.proyecto.tiendaVirtual.billetera.service.BilleteraService;
import com.proyecto.tiendaVirtual.carrito.model.CarroDeCompras;
import com.proyecto.tiendaVirtual.carrito.service.CarroDeComprasService;
import com.proyecto.tiendaVirtual.compra.dto.CompraDTO;
import com.proyecto.tiendaVirtual.compra.model.Compra;
import com.proyecto.tiendaVirtual.compra.repository.CompraRepository;
import com.proyecto.tiendaVirtual.compraDetalle.model.CompraDetalle;
import com.proyecto.tiendaVirtual.compraDetalle.repository.CompraDetalleRepository;
import com.proyecto.tiendaVirtual.compraDetalle.service.CompraDetalleService;
import com.proyecto.tiendaVirtual.exceptions.ElementoYaExistenteException;
import com.proyecto.tiendaVirtual.exceptions.NumeroInvalidoException;
import com.proyecto.tiendaVirtual.juego.model.Juego;
import com.proyecto.tiendaVirtual.user.model.User;
import com.proyecto.tiendaVirtual.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class CompraServiceImp implements CompraService{
    @Autowired
    CompraRepository repo;
    @Autowired
    CarroDeComprasService carroService;
    @Autowired
    BilleteraService billeteraService;
    @Autowired
    SecurityUtils securityUtils;
    @Autowired
    CompraDetalleService compraDetalleService;


    @Transactional
    public void createCompra() {
        //obtiene el usuario loggeado
        User cliente=securityUtils.getLoggedUser();

        //obtiene el carro de compras del cliente
        CarroDeCompras compras= carroService.getCarroByCliente();

        //metodo para formatear
        DecimalFormat decimalFormat=new DecimalFormat("0.00",new DecimalFormatSymbols(Locale.US));// Formatea para no pasarse de decimales. LocalUs es para no generar problema con el "."
        decimalFormat.setRoundingMode(RoundingMode.HALF_UP);

        //obtiene el costo total del carro
        double total = compras.getJuegos().stream().mapToDouble(item-> item.getPrecio()).sum();

        // obtiene el saldo actual del cliente
        double saldoActual = billeteraService.consultarSaldo();
        //Verifica que el saldo sea suficiente
        if (total>saldoActual){
            throw new NumeroInvalidoException("No cuenta con saldo suficiente para realizar la compra");
        }
        //Descuenta el saldo de la billetera
        billeteraService.restarSaldo(Double.parseDouble(decimalFormat.format(total)));

        //Genera la compra
        Compra compra= new Compra(Double.parseDouble(decimalFormat.format(total)), LocalDate.now(),cliente);

        //Guarda la compra
        this.repo.save(compra);

        compraDetalleService.createDetalles(compra,compras);

        //Asigna los juegos al cliente
        List<Juego> juegosComprados = new ArrayList<>(compras.getJuegos());
        cliente.getPerfil().setJuegos(juegosComprados);
    }


    public List<CompraDTO> getCompraByCliente() {
        User cliente= securityUtils.getLoggedUser();
        return this.repo.findByCliente_Email(cliente.getEmail()).stream().map(this::conventirACompraDTO).toList();
    }

    public List<CompraDTO> getCompraByCliente_Id(Long id){
        return this.repo.findByCliente_Id(id).stream().map(this::conventirACompraDTO).toList();
    }

    @Override
    public CompraDTO conventirACompraDTO(Compra compra) {
        CompraDTO dto = new CompraDTO();
        dto.setNombreCliente(compra.getCliente().getNombre());
        dto.setApellidoCliente(compra.getCliente().getApellido());
        dto.setFechaDeCompra(compra.getFecha());
        dto.setTotal(compra.getTotal());

        return dto;
    }

    @Override
    public List<CompraDTO> getAll() {
        return repo.findAll().stream().map(this::conventirACompraDTO).toList();
    }


}
