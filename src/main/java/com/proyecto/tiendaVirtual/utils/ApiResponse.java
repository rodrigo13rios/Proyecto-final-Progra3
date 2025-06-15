package com.proyecto.tiendaVirtual.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.proyecto.tiendaVirtual.dto.ErrorDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)//Cuando convierte a jason omite los campos nulls
public class ApiResponse<T> {

    //Esta clase envuelve las respuestas de la api en la siguiente estructura
    private String status;
    private List<ErrorDTO> errors;
    private T results;

    public ApiResponse(T results) {
        this.results = results;
    }
}
