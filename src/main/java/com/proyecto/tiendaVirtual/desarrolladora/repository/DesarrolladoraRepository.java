package com.proyecto.tiendaVirtual.desarrolladora.repository;

import com.proyecto.tiendaVirtual.desarrolladora.model.Desarrolladora;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DesarrolladoraRepository extends JpaRepository<Desarrolladora,Long> {
}
