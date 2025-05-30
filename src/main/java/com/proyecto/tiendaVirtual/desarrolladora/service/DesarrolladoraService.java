package com.proyecto.tiendaVirtual.desarrolladora.service;

import com.proyecto.tiendaVirtual.desarrolladora.model.Desarrolladora;
import com.proyecto.tiendaVirtual.exceptions.ElementoExistenteException;
import com.proyecto.tiendaVirtual.exceptions.ElementoNoEncontradoException;
import com.proyecto.tiendaVirtual.exceptions.ListaVaciaException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface DesarrolladoraService {
    void createDesarrolladora(Desarrolladora desarrolladora) throws ElementoExistenteException;
    Optional<Desarrolladora> findDesarrolladoraById(Long id) throws ElementoNoEncontradoException;
    Optional<Desarrolladora> findByNombre(String nombre) throws ElementoNoEncontradoException;
    List<Desarrolladora> getAllDesarrolladoras() throws ListaVaciaException;
    void updateDesarrolladora(Long id, Desarrolladora updatedDesarrolladora) throws ElementoNoEncontradoException;
    void deleteDesarrolladora(Long id) throws ElementoNoEncontradoException;
}
