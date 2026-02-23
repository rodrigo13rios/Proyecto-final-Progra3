package com.proyecto.tiendaVirtual.descuento.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
public class DescuentoResponseDTO {
    private Long id;
    private Integer porcentaje;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private Boolean activo;
    private Long juegoId;
}
