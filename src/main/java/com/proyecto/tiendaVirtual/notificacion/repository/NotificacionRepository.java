package com.proyecto.tiendaVirtual.notificacion.repository;

import com.proyecto.tiendaVirtual.notificacion.model.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {
    List<Notificacion> findByPerfilIdOrderByFechaDesc(Long perfilId);
    List<Notificacion> findByPerfilIdAndLeidaFalse(Long perfilId);
}
