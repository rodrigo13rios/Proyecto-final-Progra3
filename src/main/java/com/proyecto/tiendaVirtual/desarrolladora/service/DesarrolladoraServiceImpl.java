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
    private DesarrolladoraRepository desarrolladoraRepository;


    @Override
    public void createDesarrolladora(Desarrolladora desarrolladora) throws ElementoExistenteException {
        if (desarrolladoraRepository.existsById(desarrolladora.getId())){
            throw new ElementoExistenteException("La desarrolladora ya se encuentra cargada");
        }
        desarrolladoraRepository.save(desarrolladora);
    }

    @Override
    public Optional<Desarrolladora> findDesarrolladoraById(Long id) throws ElementoNoEncontradoException {
        Optional<Desarrolladora> desarrolladora = desarrolladoraRepository.findById(id);
        if (desarrolladora.isEmpty()){
            throw new ElementoNoEncontradoException("No se encuentra una desarrolladora con ese ID");
        }
        return desarrolladora;
    }

    @Override
    public Optional<Desarrolladora> findByName(String nombre) throws ElementoNoEncontradoException {
        Optional<Desarrolladora> desarrolladora = desarrolladoraRepository.findByName(nombre);
        if (desarrolladora.isEmpty()) {
            throw new ElementoNoEncontradoException("No se encuentra una desarrolladora con ese nombre");
        }
         return desarrolladora;
    }

    @Override
    public List<Desarrolladora> getAllDesarrolladoras() throws ListaVaciaException {
        List<Desarrolladora> desarrolladoras = desarrolladoraRepository.findAll();
        if (desarrolladoras.isEmpty()){
            throw new ListaVaciaException("No se encuentran desarrolladoras cargadas");
        }
        return desarrolladoras;
    }

    @Override
    public void updateDesarrolladora(Long id, Desarrolladora updatedDesarrolladora) throws ElementoNoEncontradoException {
        if (desarrolladoraRepository.existsById(id)){
            desarrolladoraRepository.deleteById(id);
            desarrolladoraRepository.save(updatedDesarrolladora);
        }else throw new ElementoNoEncontradoException("No se encuentra cargada una desarrolladora con ese Id");
    }

    @Override
    public void deleteDesarrolladora(Long id) throws ElementoNoEncontradoException {
        if (desarrolladoraRepository.existsById(id)){
            desarrolladoraRepository.deleteById(id);

        }else{
            throw new ElementoNoEncontradoException("No se encuentra una desarrolladora con ese Id");

        }
    }
}
