package com.proyecto.tiendaVirtual.descuento.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class CreateDescuentoDTO {
    @NotNull
    @Min(5)
    @Max(100)
    private Integer porcentaje;

    @NotNull
    @FutureOrPresent
    private LocalDateTime fechaInicio;

    @NotNull
    @FutureOrPresent
    private LocalDateTime fechaFin;

    @NotNull
    private Long juegoId;
}
