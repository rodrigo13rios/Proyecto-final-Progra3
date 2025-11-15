package com.proyecto.tiendaVirtual.carrito.repository;

import com.proyecto.tiendaVirtual.carrito.model.CarroDeCompras;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarroDeComprasRepository extends JpaRepository<CarroDeCompras, Long> {

    Optional<CarroDeCompras> findByCliente_Id(Long idCliente);

    void deleteByCliente_Id(Long idCliente);
}
