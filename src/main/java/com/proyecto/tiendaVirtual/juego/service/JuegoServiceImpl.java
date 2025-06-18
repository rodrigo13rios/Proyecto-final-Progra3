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

        Optional<Juego>findJuego=repo.findByNombre(juego.getNombre().toUpperCase());
        if (findJuego.isPresent()){
            throw new ElementoYaExistenteException("El juego ya se encuentra cargado");
        }
        juego.setNombre(juego.getNombre().toUpperCase());
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


    @Override
    public Optional<Juego> findByName(String nombre) throws ElementoNoEncontradoException {

        Optional<Juego> optional = repo.findByNombre(nombre.toUpperCase());
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

    @Override
    public Juego update(Long id, Juego updateJuego) throws ElementoNoEncontradoException {
        return repo.findById(id).map(existing ->{
                    existing.setNombre(updateJuego.getNombre().toUpperCase());
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
