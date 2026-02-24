package com.proyecto.tiendaVirtual.compra.repository;

import com.proyecto.tiendaVirtual.compra.model.CompraItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompraItemRepository extends JpaRepository<CompraItem, Long> {
}
