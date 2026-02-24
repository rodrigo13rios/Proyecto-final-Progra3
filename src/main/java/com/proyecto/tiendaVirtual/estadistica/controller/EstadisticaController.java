package com.proyecto.tiendaVirtual.estadistica.controller;

import com.proyecto.tiendaVirtual.estadistica.dto.DesarrolladoraStatsDTO;
import com.proyecto.tiendaVirtual.estadistica.service.EstadisticaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/estadisticas")
public class EstadisticaController {
    @Autowired
    private EstadisticaService statsService;

    @GetMapping
    public DesarrolladoraStatsDTO getStats(@RequestParam(defaultValue = "30") int days) {
        if (days < 1) days = 30;
        if (days > 365) days = 365;
        return statsService.getDashboard(days);
    }
}
