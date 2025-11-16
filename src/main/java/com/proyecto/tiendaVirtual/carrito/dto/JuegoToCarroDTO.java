package com.proyecto.tiendaVirtual.carrito.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JuegoToCarroDTO {
    private String nombre;
    private Double precio;
    private String foto;
}
