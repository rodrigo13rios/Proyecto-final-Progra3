package com.proyecto.tiendaVirtual.compraDetalle.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VentaJuegoDTO {
    private Long juegoId;
    private String juegoNombre;
    private Long cantidadVendida;
}
