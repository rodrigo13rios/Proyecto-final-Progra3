package com.proyecto.tiendaVirtual.juego.service;

import com.proyecto.tiendaVirtual.desarrolladora.dto.DesarrolladoraDTO;
import com.proyecto.tiendaVirtual.desarrolladora.model.Desarrolladora;
import com.proyecto.tiendaVirtual.descuento.repository.DescuentoRepository;
import com.proyecto.tiendaVirtual.exceptions.AccesoDenegadoException;
import com.proyecto.tiendaVirtual.exceptions.ElementoYaExistenteException;
import com.proyecto.tiendaVirtual.exceptions.ElementoNoEncontradoException;
import com.proyecto.tiendaVirtual.juego.dto.JuegoDTO;
import com.proyecto.tiendaVirtual.juego.dto.JuegoUpdateDTO;
import com.proyecto.tiendaVirtual.juego.dto.JuegoVerDTO;
import com.proyecto.tiendaVirtual.juego.model.Categoria;
import com.proyecto.tiendaVirtual.juego.model.Juego;
import com.proyecto.tiendaVirtual.juego.repository.JuegoRepository;
import com.proyecto.tiendaVirtual.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
@Service
public class JuegoServiceImpl implements JuegoService{
    @Autowired
    JuegoRepository repo;
    @Autowired
    SecurityUtils securityUtils;
    @Autowired
    DescuentoRepository descuentoRepository;

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
        juego.setFoto(dto.getFoto());

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
        if (nuevo.getFoto()!=null) existente.setFoto(nuevo.getFoto());

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
    public Optional<Juego> getById(Long id) {
        return repo.findById(id);
    }

    @Override
    public Optional<JuegoVerDTO> getByNombre(String nombre){
        return repo.findByNombre(nombre).map(this::convertirAVerDTO);
    }

    @Override
    public Page<JuegoVerDTO> getAll(String strCategoria, String search, Pageable pageable) {
        //Obtener Categoria
        Categoria categoriaEnum = null;
        if (strCategoria != null) {
            try {
                categoriaEnum = Categoria.valueOf(strCategoria.toUpperCase());
            } catch (IllegalArgumentException ex) {
                throw new ElementoNoEncontradoException(
                        "No se ha encontrado la categoría: " + strCategoria
                );
            }
        }

        //Buscar según parametros
        boolean hasSearch = search != null && !search.isBlank();

        Page<Juego> page;

        if (categoriaEnum != null && hasSearch) { //Se busca Categoria y Search
            page = repo
                    .findByCategoriaAndNombreContainingIgnoreCaseOrCategoriaAndDesarrolladora_NombreContainingIgnoreCase(
                            categoriaEnum,
                            search,
                            categoriaEnum,
                            search,
                            pageable
                    );
        }
        else if (categoriaEnum != null) { //Solo Categoria
            page = repo.findByCategoria(categoriaEnum, pageable);
        }
        else if (hasSearch) { //Solo Search
            page = repo
                    .findByNombreContainingIgnoreCaseOrDesarrolladora_NombreContainingIgnoreCase(
                            search, //Busca juegos con ese 'search'
                            search, //Busca desarrolladoras con ese 'search'
                            pageable
                    );
        }
        else { //Sin parametros (findAll)
            page = repo.findAll(pageable);
        }

        return page.map(this::convertirAVerDTO);
    }



    private int descuentoTotalActivo(Long juegoId) {
        int total = descuentoRepository.getDescuentoTotalActivo(juegoId, LocalDateTime.now());
        return Math.min(total, 100);
    }

    @Override
    public Double obtenerPrecioFinal(Juego juego) {
        int porcentajeTotal = descuentoTotalActivo(juego.getId());
        double precioFinal = juego.getPrecio() * (1.0 - porcentajeTotal / 100.0);
        return Math.round(precioFinal * 100.0) / 100.0;
    }

    public JuegoVerDTO convertirAVerDTO(Juego juego){
        JuegoVerDTO dto = new JuegoVerDTO();

        dto.setId(juego.getId());
        dto.setNombre(juego.getNombre());
        dto.setFechaLanzamiento(juego.getFechaLanzamiento());
        dto.setPrecio(juego.getPrecio());

        dto.setPrecioFinal(this.obtenerPrecioFinal(juego));

        int descuento = descuentoTotalActivo(juego.getId());
        if (descuento > 0) {
            dto.setPorcentajeDescuento(descuento);
        }

        dto.setCategoria(juego.getCategoria());
        dto.setFoto(juego.getFoto());
        dto.setDesarrolladora(new DesarrolladoraDTO( //Convierto a DesarrolladoraDTO
                juego.getDesarrolladora().getId(),
                juego.getDesarrolladora().getNombre(),
                juego.getDesarrolladora().getPaisOrigen()
        ));

        return dto;
    }

}

