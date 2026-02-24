package com.proyecto.tiendaVirtual.notificacion.controller;

import com.proyecto.tiendaVirtual.notificacion.dto.NotificacionResponseDTO;
import com.proyecto.tiendaVirtual.notificacion.model.Notificacion;
import com.proyecto.tiendaVirtual.notificacion.repository.NotificacionRepository;
import com.proyecto.tiendaVirtual.notificacion.service.NotificacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notificaciones")
@RequiredArgsConstructor
public class NotificacionController {
    @Autowired
    private NotificacionService notificacionService;

    @GetMapping
    public List<NotificacionResponseDTO> listar() {
        return notificacionService.listar();
    }

    @PutMapping("/leer-todas")
    public ResponseEntity<Void> marcarTodas() {
        notificacionService.leerTodas();
        return ResponseEntity.ok().build();
    }
}
