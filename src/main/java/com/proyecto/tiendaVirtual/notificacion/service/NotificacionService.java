package com.proyecto.tiendaVirtual.notificacion.service;

import com.proyecto.tiendaVirtual.notificacion.dto.NotificacionResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface NotificacionService {
    public List<NotificacionResponseDTO> listar();
    public void leerTodas();
}
