package com.proyecto.tiendaVirtual.perfil.service;

import com.proyecto.tiendaVirtual.exceptions.ElementoYaExistenteException;
import com.proyecto.tiendaVirtual.exceptions.ElementoNoEncontradoException;
import com.proyecto.tiendaVirtual.perfil.model.Perfil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface PerfilService {
    void create(Perfil perfil) throws ElementoYaExistenteException;
    List<Perfil> getAll();
    Optional<Perfil> getById(Long id) throws ElementoNoEncontradoException;
    Optional<Perfil> getByNickName(String nickName) throws ElementoNoEncontradoException;
    void update(Long id, Perfil updatePerfil) throws ElementoNoEncontradoException;
    void delete(Long id) throws ElementoNoEncontradoException;
}
