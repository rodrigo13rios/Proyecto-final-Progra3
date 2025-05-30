package com.proyecto.tiendaVirtual.desarrolladora.service;

import com.proyecto.tiendaVirtual.desarrolladora.model.Desarrolladora;
import com.proyecto.tiendaVirtual.desarrolladora.repository.DesarrolladoraRepository;
import com.proyecto.tiendaVirtual.exceptions.ElementoExistenteException;
import com.proyecto.tiendaVirtual.exceptions.ElementoNoEncontradoException;
import com.proyecto.tiendaVirtual.exceptions.ListaVaciaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DesarrolladoraServiceImpl implements DesarrolladoraService{
    @Autowired
    private DesarrolladoraRepository repo;


    @Override
    public void createDesarrolladora(Desarrolladora desarrolladora) throws ElementoExistenteException {
        if (repo.existsById(desarrolladora.getId())){
            throw new ElementoExistenteException("La desarrolladora ya se encuentra cargada");
        }
        repo.save(desarrolladora);
    }

    @Override
    public Optional<Desarrolladora> findDesarrolladoraById(Long id) throws ElementoNoEncontradoException {
        Optional<Desarrolladora> desarrolladora = repo.findById(id);
        if (desarrolladora.isEmpty()){
            throw new ElementoNoEncontradoException("No se encuentra una desarrolladora con ese ID");
        }
        return desarrolladora;
    }

    @Override
    public Optional<Desarrolladora> findByName(String nombre) throws ElementoNoEncontradoException {
        Optional<Desarrolladora> desarrolladora = repo.findByNombre(nombre);
        if (desarrolladora.isEmpty()) {
            throw new ElementoNoEncontradoException("No se encuentra una desarrolladora con ese nombre");
        }
        return desarrolladora;
    }

    @Override
    public List<Desarrolladora> getAllDesarrolladoras() throws ListaVaciaException {
        List<Desarrolladora> desarrolladoras = repo.findAll();
        if (desarrolladoras.isEmpty()){
            throw new ListaVaciaException("No se encuentran desarrolladoras cargadas");
        }
        return desarrolladoras;
    }

    @Override
    public void updateDesarrolladora(Long id, Desarrolladora updatedDesarrolladora) throws ElementoNoEncontradoException {
        if (repo.existsById(id)){
            repo.deleteById(id);
            repo.save(updatedDesarrolladora);
        }else throw new ElementoNoEncontradoException("No se encuentra cargada una desarrolladora con ese Id");
    }

    @Override
    public void deleteDesarrolladora(Long id) throws ElementoNoEncontradoException {
        if (repo.existsById(id)){
            repo.deleteById(id);

        }else{
            throw new ElementoNoEncontradoException("No se encuentra una desarrolladora con ese Id");

        }
    }
}
