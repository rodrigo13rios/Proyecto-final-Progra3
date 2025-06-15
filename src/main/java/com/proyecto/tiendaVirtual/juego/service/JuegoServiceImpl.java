package com.proyecto.tiendaVirtual.juego.service;

import com.proyecto.tiendaVirtual.exceptions.ElementoYaExistenteException;
import com.proyecto.tiendaVirtual.exceptions.ElementoNoEncontradoException;
import com.proyecto.tiendaVirtual.juego.model.Categoria;
import com.proyecto.tiendaVirtual.juego.model.Juego;
import com.proyecto.tiendaVirtual.juego.repository.JuegoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class JuegoServiceImpl implements JuegoService{

    private final JuegoRepository repo;

    public JuegoServiceImpl(JuegoRepository repo){
        this.repo = repo;
    }

    @Override
    public void create(Juego juego) throws ElementoYaExistenteException {

        Optional<Juego>findJuego=repo.findByNombre(juego.getNombre());
        if (findJuego.isPresent()){
            throw new ElementoYaExistenteException("El juego ya se encuentra cargado");
        }
        repo.save(juego);
    }

    @Override
    public Optional<Juego> findById(Long id) throws ElementoNoEncontradoException {
        Optional<Juego> optional = repo.findById(id);
        if (optional.isEmpty()){
            throw new ElementoNoEncontradoException("No se encontro un juego con ese id");
        }
        return repo.findById(id);
    }

    //TODO preguntar al profe si esta bien implementado asi o es como en DesarrolladoraServiceImpl
    @Override
    public Optional<Juego> findByName(String nombre) throws ElementoNoEncontradoException {

        Optional<Juego> optional = repo.findByNombre(nombre);
        if (optional.isEmpty()){
            throw new ElementoNoEncontradoException("No se encuentran juegos con el nombre a buscar");
        }
        return optional;
    }

    @Override
    public List<Juego> getByCategoria(Categoria categoria) {
        return repo.findAll()
                            .stream()
                            .filter(x->x.getCategoria().equals(categoria))
                            .toList();
    }

    @Override
    public List<Juego> getAll() {
        return repo.findAll();
    }

    //TODO: revisar actualizacion, quiza habra que agregar DTO
    @Override
    public Juego update(Long id, Juego updateJuego) throws ElementoNoEncontradoException {
        return repo.findById(id).map(existing ->{
                    existing.setNombre(updateJuego.getNombre());
                    existing.setPrecio(updateJuego.getPrecio());
                    existing.setCategoria(updateJuego.getCategoria());
                    existing.setDesarrolladora(updateJuego.getDesarrolladora());
                    return repo.save(existing);
                }).orElseThrow(()->new ElementoNoEncontradoException("No se encontro un juego con ID:"+id));
    }

    @Override
    public void delete(Long id) throws ElementoNoEncontradoException {
        if (repo.existsById(id)){
            repo.deleteById(id);
        }else throw new ElementoNoEncontradoException("El juego no se encuentra cargado");
    }
}
