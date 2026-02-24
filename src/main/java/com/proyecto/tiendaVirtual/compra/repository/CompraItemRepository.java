package com.proyecto.tiendaVirtual.compra.repository;

import com.proyecto.tiendaVirtual.compra.model.CompraItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface CompraItemRepository extends JpaRepository<CompraItem, Long> {
    @Query("""
      SELECT COUNT(i)
      FROM CompraItem i
      WHERE i.juego.desarrolladora.id = :devId
    """)
    long ventasTotalesDev(Long devId);

    @Query("""
      SELECT COALESCE(SUM(i.precioPagado), 0)
      FROM CompraItem i
      WHERE i.juego.desarrolladora.id = :devId
    """)
    double ingresosTotalesDev(Long devId);

    @Query("""
      SELECT COUNT(i)
      FROM CompraItem i
      WHERE i.juego.desarrolladora.id = :devId
        AND i.compra.fecha >= :from AND i.compra.fecha < :to
    """)
    long ventasPeriodoDev(Long devId, LocalDateTime from, LocalDateTime to);

    @Query("""
      SELECT COALESCE(SUM(i.precioPagado), 0)
      FROM CompraItem i
      WHERE i.juego.desarrolladora.id = :devId
        AND i.compra.fecha >= :from AND i.compra.fecha < :to
    """)
    double ingresosPeriodoDev(Long devId, LocalDateTime from, LocalDateTime to);

    // MySQL: series por día
    @Query(value = """
      SELECT DATE(c.fecha) AS dia, COUNT(i.id) AS ventas
      FROM compras c
      JOIN compra_items i ON i.compra_id = c.id
      JOIN juegos j ON j.id = i.juego_id
      WHERE j.id_desarrolladora = :devId
        AND c.fecha >= :from AND c.fecha < :to
      GROUP BY DATE(c.fecha)
      ORDER BY dia
    """, nativeQuery = true)
    List<Object[]> ventasPorDia(Long devId, LocalDateTime from, LocalDateTime to);

    @Query(value = """
      SELECT DATE(c.fecha) AS dia, COALESCE(SUM(i.precio_pagado), 0) AS ingresos
      FROM compras c
      JOIN compra_items i ON i.compra_id = c.id
      JOIN juegos j ON j.id = i.juego_id
      WHERE j.id_desarrolladora = :devId
        AND c.fecha >= :from AND c.fecha < :to
      GROUP BY DATE(c.fecha)
      ORDER BY dia
    """, nativeQuery = true)
    List<Object[]> ingresosPorDia(Long devId, LocalDateTime from, LocalDateTime to);

    @Query("""
      SELECT COUNT(DISTINCT i.compra.user.id)
      FROM CompraItem i
      WHERE i.juego.desarrolladora.id = :devId
    """)
    long compradoresUnicosDev(Long devId);

    @Query(value = """
      SELECT COUNT(*) FROM (
        SELECT c.user_id
        FROM compras c
        JOIN compra_items i ON i.compra_id = c.id
        JOIN juegos j ON j.id = i.juego_id
        WHERE j.id_desarrolladora = :devId
        GROUP BY c.user_id
        HAVING COUNT(DISTINCT j.id) >= 2
      ) t
    """, nativeQuery = true)
    long compradoresRecurrentesDev(Long devId);
}
