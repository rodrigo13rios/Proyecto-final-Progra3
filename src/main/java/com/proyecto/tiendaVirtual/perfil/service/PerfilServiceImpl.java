package com.proyecto.tiendaVirtual.perfil.service;

import com.proyecto.tiendaVirtual.billetera.model.Billetera;
import com.proyecto.tiendaVirtual.exceptions.ElementoYaExistenteException;
import com.proyecto.tiendaVirtual.exceptions.ElementoNoEncontradoException;
import com.proyecto.tiendaVirtual.juego.model.Juego;
import com.proyecto.tiendaVirtual.juego.service.JuegoService;
import com.proyecto.tiendaVirtual.perfil.dto.PerfilDTO;
import com.proyecto.tiendaVirtual.perfil.model.Perfil;
import com.proyecto.tiendaVirtual.perfil.repository.PerfilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class PerfilServiceImpl implements PerfilService {
    @Autowired
    private PerfilRepository repo;
    @Autowired
    private JuegoService juegoService;

    @Override
    public Perfil create(PerfilDTO dto) throws ElementoYaExistenteException {

        if (repo.existsByNickName(dto.getNickName()))throw new ElementoYaExistenteException("Ya existe un perfil con el nickName ingresado");
        Perfil perfil = new Perfil();
        Billetera billetera = new Billetera();
        billetera.setSaldo(0.0);

        perfil.setNickName(dto.getNickName());
        perfil.setBilletera(billetera);
        return repo.save(perfil);
    }

    @Override
    public List<Perfil> getAll() {
        return repo.findAll();
    }

    @Override
    public Optional<Perfil> getById(Long id) {
        Optional<Perfil> optional = repo.findById(id);
        if (optional.isEmpty())throw new ElementoNoEncontradoException("Perfil con ID "+id+" no fue encontrado");
        return optional;
    }

    @Override
    public Optional<Perfil> getByNickName(String nickName) {
        Optional<Perfil> optional = repo.findByNickName(nickName);

        if (optional.isEmpty()) throw new ElementoNoEncontradoException("No se encontro un perfil con el NickName indicado");
        return optional;
    }

    @Override
    public List<Juego> obtenerJuegos(Long id){
        Perfil optional = repo.findById(id)
                        .orElseThrow(()-> new ElementoNoEncontradoException("Perfil con ID "+id+" no encontrado"));
        return optional.getJuegos();
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
    public Perfil update(Long id, PerfilDTO nuevo) {

        Perfil existente = repo.findById(id).orElseThrow(()-> new ElementoNoEncontradoException("Perfil con ID "+id+" no encontrado"));
        if (nuevo.getNickName()!=null){

        if (!existente.getNickName().equals(nuevo.getNickName()) && repo.existsByNickName(nuevo.getNickName())) {
            throw new ElementoYaExistenteException("Ya existe un perfil con el nickName "+nuevo.getNickName());
        }

            existente.setNickName(nuevo.getNickName());
        }


        return repo.save(existente);
    }

    @Override
    public void delete(Long id){
        if (repo.existsById(id)){
            repo.deleteById(id);
        }
        throw new ElementoNoEncontradoException("No se encontro un perfil con el ID seleccionado");
    }
}
