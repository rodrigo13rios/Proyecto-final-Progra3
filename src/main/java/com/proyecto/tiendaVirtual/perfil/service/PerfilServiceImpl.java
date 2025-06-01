package com.proyecto.tiendaVirtual.perfil.service;

import com.proyecto.tiendaVirtual.exceptions.ElementoYaExistenteException;
import com.proyecto.tiendaVirtual.exceptions.ElementoNoEncontradoException;
import com.proyecto.tiendaVirtual.perfil.model.Perfil;
import com.proyecto.tiendaVirtual.perfil.repository.PerfilRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class PerfilServiceImpl implements PerfilService {
    //@AutoWired
    private final PerfilRepository repo;

    public PerfilServiceImpl(PerfilRepository repo){
        this.repo=repo;
    }
    @Override
    public void create(Perfil perfil) throws ElementoYaExistenteException {
        if (repo.existsById(perfil.getId())){
            throw new ElementoYaExistenteException("El perfil ya se encuentra creado");
        }
        repo.save(perfil);
    }

    @Override
    public List<Perfil> getAll() {
        return repo.findAll();
    }

    @Override
    public Optional<Perfil> getById(Long id) throws ElementoNoEncontradoException {
        Optional<Perfil> optional = repo.findById(id);
        if (optional.isEmpty())throw new ElementoNoEncontradoException("No se encontro un perfil con el ID");
        return optional;
    }

    @Override
    public Optional<Perfil> getByNickName(String nickName) throws ElementoNoEncontradoException {
        Optional<Perfil> optional = repo.findAll()
                                    .stream()
                                    .filter(x->x.getNickName().equals(nickName))
                                    .findFirst();

        if (optional.isEmpty()) throw new ElementoNoEncontradoException("No se encontro un perfil con el NickName indicado");
        return optional;
    }

    @Override
    public void update(Long id, Perfil updatePerfil) throws ElementoNoEncontradoException {
        if (repo.existsById(id)){
            repo.deleteById(id);
            repo.save(updatePerfil);
        }
        throw new ElementoNoEncontradoException("No se encontro un perfil con el ID indicado");

    }

    @Override
    public void delete(Long id) throws ElementoNoEncontradoException {
        if (repo.existsById(id)){
            repo.deleteById(id);
        }
        throw new ElementoNoEncontradoException("No se encontro un perfil con el ID seleccionado");
    }
}
