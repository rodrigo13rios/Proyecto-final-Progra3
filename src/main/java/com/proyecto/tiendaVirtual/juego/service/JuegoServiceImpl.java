package com.proyecto.tiendaVirtual.juego.service;

import com.proyecto.tiendaVirtual.billetera.service.BilleteraService;
import com.proyecto.tiendaVirtual.desarrolladora.model.Desarrolladora;
import com.proyecto.tiendaVirtual.exceptions.AccesoDenegadoException;
import com.proyecto.tiendaVirtual.exceptions.ElementoYaExistenteException;
import com.proyecto.tiendaVirtual.exceptions.ElementoNoEncontradoException;
import com.proyecto.tiendaVirtual.juego.dto.JuegoDTO;
import com.proyecto.tiendaVirtual.juego.dto.JuegoUpdateDTO;
import com.proyecto.tiendaVirtual.juego.model.Categoria;
import com.proyecto.tiendaVirtual.juego.model.Juego;
import com.proyecto.tiendaVirtual.juego.repository.JuegoRepository;
import com.proyecto.tiendaVirtual.perfil.model.Perfil;
import com.proyecto.tiendaVirtual.perfil.repository.PerfilRepository;
import com.proyecto.tiendaVirtual.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Service
public class JuegoServiceImpl implements JuegoService{
    @Autowired
    JuegoRepository repo;
    @Autowired
    PerfilRepository perfilRepo;
    @Autowired
    BilleteraService billeteraService;
    @Autowired
    SecurityUtils securityUtils;

    @Override
    @Transactional
    public Juego create(JuegoDTO dto) {
        // Obtener la desarrolladora del usuario logeado
        Desarrolladora desarrolladora = securityUtils.getLoggedUser().getDesarrolladora();
        if (desarrolladora == null) {
            throw new ElementoNoEncontradoException("No se ha podido obtener la Desarrolladora del User logeado");
        }

        if (getByNombre(dto.getNombre()).isPresent()) {
            throw new ElementoYaExistenteException("Ya existe un juego con ese nombre");
        }

        // Construir el juego
        Juego juego = new Juego();
        juego.setDesarrolladora(desarrolladora);
        juego.setNombre(dto.getNombre());
        juego.setFechaLanzamiento(dto.getFechaLanzamiento());
        juego.setPrecio(dto.getPrecio());

        try { //"Traduzco" la Categoria
            Categoria categoriaEnum = Categoria.valueOf(dto.getCategoria().toUpperCase());
            juego.setCategoria(categoriaEnum);
        } catch (IllegalArgumentException ex) {
            throw new ElementoNoEncontradoException("No se ha encontrado la categoría: " + dto.getCategoria() + ", o no se ha podido asignar");
        }

        return repo.save(juego);
    }

    @Override
    @Transactional
    public Juego update(Long id, JuegoUpdateDTO nuevo) {
        // Obtener la desarrolladora del usuario logeado
        Desarrolladora desarrolladora = securityUtils.getLoggedUser().getDesarrolladora();
        if (desarrolladora == null) {
            throw new ElementoNoEncontradoException("No se ha podido obtener la Desarrolladora del User logeado");
        }

        //Encontrar el Juego
        Juego existente = repo.findById(id)
                .orElseThrow(() -> new ElementoNoEncontradoException("No se encontró el juego con ID: " + id));

        // Validar que el juego pertenece a la desarrolladora del user
        if (!existente.getDesarrolladora().getId().equals(desarrolladora.getId())) {
            throw new AccesoDenegadoException("No podés modificar juegos de otra desarrolladora.");
        }

        // Actualizar campos si vienen en el DTO
        if (nuevo.getNombre()!=null){
            if (!existente.getNombre().equals(nuevo.getNombre()) && getByNombre(nuevo.getNombre()).isPresent()) {
                throw new ElementoYaExistenteException("Ya existe un Juego con el nombre "+nuevo.getNombre());
            }
            existente.setNombre(nuevo.getNombre());
        }
        if (nuevo.getFechaLanzamiento() != null) existente.setFechaLanzamiento(nuevo.getFechaLanzamiento());
        if (nuevo.getPrecio() != null) existente.setPrecio(nuevo.getPrecio());

        if (nuevo.getCategoria() != null) {
            try {
                Categoria cat = Categoria.valueOf(nuevo.getCategoria().toUpperCase());
                existente.setCategoria(cat);
            } catch (IllegalArgumentException ex) {
                throw new ElementoNoEncontradoException("No se ha encontrado la categoría: " + nuevo.getCategoria() + ", o no se ha podido asignar");
            }
        }

        return repo.save(existente);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        // Obtener la desarrolladora del usuario logeado
        Desarrolladora desarrolladora = securityUtils.getLoggedUser().getDesarrolladora();
        if (desarrolladora == null) {
            throw new ElementoNoEncontradoException("No se ha podido obtener la Desarrolladora del User logeado");
        }

        Juego juego = repo.findById(id)
                .orElseThrow(() -> new ElementoNoEncontradoException("No se encontró el juego con ID: " + id));

        // Verificar propiedad del juego
        if (!juego.getDesarrolladora().getId().equals(desarrolladora.getId())) {
            throw new AccesoDenegadoException("No podés eliminar un juego que no te pertenece.");
        }

        repo.delete(juego);
    }


    @Override
    @Transactional
    public void comprarJuego(Long juegoId) {
        Perfil perfil = securityUtils.getLoggedUser().getPerfil();

        //El juego existe?
        Juego juego = repo.findById(juegoId)
                .orElseThrow(() -> new ElementoNoEncontradoException("Juego no encontrado con ID: " + juegoId));

        //Ya tiene comprado el Juego?
        if (perfil.getJuegos().contains(juego)) {
            throw new ElementoYaExistenteException("Ya tienes este juego.");
        }

        //Realizar el Pago
        billeteraService.restarSaldo(juego.getPrecio());

        // Agregar juego a la lista del Perfil
        perfil.getJuegos().add(juego);

        // Guardar usuario (o billetera, según cómo esté configurado)
        perfilRepo.save(perfil);
    }


    @Override
    public Optional<Juego> getById(Long id) {
        return repo.findById(id);
    }

    @Override
    public Optional<Juego> getByNombre(String nombre){
        return repo.findByNombre(nombre);
    }

    @Override
    public List<Juego> getByCategoria(String strCategoria){
        Categoria categoriaEnum;
        try { //"Traduzco" la Categoria
            categoriaEnum = Categoria.valueOf(strCategoria.toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new ElementoNoEncontradoException("No se ha encontrado la categoría: " + strCategoria + ", o no se ha podido asignar");
        }
        return repo.getByCategoria(categoriaEnum);
    }

    @Override
    public List<Juego> getAll() {
        return repo.findAll();
    }
}
