package com.proyecto.tiendaVirtual.carrito.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarroDeComprasDTO {
    @NotBlank(message = "El nombre del juego no puede estar vacío")
    private String nombreJuego;
}
