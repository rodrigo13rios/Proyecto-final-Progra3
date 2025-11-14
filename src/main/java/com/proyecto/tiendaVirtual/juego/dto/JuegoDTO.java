package com.proyecto.tiendaVirtual.juego.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.proyecto.tiendaVirtual.juego.model.Categoria;
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
public class JuegoDTO {
    @NotNull(message = "El nombre no puede ser nulo")
    @Size(max = 50, message = "El nombre del juego no puede exceder de 50 caracteres")
    private String nombre;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaLanzamiento;

    @NotNull(message = "El precio no puede estar vacío")
    @Min(value =0, message = "La cantidad unitaria no puede ser menor a 0")
    @Max(value = 999999999, message = "el valor no puede ser mayor a 999999999")
    private Double precio;

    @NotNull(message = "La categoría no puede ser nula")
    private String categoria;

    @NotNull(message = "Debe ingresar una URL para la foto")
    private String foto;
}
