package com.proyecto.tiendaVirtual.compra.controller;

import com.proyecto.tiendaVirtual.compra.model.DetalleCompra;
import com.proyecto.tiendaVirtual.compra.service.detallecompra.DetalleCompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DetalleCompraController {
    @Autowired
    DetalleCompraService service;

    @GetMapping("/{id_compra}")
    public ResponseEntity<List<DetalleCompra>>getDetalleCompra(@PathVariable Long idCompra){
        List<DetalleCompra>compraList=service.getByCompra(idCompra);
        return new ResponseEntity<>(compraList, HttpStatus.OK);
    }
}
