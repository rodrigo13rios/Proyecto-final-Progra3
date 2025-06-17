package com.proyecto.tiendaVirtual.perfil.service;

import com.proyecto.tiendaVirtual.billetera.model.Billetera;
import com.proyecto.tiendaVirtual.exceptions.ElementoYaExistenteException;
import com.proyecto.tiendaVirtual.exceptions.ElementoNoEncontradoException;
import com.proyecto.tiendaVirtual.juego.model.Juego;
import com.proyecto.tiendaVirtual.juego.service.JuegoService;
import com.proyecto.tiendaVirtual.perfil.dto.PerfilDTO;
import com.proyecto.tiendaVirtual.perfil.model.Perfil;
import com.proyecto.tiendaVirtual.perfil.repository.PerfilRepository;
import com.proyecto.tiendaVirtual.user.dto.UserDTO;
import com.proyecto.tiendaVirtual.user.model.User;
import com.proyecto.tiendaVirtual.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class PerfilServiceImpl implements PerfilService {
    @Autowired
    private PerfilRepository repo;
    @Autowired
    private JuegoService juegoService;
    @Autowired
    private SecurityUtils securityUtils;

    @Override
    public Perfil create(Perfil perfil) throws ElementoYaExistenteException {
        if (repo.existsByNickName(perfil.getNickName())){
            throw new ElementoYaExistenteException("Ya existe un perfil con el nickName ingresado");
        }
        Billetera billetera = new Billetera();
        billetera.setSaldo(0.0);
        perfil.setBilletera(billetera);

        return repo.save(perfil);
    }

    @Override
    public List<Perfil> getAll() {
        return repo.findAll();
    }

    @Override
    public Optional<Perfil> getById(Long id) {
        return repo.findById(id);
    }

    @Override
    public Optional<Perfil> getByNickName(String nickName) {
        return repo.findByNickName(nickName);
    }

    @Override
    public List<Juego> obtenerJuegos(Long id){ //Obtener juegos a partil de un Perfil ID
        Perfil perfil = repo.findById(id)
                        .orElseThrow(()-> new ElementoNoEncontradoException("Perfil con ID "+id+" no encontrado"));
        return perfil.getJuegos();
    }

    @Override
    public List<Juego> obtenerJuegos(){ //Obtener los juegos del usuario Logeado
        Perfil perfil = securityUtils.getLoggedUser().getPerfil();
        if (perfil == null) {
            throw new ElementoNoEncontradoException("No se ha podido obtener el Perfil del User logeado");
        }
        return perfil.getJuegos();
    }

    @Override
    public void agregarJuego(Long id, Long juegoId){
        Perfil perfil = repo.findById(id)
                .orElseThrow(()->new ElementoNoEncontradoException("Perfil con ID "+id+" no encontrado"));

        Juego juego = juegoService.findById(id)
                .orElseThrow(()->new ElementoNoEncontradoException("Juego no encontrado"));

        if (perfil.getJuegos().contains(juego)) throw new ElementoYaExistenteException("El perfil ya posee este juego");

        perfil.getJuegos().add(juego);
        repo.save(perfil);
    }

    @Override
    public Perfil update(PerfilDTO nuevo) {
        //Obtengo el Perfil
        Perfil existente = securityUtils.getLoggedUser().getPerfil();
        if (existente == null) {
            throw new ElementoNoEncontradoException("No se ha podido obtener el Perfil del User logeado");
        }

        //Validación del nuevo Nickname
        if (nuevo.getNickName()!=null){
            if (!existente.getNickName().equals(nuevo.getNickName()) && repo.existsByNickName(nuevo.getNickName())) {
                throw new ElementoYaExistenteException("Ya existe un perfil con el nickName "+nuevo.getNickName());
            }
            existente.setNickName(nuevo.getNickName());
        }
        return repo.save(existente);
    }
}
