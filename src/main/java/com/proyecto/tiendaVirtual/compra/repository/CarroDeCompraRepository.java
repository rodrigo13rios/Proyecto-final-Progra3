package com.proyecto.tiendaVirtual.compra.repository;

import com.proyecto.tiendaVirtual.compra.model.CarroDeCompras;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface CarroDeCompraRepository extends JpaRepository<CarroDeCompras,Long> {
    List<CarroDeCompras> findByCliente_Id(Long id_cliente);
    List<CarroDeCompras>findByCliente_nickName(String nickName);
    void deleteByCliente_Id(Long id_cliente);
    Long countByCliente_Id(Long id_cliente);
}
