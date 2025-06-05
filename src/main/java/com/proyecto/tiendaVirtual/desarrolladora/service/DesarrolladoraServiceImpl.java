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
    public Desarrolladora create(Desarrolladora desarrolladora) throws ElementoYaExistenteException {
        return repo.save(desarrolladora);
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
    public Desarrolladora update(Long id, Desarrolladora nuevo) {
        // Buscar la desarrolladora existente por ID
        Desarrolladora existente = repo.findById(id)
                .orElseThrow(() -> new ElementoNoEncontradoException("Desarrolladora con ID " + id + " no encontrada"));

        // Actualizar campos
        if (nuevo.getNombre() != null) {
            existente.setNombre(nuevo.getNombre());
        }
        if (nuevo.getPaisOrigen() != null) {
            existente.setPaisOrigen(nuevo.getPaisOrigen());
        }

        // Guardar y retornar
        return repo.save(existente);
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
