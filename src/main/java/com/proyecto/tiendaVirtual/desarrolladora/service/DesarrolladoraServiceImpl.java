package com.proyecto.tiendaVirtual.desarrolladora.service;

import com.proyecto.tiendaVirtual.desarrolladora.dto.DesarrolladoraDTO;
import com.proyecto.tiendaVirtual.desarrolladora.dto.EstadisticaJuegoDTO;
import com.proyecto.tiendaVirtual.desarrolladora.model.Desarrolladora;
import com.proyecto.tiendaVirtual.desarrolladora.repository.DesarrolladoraRepository;
import com.proyecto.tiendaVirtual.exceptions.ElementoYaExistenteException;
import com.proyecto.tiendaVirtual.exceptions.ElementoNoEncontradoException;
import com.proyecto.tiendaVirtual.juego.dto.JuegoVerDTO;
import com.proyecto.tiendaVirtual.juego.model.Juego;
import com.proyecto.tiendaVirtual.juego.service.JuegoService;
import com.proyecto.tiendaVirtual.perfil.repository.PerfilRepository;
import com.proyecto.tiendaVirtual.user.model.User;
import com.proyecto.tiendaVirtual.user.repository.UserRepository;
import com.proyecto.tiendaVirtual.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DesarrolladoraServiceImpl implements DesarrolladoraService{
    @Autowired
    private DesarrolladoraRepository repo;
    @Autowired
    private SecurityUtils securityUtils;
    @Autowired
    private PerfilRepository perfilRepo;
    @Autowired
    private JuegoService juegoService;

    @Override
    public Desarrolladora create(Desarrolladora desarrolladora) throws ElementoYaExistenteException {
        if (findByNombre(desarrolladora.getNombre().toUpperCase()).isPresent()) {
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
        return repo.findByNombre(nombre.toUpperCase());
    }

    @Override
    public List<DesarrolladoraDTO> getAll() {
        return repo.findAll().stream().map(this::convertirADTO).toList();
    }

    @Override
    public Desarrolladora update(Desarrolladora nuevo) {
        //El Update se realiza sobre la Desarrolladora del User logeado que hace la peticion. Haciendo así que solo pueda editar su propia Desarrolladora.
        //Obtengo el User
        User logedUser = securityUtils.getLoggedUser();

        //Obtengo la desarrolladora desde el User
        Desarrolladora existente = logedUser.getDesarrolladora();
        if (existente == null) {
            throw new ElementoNoEncontradoException("No se ha podido obtener la Desarrolladora del User logeado");
        }


        if (nuevo.getNombre()!=null){
            if (!existente.getNombre().equals(nuevo.getNombre()) && findByNombre(nuevo.getNombre()).isPresent()) {
                throw new ElementoYaExistenteException("Ya existe una Desarrolladora el nombre "+nuevo.getNombre());
            }
            existente.setNombre(nuevo.getNombre());

        }

        if (nuevo.getPaisOrigen() != null) {
            existente.setPaisOrigen(nuevo.getPaisOrigen());
        }

        // Guardar y retornar
        return repo.save(existente);
    }

    public List<JuegoVerDTO> getJuegos(){
        Desarrolladora desarrolladora = securityUtils.getLoggedUser().getDesarrolladora();
        if (desarrolladora==null)throw new ElementoNoEncontradoException("No se ha podido obtener la desarrolladora del User logueado");

        return desarrolladora.getJuegos().stream().map(j -> juegoService.convertirAVerDTO(j)).toList();
    }

    public DesarrolladoraDTO convertirADTO(Desarrolladora desarrolladora){
        DesarrolladoraDTO dto = new DesarrolladoraDTO();

        dto.setId(desarrolladora.getId());
        dto.setNombre(desarrolladora.getNombre());
        dto.setPais(desarrolladora.getPaisOrigen());

        return dto;
    }

    @Override
    public List<EstadisticaJuegoDTO> getEstadisticasJuegos() {
        Desarrolladora desarrolladora = securityUtils.getLoggedUser().getDesarrolladora();
        if (desarrolladora==null)throw new ElementoNoEncontradoException("No se ha podido obtener la desarrolladora del User logueado");

        List<Object[]> raw = perfilRepo.countVentasYFavoritosPorDesarrolladora(desarrolladora.getId());

        Map<Long, Long> ventasPorJuego = new HashMap<>();
        Map<Long, Long> favsPorJuego = new HashMap<>();
        for (Object[] r : raw) {
            Long juegoId = ((Number) r[0]).longValue();
            Long ventas = ((Number) r[1]).longValue();
            Long favs = ((Number) r[2]).longValue();
            ventasPorJuego.put(juegoId, ventas);
            favsPorJuego.put(juegoId, favs);
        }

        List<EstadisticaJuegoDTO> estadisticas = desarrolladora.getJuegos().stream()
                .map(j -> new EstadisticaJuegoDTO(
                        juegoService.convertirAVerDTO(j),
                        ventasPorJuego.getOrDefault(j.getId(), 0L),
                        favsPorJuego.getOrDefault(j.getId(), 0L)
                ))
                .toList();

        return estadisticas;
    }

    public
}
