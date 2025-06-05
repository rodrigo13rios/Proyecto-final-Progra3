package com.proyecto.tiendaVirtual.desarrolladora.service;

import com.proyecto.tiendaVirtual.desarrolladora.model.Desarrolladora;
import com.proyecto.tiendaVirtual.exceptions.ElementoYaExistenteException;
import com.proyecto.tiendaVirtual.exceptions.ElementoNoEncontradoException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface DesarrolladoraService {
    void create(Desarrolladora desarrolladora);
    Optional<Desarrolladora> findById(Long id);
    Optional<Desarrolladora> findByNombre(String nombre);
    List<Desarrolladora> getAll();
    void update(Long id, Desarrolladora updatedDesarrolladora);
    void delete(Long id) throws ElementoNoEncontradoException;
}
