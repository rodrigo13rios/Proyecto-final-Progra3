package com.proyecto.tiendaVirtual.carrito.repository;

import com.proyecto.tiendaVirtual.carrito.model.JuegoCarro;
import com.proyecto.tiendaVirtual.juego.model.Juego;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarritoRepository extends JpaRepository<JuegoCarro,Long> {
    List<JuegoCarro> getByUserId(Long userId);
    Optional<JuegoCarro> findByUserIdAndJuegoId(Long userId, Long juegoId);
    void deleteByUserId(Long userId);

}
