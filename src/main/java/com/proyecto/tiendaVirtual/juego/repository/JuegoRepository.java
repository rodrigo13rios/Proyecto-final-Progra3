package com.proyecto.tiendaVirtual.juego.repository;

import com.proyecto.tiendaVirtual.juego.model.Categoria;
import com.proyecto.tiendaVirtual.juego.model.Juego;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface JuegoRepository extends JpaRepository<Juego,Long> {
    Optional<Juego> findByNombre(String nombre);
    Page<Juego> findByCategoria(Categoria categoria, Pageable pageable);
}
