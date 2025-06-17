package com.proyecto.tiendaVirtual.desarrolladora.service;

import com.proyecto.tiendaVirtual.desarrolladora.model.Desarrolladora;
import com.proyecto.tiendaVirtual.desarrolladora.repository.DesarrolladoraRepository;
import com.proyecto.tiendaVirtual.exceptions.ElementoYaExistenteException;
import com.proyecto.tiendaVirtual.exceptions.ElementoNoEncontradoException;
import com.proyecto.tiendaVirtual.user.model.User;
import com.proyecto.tiendaVirtual.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DesarrolladoraServiceImpl implements DesarrolladoraService{
    @Autowired
    private DesarrolladoraRepository repo;
    @Autowired
    private UserRepository userRepo;


    @Override
    public Desarrolladora create(Desarrolladora desarrolladora) throws ElementoYaExistenteException {
        if (findByNombre(desarrolladora.getNombre()).isPresent()) {
            throw new ElementoYaExistenteException("Ya existe una desarrolladora con ese nombre");
        }
        return repo.save(desarrolladora);
    }

    @Override
    public Optional<Desarrolladora> findById(Long id) {
        return repo.findById(id);
    }


    @Override

    public Optional<Desarrolladora> findByNombre(String nombre){
        return repo.findByNombre(nombre);
    }

    @Override
    public List<Desarrolladora> getAll() {
        return repo.findAll();
    }

    @Override
    public Desarrolladora update(Desarrolladora nuevo) {
        //El Update se realiza sobre la Desarrolladora del User logeado que hace la peticion. Haciendo así que solo pueda editar su propia Desarrolladora.
        //Obtengo el User
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User logedUser = userRepo.findByEmail(email)
                .orElseThrow(() -> new ElementoNoEncontradoException("No se ha podido obtener el usuario logeado"));

        //Obtengo la desarrolladora desde el User
        Desarrolladora existente = logedUser.getDesarrolladora();
        if (existente == null) {
            throw new ElementoNoEncontradoException("No se ha podido obtener la Desarrolladora del User logeado");
        }

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
