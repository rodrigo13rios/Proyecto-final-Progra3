package com.proyecto.tiendaVirtual.compraDetalle.controller;

import com.proyecto.tiendaVirtual.compraDetalle.dto.EstadisticasDesarrolladoraDTO;
import com.proyecto.tiendaVirtual.compraDetalle.dto.VentaJuegoDTO;
import com.proyecto.tiendaVirtual.compraDetalle.service.CompraDetalleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/estadisticas")
public class CompraDetalleController {
    @Autowired
    CompraDetalleService service;


    @GetMapping
    public ResponseEntity<EstadisticasDesarrolladoraDTO> obtenerEstadisticas() {
        return ResponseEntity.ok(service.obtenerEstadisticas());
    }

    @GetMapping("/mas-vendido")
    public ResponseEntity<VentaJuegoDTO> juegoMasVendido() {
        return service.juegoMasVendido()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    @GetMapping("/ventas")
    public ResponseEntity<List<VentaJuegoDTO>> ventasPorJuego() {
        return ResponseEntity.ok(service.ventasPorJuegoDeDesarrolladora());
    }

    @GetMapping("/ganancias")
    public ResponseEntity<Double> gananciasTotales() {
        return ResponseEntity.ok(service.gananciasTotalesHistoricasDesarrolladora());
    }
}
