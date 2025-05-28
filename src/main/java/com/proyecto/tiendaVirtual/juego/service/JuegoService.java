package com.proyecto.tiendaVirtual.juego.service;

import com.proyecto.tiendaVirtual.exceptions.ElementoExistenteException;
import com.proyecto.tiendaVirtual.exceptions.ElementoNoEncontradoException;
import com.proyecto.tiendaVirtual.exceptions.ListaVaciaException;
import com.proyecto.tiendaVirtual.juego.model.Categoria;
import com.proyecto.tiendaVirtual.juego.model.Juego;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public interface JuegoService {
    void createJuego(Juego juego) throws ElementoExistenteException;
    Optional<Juego> findJuegoById(Long id) throws ElementoNoEncontradoException;
    Optional<Juego> findByName(String nombre) throws ListaVaciaException;
    List<Juego> getByCategoria(Categoria categoria) throws ListaVaciaException;
    List<Juego> getAllJuegos() throws ListaVaciaException;
    void updateJuego(Long id, Juego updateJuego) throws ElementoNoEncontradoException;
    void deleteJuego(Long id) throws ElementoNoEncontradoException;
}
