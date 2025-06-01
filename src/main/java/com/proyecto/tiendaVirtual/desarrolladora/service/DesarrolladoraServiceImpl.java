package com.proyecto.tiendaVirtual.desarrolladora.service;

import com.proyecto.tiendaVirtual.desarrolladora.model.Desarrolladora;
import com.proyecto.tiendaVirtual.desarrolladora.repository.DesarrolladoraRepository;
import com.proyecto.tiendaVirtual.exceptions.ElementoYaExistenteException;
import com.proyecto.tiendaVirtual.exceptions.ElementoNoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DesarrolladoraServiceImpl implements DesarrolladoraService{
    @Autowired
    private DesarrolladoraRepository repo;


    @Override
    public void create(Desarrolladora desarrolladora) throws ElementoYaExistenteException {
        if (repo.existsById(desarrolladora.getId())){
            throw new ElementoYaExistenteException("La desarrolladora ya se encuentra cargada");
        }
        repo.save(desarrolladora);
    }

    @Override
    public Optional<Desarrolladora> findById(Long id) throws ElementoNoEncontradoException {
        Optional<Desarrolladora> desarrolladora = repo.findById(id);
        if (desarrolladora.isEmpty()){
            throw new ElementoNoEncontradoException("No se encuentra una desarrolladora con ese ID");
        }
        return desarrolladora;
    }


    @Override

    public Optional<Desarrolladora> findByNombre(String nombre) throws ElementoNoEncontradoException {
        Optional<Desarrolladora> desarrolladora = repo.findByNombre(nombre);
        if (desarrolladora.isEmpty()) {
            throw new ElementoNoEncontradoException("No se encuentra una desarrolladora con ese nombre");
        }
        return desarrolladora;
    }

    @Override
    public List<Desarrolladora> getAll() {
        return repo.findAll();
    }

    @Override
    public void update(Long id, Desarrolladora updatedDesarrolladora) throws ElementoNoEncontradoException {
        if (repo.existsById(id)){
            repo.deleteById(id);
            repo.save(updatedDesarrolladora);
        }else throw new ElementoNoEncontradoException("No se encuentra cargada una desarrolladora con ese Id");
    }

    @Override
    public void delete(Long id) throws ElementoNoEncontradoException {
        if (repo.existsById(id)){
            repo.deleteById(id);

        }else{
            throw new ElementoNoEncontradoException("No se encuentra una desarrolladora con ese Id");
        }
    }
}
