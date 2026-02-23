package com.proyecto.tiendaVirtual.descuento.repository;

import com.proyecto.tiendaVirtual.descuento.model.Descuento;
import com.proyecto.tiendaVirtual.juego.model.Juego;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@Repository
public interface DescuentoRepository extends JpaRepository<Descuento,Long> {
    @Query("""
        SELECT COALESCE(SUM(d.porcentaje), 0)
        FROM Descuento d
        WHERE d.juego.id = :juegoId
        AND :now BETWEEN d.fechaInicio AND d.fechaFin
    """)
    int getDescuentoTotalActivo(Long juegoId, LocalDateTime now);
}
