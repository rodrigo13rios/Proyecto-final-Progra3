package com.proyecto.tiendaVirtual.desarrolladora.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DesarrolladoraDTO {
    private String nombre;
    private String pais;
    private String CEO;

    @Override
    public String toString() {
        return "DesarrolladoraDTO{" +
                "nombre='" + nombre + '\'' +
                ", pais='" + pais + '\'' +
                ", CEO='" + CEO + '\'' +
                '}';
    }
}
