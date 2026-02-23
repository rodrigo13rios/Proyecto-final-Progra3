package com.proyecto.tiendaVirtual.notificacion.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class NotificacionResponseDTO {

    private Long id;
    private String mensaje;
    private Boolean leida;
    private LocalDateTime fecha;
}