package com.proyecto.tiendaVirtual.notificacion.service;

import com.proyecto.tiendaVirtual.desarrolladora.model.Desarrolladora;
import com.proyecto.tiendaVirtual.exceptions.ElementoNoEncontradoException;
import com.proyecto.tiendaVirtual.notificacion.dto.NotificacionResponseDTO;
import com.proyecto.tiendaVirtual.notificacion.model.Notificacion;
import com.proyecto.tiendaVirtual.notificacion.repository.NotificacionRepository;
import com.proyecto.tiendaVirtual.perfil.model.Perfil;
import com.proyecto.tiendaVirtual.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class NotificacionServiceImpl implements NotificacionService {
    @Autowired
    private NotificacionRepository repo;
    @Autowired
    private SecurityUtils securityUtils;

    @Override
    @Transactional(readOnly = true)
    public List<NotificacionResponseDTO> listar() {
        Perfil perfil = securityUtils.getLoggedUser().getPerfil();
        if (perfil == null) {
            throw new ElementoNoEncontradoException(
                    "No se ha podido obtener el Perfil del User logeado"
            );
        }
        return repo.findByPerfilIdOrderByFechaDesc(perfil.getId())
                .stream()
                .map(NotificacionResponseDTO::new)
                .toList();
    }

    @Override
    @Transactional
    public void leerTodas() {
        Perfil perfil = securityUtils.getLoggedUser().getPerfil();
        if (perfil == null) {
            throw new ElementoNoEncontradoException(
                    "No se ha podido obtener el Perfil del User logeado"
            );
        }
        repo.marcarTodasComoLeidas(perfil.getId());
    }
}
