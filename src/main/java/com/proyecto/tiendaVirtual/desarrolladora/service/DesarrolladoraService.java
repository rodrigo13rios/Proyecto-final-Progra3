package com.proyecto.tiendaVirtual.desarrolladora.service;

import com.proyecto.tiendaVirtual.desarrolladora.model.Desarrolladora;
import com.proyecto.tiendaVirtual.exceptions.ElementoYaExistenteException;
import com.proyecto.tiendaVirtual.exceptions.ElementoNoEncontradoException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface DesarrolladoraService {
    Desarrolladora create(Desarrolladora desarrolladora) throws ElementoYaExistenteException;
    Optional<Desarrolladora> findById(Long id);
    Optional<Desarrolladora> findByNombre(String nombre);
    List<Desarrolladora> getAll();
    Desarrolladora update(Desarrolladora updatedDesarrolladora);
}
