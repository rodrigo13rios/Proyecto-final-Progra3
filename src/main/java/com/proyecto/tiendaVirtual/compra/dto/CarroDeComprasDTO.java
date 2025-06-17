package com.proyecto.tiendaVirtual.compra.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarroDeComprasDTO {

    @NotBlank(message = "El nombre del juego no puede estar vacío")
    private String nombreJuego;

    @Min(value = 0, message = "La cantidad unitaria no puede ser menor a 0")
    @Max(value = 999999999, message = "El valor no puede ser mayor a 999999999")
    @NotNull(message = "La cantidad no puede estar vacía")
    private Integer cantidad;
}
