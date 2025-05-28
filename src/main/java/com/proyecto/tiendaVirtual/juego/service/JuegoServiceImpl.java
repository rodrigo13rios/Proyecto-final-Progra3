package com.proyecto.tiendaVirtual.juego.service;

import com.proyecto.tiendaVirtual.exceptions.ElementoExistenteException;
import com.proyecto.tiendaVirtual.exceptions.ElementoNoEncontradoException;
import com.proyecto.tiendaVirtual.exceptions.ListaVaciaException;
import com.proyecto.tiendaVirtual.juego.model.Categoria;
import com.proyecto.tiendaVirtual.juego.model.Juego;
import com.proyecto.tiendaVirtual.juego.repository.JuegoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class JuegoServiceImpl implements JuegoService{

    private final JuegoRepository juegoRepository;

    public JuegoServiceImpl(JuegoRepository juegoRepository){
        this.juegoRepository=juegoRepository;
    }

    //TOdO ver si es mejor verificar por nombre del juego que por el ID
    @Override
    public void createJuego(Juego juego) throws ElementoExistenteException {

        if (juegoRepository.existsById(juego.getId())){
            throw new ElementoExistenteException("El juego ya se encuentra cargado");
        }
        juegoRepository.save(juego);
    }

    @Override
    public Optional<Juego> findJuegoById(Long id) throws ElementoNoEncontradoException {
        Optional<Juego> optional = juegoRepository.findById(id);
        if (optional.isEmpty()){
            throw new ElementoNoEncontradoException("No se encontro un juego con ese id");
        }
        return juegoRepository.findById(id);
    }

    //TODO preguntar al profe si esta bien implementado asi o es como en DesarrolladoraServiceImpl
    @Override
    public Optional<Juego> findByName(String nombre) throws ListaVaciaException {
        Optional<Juego> optional = juegoRepository.findAll()
                                    .stream()
                                    .filter(x->x.getNombre().equals(nombre))
                                    .findFirst();
        if (optional.isEmpty()){
            throw new ListaVaciaException("No se encuentran juegos con el nombre a buscar");
        }
        return optional;
    }

    @Override
    public List<Juego> getByCategoria(Categoria categoria) throws ListaVaciaException {
        List<Juego> juegos = juegoRepository.findAll()
                            .stream()
                            .filter(x->x.getCategoria().equals(categoria))
                            .toList();
        if (juegos.isEmpty()){
            throw new ListaVaciaException("No se encuentran juegos con la categoria a buscar");
        }
        return juegos;
    }

    @Override
    public List<Juego> getAllJuegos() throws ListaVaciaException {
        List<Juego> juegos = juegoRepository.findAll();
        if (juegos.isEmpty()){
            throw new ListaVaciaException("No se encuentran juegos cargados");
        }
        return juegos;
    }

    //TODO: revisar actualizacion, quiza habra que agregar DTO
    @Override
    public void updateJuego(Long id, Juego updateJuego) throws ElementoNoEncontradoException {
        if (juegoRepository.existsById(id)){
            juegoRepository.deleteById(id);
            juegoRepository.save(updateJuego);
        }else throw new ElementoNoEncontradoException("El juego no se encuentra cargado");
    }

    @Override
    public void deleteJuego(Long id) throws ElementoNoEncontradoException {
        if (juegoRepository.existsById(id)){
            juegoRepository.deleteById(id);
        }else throw new ElementoNoEncontradoException("El juego no se encuentra cargado");
    }
}
