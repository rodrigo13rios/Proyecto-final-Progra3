package com.proyecto.tiendaVirtual.juego.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JuegoUpdateDTO {
    @Size(max = 50, message = "El nombre del juego no puede exceder de 50 caracteres")
    private String nombre;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaLanzamiento;

    @Min(value =0, message = "La cantidad unitaria no puede ser menor a 0")
    @Max(value = 999999999, message = "el valor no puede ser mayor a 999999999")
    private Double precio;

    private String categoria;

    @NotNull(message = "Debe ingresar una URL para la foto")
    private String foto;
}
