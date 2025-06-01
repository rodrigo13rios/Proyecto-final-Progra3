package com.proyecto.tiendaVirtual.juego.service;

import com.proyecto.tiendaVirtual.exceptions.ElementoYaExistenteException;
import com.proyecto.tiendaVirtual.exceptions.ElementoNoEncontradoException;
import com.proyecto.tiendaVirtual.juego.model.Categoria;
import com.proyecto.tiendaVirtual.juego.model.Juego;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public interface JuegoService {
    void create(Juego juego) throws ElementoYaExistenteException;
    Optional<Juego> findById(Long id) throws ElementoNoEncontradoException;
    Optional<Juego> findByName(String nombre) throws ElementoNoEncontradoException;
    List<Juego> getByCategoria(Categoria categoria);
    List<Juego> getAll();
    void update(Long id, Juego updateJuego) throws ElementoNoEncontradoException;
    void delete(Long id) throws ElementoNoEncontradoException;
}
