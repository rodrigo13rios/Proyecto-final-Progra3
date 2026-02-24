package com.proyecto.tiendaVirtual.estadistica.dto;

import java.time.LocalDate;
import java.util.List;

public record DesarrolladoraStatsDTO (
    long comprasTotales,
    double ingresosTotales,

    long comprasPeriodo,
    double ingresosPeriodo,

    List<SeriePointDTO> ventasPorDia,     // date + count
    List<SeriePointDTO> ingresosPorDia,   // date + amount

    double precioPromedio,
    double precioMediana,

    List<CategoriaCountDTO> distribucionCategorias,

    long compradoresUnicos,
    long compradoresRecurrentes,
    double tasaRetencionSimple
) {}

