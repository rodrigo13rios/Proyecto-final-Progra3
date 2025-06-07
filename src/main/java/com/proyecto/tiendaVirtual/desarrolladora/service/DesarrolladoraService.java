package com.proyecto.tiendaVirtual.desarrolladora.service;

import com.proyecto.tiendaVirtual.desarrolladora.model.Desarrolladora;
import com.proyecto.tiendaVirtual.exceptions.ElementoYaExistenteException;
import com.proyecto.tiendaVirtual.exceptions.ElementoNoEncontradoException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface DesarrolladoraService {
    void create(Desarrolladora desarrolladora) throws ElementoYaExistenteException;
    Optional<Desarrolladora> findById(Long id) throws ElementoNoEncontradoException;
    Optional<Desarrolladora> findByNombre(String nombre) throws ElementoNoEncontradoException;
    List<Desarrolladora> getAll();
    void update(Long id, Desarrolladora updatedDesarrolladora) throws ElementoNoEncontradoException;
    void delete(Long id) throws ElementoNoEncontradoException;
}
