package com.proyecto.tiendaVirtual.notificacion.controller;

import com.proyecto.tiendaVirtual.notificacion.dto.NotificacionResponseDTO;
import com.proyecto.tiendaVirtual.notificacion.model.Notificacion;
import com.proyecto.tiendaVirtual.notificacion.repository.NotificacionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notificaciones")
@RequiredArgsConstructor
public class NotificacionController {
    private final NotificacionRepository notificacionRepository;
    @GetMapping("/{perfilId}")
    public List<NotificacionResponseDTO> listar(@PathVariable Long perfilId) {

        return notificacionRepository
                .findByPerfilIdOrderByFechaDesc(perfilId)
                .stream()
                .map(n -> NotificacionResponseDTO.builder()
                        .id(n.getId())
                        .mensaje(n.getMensaje())
                        .leida(n.getLeida())
                        .fecha(n.getFecha())
                        .build())
                .toList();
    }

    @PutMapping("/leer-todas/{perfilId}")
    public ResponseEntity<Void> marcarTodas(@PathVariable Long perfilId) {
        List<Notificacion> notificaciones =
                notificacionRepository.findByPerfilIdAndLeidaFalse(perfilId);

        for (Notificacion n : notificaciones) {
            n.setLeida(true);
        }
        return ResponseEntity.ok().build();
    }
}
