package com.proyecto.tiendaVirtual.juego.service;

import com.proyecto.tiendaVirtual.juego.dto.JuegoDTO;
import com.proyecto.tiendaVirtual.juego.dto.JuegoUpdateDTO;
import com.proyecto.tiendaVirtual.juego.model.Categoria;
import com.proyecto.tiendaVirtual.juego.model.Juego;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public interface JuegoService {
    Juego create(JuegoDTO juegoDTO);
    Juego update(Long id, JuegoUpdateDTO dto);
    void delete(Long id);
    Optional<Juego> getById(Long id);
    Optional<Juego> getByNombre(String nombre);
    List<Juego> getByCategoria(String categoria);
    List<Juego> getAll();
}
