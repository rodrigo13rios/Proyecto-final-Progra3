package com.proyecto.tiendaVirtual.billetera.repository;

import com.proyecto.tiendaVirtual.billetera.model.Billetera;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BilleteraRepository extends JpaRepository<Billetera, Long> {
}
