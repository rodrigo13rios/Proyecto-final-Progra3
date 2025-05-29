package com.proyecto.tiendaVirtual.juego.repository;

import com.proyecto.tiendaVirtual.juego.model.Categoria;
import com.proyecto.tiendaVirtual.juego.model.Juego;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface JuegoRepository extends JpaRepository<Juego,Long> {
    Optional<Juego> findByName(String nombre);
    List<Juego> getByCategoria(Categoria categoria);
}
