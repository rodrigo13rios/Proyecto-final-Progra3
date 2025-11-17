package com.proyecto.tiendaVirtual.perfil.service;

import com.proyecto.tiendaVirtual.exceptions.ElementoYaExistenteException;
import com.proyecto.tiendaVirtual.exceptions.ElementoNoEncontradoException;
import com.proyecto.tiendaVirtual.juego.model.Juego;
import com.proyecto.tiendaVirtual.perfil.dto.PerfilDTO;
import com.proyecto.tiendaVirtual.perfil.model.Perfil;
import com.proyecto.tiendaVirtual.user.dto.UserDTO;
import com.proyecto.tiendaVirtual.user.model.User;
import org.springframework.stereotype.Service;

import java.time.Period;
import java.util.List;
import java.util.Optional;

@Service
public interface PerfilService {
    Perfil create(Perfil perfil);
    List<Perfil> getAll();
    Optional<Perfil> getById(Long id);
    Optional<Perfil> getByNickName(String nickName);
    Perfil update(PerfilDTO nuevo);
    List<Juego> obtenerJuegos(Long id);
    List<Juego> obtenerJuegos();
    void agregarJuego(Long id,Long juegoId);
    Perfil agregarAFavoritos(Long juegoId);
    List<Juego> obtenerFavoritos();
    Perfil eliminarFavoritos(Long juegoId);
}
