package com.proyecto.tiendaVirtual.descuento.service;

import com.proyecto.tiendaVirtual.desarrolladora.model.Desarrolladora;
import com.proyecto.tiendaVirtual.desarrolladora.service.DesarrolladoraService;
import com.proyecto.tiendaVirtual.descuento.dto.CreateDescuentoDTO;
import com.proyecto.tiendaVirtual.descuento.dto.DescuentoResponseDTO;
import com.proyecto.tiendaVirtual.descuento.mapper.DescuentoMapper;
import com.proyecto.tiendaVirtual.descuento.model.Descuento;
import com.proyecto.tiendaVirtual.descuento.repository.DescuentoRepository;
import com.proyecto.tiendaVirtual.exceptions.AccesoDenegadoException;
import com.proyecto.tiendaVirtual.exceptions.ElementoNoEncontradoException;
import com.proyecto.tiendaVirtual.juego.model.Juego;
import com.proyecto.tiendaVirtual.juego.service.JuegoService;
import com.proyecto.tiendaVirtual.notificacion.model.Notificacion;
import com.proyecto.tiendaVirtual.notificacion.repository.NotificacionRepository;
import com.proyecto.tiendaVirtual.perfil.model.Perfil;
import com.proyecto.tiendaVirtual.perfil.repository.PerfilRepository;
import com.proyecto.tiendaVirtual.utils.SecurityUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class DescuentoServiceImpl implements DescuentoService{
    private final DescuentoRepository descuentoRepository;

    private final DesarrolladoraService desarrolladoraService;
    private final JuegoService juegoService;
    private final PerfilRepository perfilRepository;
    private final NotificacionRepository notificacionRepository;
    private final DescuentoMapper mapper;
    private final SecurityUtils securityUtils;

    @Override
    public DescuentoResponseDTO crearDescuento(CreateDescuentoDTO dto) {


        if (dto.getFechaFin().isBefore(dto.getFechaInicio())) {
            throw new IllegalArgumentException("Fecha fin inválida");
        }

        Desarrolladora desarrolladora =
                securityUtils.getLoggedUser().getDesarrolladora();
        if (desarrolladora == null) {
            throw new RuntimeException("El usuario no es desarrolladora");
        }


        Optional<Juego> juego = juegoService.getById(dto.getJuegoId());

        if (!juego.get().getDesarrolladora().getId()
                .equals(desarrolladora.getId())) {
            throw new AccesoDenegadoException("El juego no pertenece a esta desarrolladora");
        }

        //Remplazo esto y permito que haya varios descuentos simultaneos. A cambio, los descuentos se suman hasta un máximo de 100%
//        Optional<Descuento> existente =
//                descuentoRepository.findDescuentoActivo(juego.get(), LocalDateTime.now());
//        if (existente.isPresent()) {
//            throw new AccesoDenegadoException("Ya existe un descuento activo para este juego");
//        }

        Descuento descuento = Descuento.builder()
                .porcentaje(dto.getPorcentaje())
                .fechaInicio(dto.getFechaInicio())
                .fechaFin(dto.getFechaFin())
                .desarrolladora(desarrolladora)
                .juego(juego.get())
                .activo(true)
                .build();

        descuentoRepository.save(descuento);

        generarNotificaciones(descuento);

        return mapper.toResponseDTO(descuento);
    }

    @Override
    public void generarNotificaciones(Descuento descuento) {

        Juego juego = descuento.getJuego();

        List<Perfil> perfiles =
                perfilRepository.findByFavoritosContaining(juego);

        for (Perfil perfil : perfiles) {

            Notificacion notificacion = Notificacion.builder()
                    .mensaje("El juego " + juego.getNombre()
                            + " tiene un "
                            + descuento.getPorcentaje()
                            + "% de descuento!")
                    .perfil(perfil)
                    .fecha(LocalDateTime.now())
                    .leida(false)
                    .build();

            notificacionRepository.save(notificacion);
        }
    }

    @Override
    public int getDescuentoByGameId(Long gameId) {
        int total = descuentoRepository
                .getDescuentoTotalActivo(gameId, LocalDateTime.now());
        return Math.min(total, 100);
    }
}
