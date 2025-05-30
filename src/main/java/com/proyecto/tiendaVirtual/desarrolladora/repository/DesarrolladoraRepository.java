package com.proyecto.tiendaVirtual.desarrolladora.repository;

import com.proyecto.tiendaVirtual.desarrolladora.model.Desarrolladora;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DesarrolladoraRepository extends JpaRepository<Desarrolladora, Long> {
    Optional<Desarrolladora> findByNombre(String nombre);
}

