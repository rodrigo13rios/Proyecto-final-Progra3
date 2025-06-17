package com.proyecto.tiendaVirtual.compra.repository;

import com.proyecto.tiendaVirtual.compra.model.Compra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CompraRepository extends JpaRepository<Compra,Long> {
    List<Compra> findByCliente_Email(String email);
}
