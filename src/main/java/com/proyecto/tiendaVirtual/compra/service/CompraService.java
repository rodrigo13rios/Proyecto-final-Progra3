package com.proyecto.tiendaVirtual.compra.service;

import com.proyecto.tiendaVirtual.compra.dto.PedidoCompra;
import com.proyecto.tiendaVirtual.juego.dto.JuegoVerDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CompraService {
    void comprar(List<Long> juegosIds);
    List<JuegoVerDTO> preview(List<Long> gamesIds);
}
