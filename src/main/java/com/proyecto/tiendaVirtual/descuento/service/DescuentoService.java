package com.proyecto.tiendaVirtual.descuento.service;

import com.proyecto.tiendaVirtual.descuento.dto.CreateDescuentoDTO;
import com.proyecto.tiendaVirtual.descuento.dto.DescuentoResponseDTO;
import com.proyecto.tiendaVirtual.descuento.model.Descuento;
import org.springframework.stereotype.Service;

@Service
public interface DescuentoService {
    public DescuentoResponseDTO crearDescuento(CreateDescuentoDTO dto);
    public void generarNotificaciones(Descuento descuento);
}
