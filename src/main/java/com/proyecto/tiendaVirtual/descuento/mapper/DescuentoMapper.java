package com.proyecto.tiendaVirtual.descuento.mapper;

import com.proyecto.tiendaVirtual.descuento.dto.DescuentoResponseDTO;
import com.proyecto.tiendaVirtual.descuento.model.Descuento;
import com.proyecto.tiendaVirtual.juego.model.Juego;
import org.springframework.stereotype.Component;

@Component
public class DescuentoMapper {
    public DescuentoResponseDTO toResponseDTO(Descuento descuento){
        return  DescuentoResponseDTO.builder()
                .id(descuento.getId())
                .porcentaje(descuento.getPorcentaje())
                .fechaInicio(descuento.getFechaInicio())
                .fechaFin(descuento.getFechaFin())
                .activo(descuento.getActivo())
                .juegoId(descuento.getJuego().getId())
                .build();
    }
}
