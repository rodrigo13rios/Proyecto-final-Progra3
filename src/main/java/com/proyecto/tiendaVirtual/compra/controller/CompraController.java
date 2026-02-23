package com.proyecto.tiendaVirtual.compra.controller;

import com.proyecto.tiendaVirtual.compra.dto.PedidoCompra;
import com.proyecto.tiendaVirtual.compra.service.CompraService;
import com.proyecto.tiendaVirtual.juego.dto.JuegoVerDTO;
import com.proyecto.tiendaVirtual.juego.model.Juego;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/compra")
public class CompraController {
    @Autowired
    CompraService service;

    @PostMapping
    public ResponseEntity<Void> comprar(@RequestBody PedidoCompra pedidoCompra) {
        service.comprar(pedidoCompra.getGameIds());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/preview")
    public ResponseEntity<List<JuegoVerDTO>> preview(@RequestBody List<Long> ids) {
        List<JuegoVerDTO> preview = service.preview(ids);
        return ResponseEntity.ok(preview);
    }
}
