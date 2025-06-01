package com.proyecto.tiendaVirtual.perfil.service;

import com.proyecto.tiendaVirtual.exceptions.ElementoYaExistenteException;
import com.proyecto.tiendaVirtual.exceptions.ElementoNoEncontradoException;
import com.proyecto.tiendaVirtual.exceptions.ListaVaciaException;
import com.proyecto.tiendaVirtual.perfil.model.Perfil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface PerfilService {
    void createPerfil(Perfil perfil) throws ElementoYaExistenteException;
    List<Perfil> getAllPerfil() throws ListaVaciaException;
    Optional<Perfil> getPefilById(Long id) throws ElementoNoEncontradoException;
    Optional<Perfil> getPerfilByNickName(String nickName) throws ElementoNoEncontradoException;
    void updatePerfil(Long id, Perfil updatePerfil) throws ElementoNoEncontradoException;
    void deletePerfil (Long id) throws ElementoNoEncontradoException;
}
