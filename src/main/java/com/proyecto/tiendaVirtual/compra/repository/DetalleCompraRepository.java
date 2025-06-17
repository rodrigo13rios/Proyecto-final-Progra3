package com.proyecto.tiendaVirtual.compra.repository;

import com.proyecto.tiendaVirtual.compra.model.DetalleCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface DetalleCompraRepository extends JpaRepository<DetalleCompra, Long> {
    List<DetalleCompra> findByCompra_Id(Long id_compra);
}
