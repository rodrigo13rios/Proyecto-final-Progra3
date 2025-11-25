package com.proyecto.tiendaVirtual.carrito.service;

import com.proyecto.tiendaVirtual.juego.dto.JuegoVerDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CarritoService {
    List<JuegoVerDTO> agregarJuego(Long juegoId);
    List<JuegoVerDTO> borrarJuego(Long juegoId);
    List<JuegoVerDTO> getAll();
    void limpiar();
    void comprar();
}
