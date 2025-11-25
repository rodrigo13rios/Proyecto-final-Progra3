package com.proyecto.tiendaVirtual.desarrolladora.dto;

import com.proyecto.tiendaVirtual.juego.dto.JuegoVerDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EstadisticaJuegoDTO {
    private JuegoVerDTO juego;
    private Long ventas;
    private Long favoritos;
}
