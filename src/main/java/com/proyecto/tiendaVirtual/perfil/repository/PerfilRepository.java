package com.proyecto.tiendaVirtual.perfil.repository;

import com.proyecto.tiendaVirtual.perfil.model.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PerfilRepository extends JpaRepository<Perfil,Long> {
    Optional<Perfil> findByNickName(String nickName);
    boolean existsByNickName(String nickName);
//    long countByJuegos_Id(Long juegoId);
    @Query("""
        SELECT j.id,
               (SELECT COUNT(p1.id) FROM Perfil p1 JOIN p1.juegos pj1 WHERE pj1.id = j.id),
               (SELECT COUNT(p2.id) FROM Perfil p2 JOIN p2.favoritos f2 WHERE f2.id = j.id)
        FROM Juego j
        WHERE j.desarrolladora.id = :desarrolladoraId
    """)
    List<Object[]> countVentasYFavoritosPorDesarrolladora(@Param("desarrolladoraId") Long desarrolladoraId);
}
