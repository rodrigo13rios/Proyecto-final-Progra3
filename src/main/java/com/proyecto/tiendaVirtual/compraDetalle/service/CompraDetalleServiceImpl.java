package com.proyecto.tiendaVirtual.compraDetalle.service;

import com.proyecto.tiendaVirtual.carrito.model.CarroDeCompras;
import com.proyecto.tiendaVirtual.compra.model.Compra;
import com.proyecto.tiendaVirtual.compraDetalle.dto.EstadisticasDesarrolladoraDTO;
import com.proyecto.tiendaVirtual.compraDetalle.dto.JuegoResumenDTO;
import com.proyecto.tiendaVirtual.compraDetalle.dto.VentaJuegoDTO;
import com.proyecto.tiendaVirtual.compraDetalle.model.CompraDetalle;
import com.proyecto.tiendaVirtual.compraDetalle.repository.CompraDetalleRepository;
import com.proyecto.tiendaVirtual.desarrolladora.model.Desarrolladora;
import com.proyecto.tiendaVirtual.desarrolladora.service.DesarrolladoraService;
import com.proyecto.tiendaVirtual.exceptions.ElementoNoEncontradoException;
import com.proyecto.tiendaVirtual.juego.model.Juego;
import com.proyecto.tiendaVirtual.user.model.User;
import com.proyecto.tiendaVirtual.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CompraDetalleServiceImpl implements CompraDetalleService{
    @Autowired
    CompraDetalleRepository repo;
    @Autowired
    DesarrolladoraService desarrolladoraService;
    @Autowired
    SecurityUtils securityUtils;

    @Override
    public void createDetalles(Compra compra, CarroDeCompras carroDeCompras) {

        List<CompraDetalle> detalles = carroDeCompras.getJuegos().stream()
                .map(juego -> new CompraDetalle(compra, juego, juego.getPrecio()))
                .toList();

        repo.saveAll(detalles);
    }



    private Desarrolladora getDesarrolladoraLogueada() {
        User user = securityUtils.getLoggedUser();


        return desarrolladoraService.findById(user.getDesarrolladora().getId())
                .orElseThrow(() -> new ElementoNoEncontradoException("Desarrolladora no encontrada para el usuario logueado"));
    }
    @Override
    public List<VentaJuegoDTO> ventasPorJuegoDeDesarrolladora() {

        Desarrolladora desarrolladora = securityUtils.getLoggedUser().getDesarrolladora();

        List<CompraDetalle> todos = repo.findAll();

        Map<Juego, Long> agrupado = todos.stream()
                .filter(d -> d.getJuego() != null && d.getJuego().getDesarrolladora() != null)
                .filter(d -> Objects.equals(
                        d.getJuego().getDesarrolladora().getId(),
                        desarrolladora.getId()
                ))
                .collect(Collectors.groupingBy(CompraDetalle::getJuego, Collectors.counting()));

        return agrupado.entrySet().stream()
                .map(e -> new VentaJuegoDTO(
                        e.getKey().getId(),
                        e.getKey().getNombre(),
                        e.getValue()
                ))
                .collect(Collectors.toList());
    }

    // ---------------------------------------------------------
    // JUEGO MÁS VENDIDO
    // ---------------------------------------------------------
    @Override
    public Optional<VentaJuegoDTO> juegoMasVendido() {
        return ventasPorJuegoDeDesarrolladora().stream()
                .max(Comparator.comparingLong(VentaJuegoDTO::getCantidadVendida));
    }

    // ---------------------------------------------------------
    // GANANCIAS HISTÓRICAS
    // ---------------------------------------------------------
    @Override
    public double gananciasTotalesHistoricasDesarrolladora() {

        Desarrolladora desarrolladora = securityUtils.getLoggedUser().getDesarrolladora();

        return repo.findAll().stream()
                .filter(d -> d.getJuego() != null && d.getJuego().getDesarrolladora() != null)
                .filter(d -> Objects.equals(
                        d.getJuego().getDesarrolladora().getId(),
                        desarrolladora.getId()
                ))
                .mapToDouble(CompraDetalle::getTotal)
                .sum();
    }

    // ---------------------------------------------------------
    // DTO FINAL
    // ---------------------------------------------------------
    @Override
    public EstadisticasDesarrolladoraDTO obtenerEstadisticas() {

        List<VentaJuegoDTO> ventas = ventasPorJuegoDeDesarrolladora();
        Optional<VentaJuegoDTO> masVendido = juegoMasVendido();
        double ganancias = gananciasTotalesHistoricasDesarrolladora();

        EstadisticasDesarrolladoraDTO dto = new EstadisticasDesarrolladoraDTO();
        dto.setJuegoMasVendido(masVendido.orElse(null));
        dto.setGananciasTotales(ganancias);
        dto.setVentasPorJuego(ventas);

        return dto;
    }
}
