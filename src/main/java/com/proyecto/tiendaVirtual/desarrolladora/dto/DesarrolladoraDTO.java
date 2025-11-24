package com.proyecto.tiendaVirtual.desarrolladora.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DesarrolladoraDTO {
    private Long id;
    private String nombre;
    private String pais;
    private String CEO;
}
