package com.proyecto.tiendaVirtual.compra.controller;

import com.proyecto.tiendaVirtual.compra.dto.CompraDTO;
import com.proyecto.tiendaVirtual.compra.model.Compra;
import com.proyecto.tiendaVirtual.compra.service.CompraService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/compra")
public class CompraController {
    @Autowired
    CompraService service;

    @PostMapping
    public ResponseEntity<Void>crearCompra(){
        this.service.createCompra();
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<CompraDTO>> getByClienteLogged(){
        List<CompraDTO>compras= this.service.getCompraByCliente();
        return ResponseEntity.ok(compras);
    }

    //ToDo: Devuelve una lista vacia, Si lo utilizamos habria que corregirlo.
//    @GetMapping("/{id}")
//    public ResponseEntity<List<CompraDTO>> getByClienteId(@PathVariable Long id){
//        List<CompraDTO>compras= this.service.getCompraByCliente_Id(id);
//        return ResponseEntity.ok(compras);
//    }

    @GetMapping("/get")
    public ResponseEntity<List<CompraDTO>> getAll(){
        List<CompraDTO>compras=this.service.getAll();
        return ResponseEntity.ok(compras);
    }

}
