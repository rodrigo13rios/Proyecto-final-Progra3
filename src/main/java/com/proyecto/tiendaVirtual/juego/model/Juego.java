package com.proyecto.tiendaVirtual.juego.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.proyecto.tiendaVirtual.desarrolladora.model.Desarrolladora;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Table(name = "juegos")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Juego {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El nombre no puede ser nulo")
    @Size(max = 50, message = "El nombre del juego no puede exceder de 50 caracteres")
    private String nombre;
    @NotNull
    @PastOrPresent(message = "La fecha debe ser pasada u hoy")
    @JsonFormat(pattern = ("yyyy/MM/dd"))
    private LocalDate fechaLanzamiento;

    @Min(value =0, message = "La cantidad unitaria no puede ser menor a 0")
    @Max(value = 999999999, message = "el valor no puede ser mayor a 999999999")
    @NotNull(message = "el precio no puede estar vacio")
    private Double precio;

    @Enumerated(EnumType.STRING)
    private Categoria categoria;

    @NotNull
    private String foto;

    @ManyToOne
    @JoinColumn(name = "id_desarrolladora", nullable = false)
    private Desarrolladora desarrolladora;
}
