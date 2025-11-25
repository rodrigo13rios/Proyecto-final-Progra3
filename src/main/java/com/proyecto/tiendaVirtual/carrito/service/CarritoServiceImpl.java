package com.proyecto.tiendaVirtual.carrito.service;

import com.proyecto.tiendaVirtual.carrito.model.JuegoCarro;
import com.proyecto.tiendaVirtual.carrito.repository.CarritoRepository;
import com.proyecto.tiendaVirtual.exceptions.ElementoNoEncontradoException;
import com.proyecto.tiendaVirtual.exceptions.ElementoYaExistenteException;
import com.proyecto.tiendaVirtual.juego.dto.JuegoVerDTO;
import com.proyecto.tiendaVirtual.juego.model.Juego;
import com.proyecto.tiendaVirtual.juego.repository.JuegoRepository;
import com.proyecto.tiendaVirtual.juego.service.JuegoService;
import com.proyecto.tiendaVirtual.perfil.model.Perfil;
import com.proyecto.tiendaVirtual.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CarritoServiceImpl implements CarritoService {
    @Autowired
    CarritoRepository repo;
    @Autowired
    SecurityUtils securityUtils;
    @Autowired
    JuegoRepository juegoRepo;
    @Autowired
    JuegoService juegoService;

    @Override
    @Transactional
    public List<JuegoVerDTO> agregarJuego(Long juegoId) {
        Perfil perfil = securityUtils.getLoggedUser().getPerfil();

        //El juego existe?
        Juego juego = juegoRepo.findById(juegoId)
                .orElseThrow(() -> new ElementoNoEncontradoException("Juego no encontrado con ID: " + juegoId));

        //Ya tiene comprado el Juego?
        if (perfil.getJuegos().contains(juego)) {
            throw new ElementoYaExistenteException("Ya tienes este juego.");
        }

        JuegoCarro juegoCarro = new JuegoCarro();
        juegoCarro.setUserId(perfil.getId());
        juegoCarro.setJuego(juego);
        repo.save(juegoCarro);
        return getAll();
    }

    @Override
    @Transactional
    public List<JuegoVerDTO> getAll() {
        Perfil perfil = securityUtils.getLoggedUser().getPerfil();

        List<JuegoCarro> carrito = repo.getByUserId(perfil.getId());

        // Convertimos el JuegoCarro → Juego → DTO
        return carrito.stream()
                .map(JuegoCarro::getJuego)             // obtener el juego
                .map(JuegoVerDTO::convertirAVerDTO)   // convertir a DTO
                .toList();
    }



    @Override
    @Transactional
    public List<JuegoVerDTO> borrarJuego(Long juegoId) {
        Long userId = securityUtils.getLoggedUser().getPerfil().getId();

        JuegoCarro juegoCarro = repo.findByUserIdAndJuegoId(userId, juegoId)
                .orElseThrow(() -> new ElementoNoEncontradoException("No se encontró el juego en el carrito"));
        repo.delete(juegoCarro);

        return getAll(); // asume que getAll() mapea y corre dentro de la misma transacción
    }


    @Override
    @Transactional
    public void limpiar() {
        Perfil perfil = securityUtils.getLoggedUser().getPerfil();

        if (perfil == null) {
            throw new ElementoNoEncontradoException("No se ha podido obtener el user del perfil logueado");
        }

        repo.deleteByUserId(perfil.getId());
    }

    @Override
    @Transactional
    public void comprar() {
        Perfil perfil = securityUtils.getLoggedUser().getPerfil();
        if (perfil == null) {
            throw new ElementoNoEncontradoException("No se ha podido obtener el perfil del usuario logueado");
        }

        List<JuegoCarro> carrito = repo.getByUserId(perfil.getId());

        for (JuegoCarro jc : carrito) {
            Juego juego = jc.getJuego();
            if (juego != null && juego.getId() != null) {
                juegoService.comprarJuego(juego.getId());
            }
        }
        limpiar();
    }
}
