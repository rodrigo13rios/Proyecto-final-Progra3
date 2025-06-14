package com.proyecto.tiendaVirtual.perfil.repository;

import com.proyecto.tiendaVirtual.perfil.model.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PerfilRepository extends JpaRepository<Perfil,Long> {
    Optional<Perfil> findByNickName(String nickName);
    boolean existsByNickName(String nickName);
}
