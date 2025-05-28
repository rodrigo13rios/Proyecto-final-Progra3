package com.proyecto.tiendaVirtual.perfil.repository;

import com.proyecto.tiendaVirtual.perfil.model.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PerfilRepository extends JpaRepository<Perfil,Long> {
}
