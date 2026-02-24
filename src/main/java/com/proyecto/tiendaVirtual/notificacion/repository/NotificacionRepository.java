package com.proyecto.tiendaVirtual.notificacion.repository;

import com.proyecto.tiendaVirtual.notificacion.model.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {

    List<Notificacion> findByPerfilIdOrderByFechaDesc(Long perfilId);

    List<Notificacion> findByPerfilIdAndLeidaFalse(Long perfilId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("""
        UPDATE Notificacion n
        SET n.leida = true
        WHERE n.perfil.id = :perfilId
          AND n.leida = false
    """)
    int marcarTodasComoLeidas(@Param("perfilId") Long perfilId);
}
