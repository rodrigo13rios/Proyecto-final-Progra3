package com.proyecto.tiendaVirtual.juego.dto;


import com.proyecto.tiendaVirtual.desarrolladora.dto.DesarrolladoraDTO;
import com.proyecto.tiendaVirtual.juego.model.Categoria;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JuegoVerDTO {
    private Long id;
    private String nombre;
    private LocalDate fechaLanzamiento;
    private Double precio;
    private Categoria categoria;
    private String foto;
    private DesarrolladoraDTO desarrolladora;
}

