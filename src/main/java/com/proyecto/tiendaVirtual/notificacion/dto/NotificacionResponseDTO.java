package com.proyecto.tiendaVirtual.notificacion.dto;

import com.proyecto.tiendaVirtual.notificacion.model.Notificacion;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificacionResponseDTO {

    private Long id;
    private String mensaje;
    private Boolean leida;
    private LocalDateTime fecha;

    public NotificacionResponseDTO(Notificacion n) {
        this.id = n.getId();
        this.mensaje = n.getMensaje();
        this.leida = n.getLeida();
        this.fecha = n.getFecha();
    }
}