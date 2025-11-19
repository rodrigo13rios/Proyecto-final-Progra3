package com.proyecto.tiendaVirtual.compraDetalle.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JuegoResumenDTO {
    private Long id;
    private String nombre;
    private String foto;
    private Double precio;
}
