package com.proyecto.tiendaVirtual.compraDetalle.repository;

import com.proyecto.tiendaVirtual.compraDetalle.model.CompraDetalle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompraDetalleRepository extends JpaRepository<CompraDetalle, Long> {
    List<CompraDetalle> findAllByCompraId(Long compraId);
    List<CompraDetalle> findAllByJuegoId(Long juegoId);
}
