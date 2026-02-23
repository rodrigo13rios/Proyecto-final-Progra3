package com.proyecto.tiendaVirtual.juego.service;

import com.proyecto.tiendaVirtual.juego.dto.JuegoDTO;
import com.proyecto.tiendaVirtual.juego.dto.JuegoUpdateDTO;
import com.proyecto.tiendaVirtual.juego.dto.JuegoVerDTO;
import com.proyecto.tiendaVirtual.juego.model.Juego;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
@Service
public interface JuegoService {
    Juego create(JuegoDTO juegoDTO);
    Juego update(Long id, JuegoUpdateDTO dto);
    void delete(Long id);
    Optional<Juego> getById(Long id);
    Optional<JuegoVerDTO> getByNombre(String nombre);
    Page<JuegoVerDTO> getAll(String strCategoria, String search, Pageable pageable);
    JuegoVerDTO convertirAVerDTO (Juego juego);
    Double obtenerPrecioFinal(Juego juego);
}
