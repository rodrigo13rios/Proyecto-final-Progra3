package com.proyecto.tiendaVirtual.compraDetalle.service;

import com.proyecto.tiendaVirtual.carrito.model.CarroDeCompras;
import com.proyecto.tiendaVirtual.compra.model.Compra;
import com.proyecto.tiendaVirtual.compraDetalle.dto.EstadisticasDesarrolladoraDTO;
import com.proyecto.tiendaVirtual.compraDetalle.dto.VentaJuegoDTO;
import com.proyecto.tiendaVirtual.desarrolladora.model.Desarrolladora;
import com.proyecto.tiendaVirtual.juego.model.Juego;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CompraDetalleService {
    void createDetalles(Compra compra, CarroDeCompras carroDeCompras);

    List<VentaJuegoDTO> ventasPorJuegoDeDesarrolladora();

    Optional<VentaJuegoDTO> juegoMasVendido();

    double gananciasTotalesHistoricasDesarrolladora();

    EstadisticasDesarrolladoraDTO obtenerEstadisticas();

}
