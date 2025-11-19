package com.proyecto.tiendaVirtual.compraDetalle.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EstadisticasDesarrolladoraDTO {
    private VentaJuegoDTO juegoMasVendido;
    private double gananciasTotales;
    private List<VentaJuegoDTO> ventasPorJuego;
}
