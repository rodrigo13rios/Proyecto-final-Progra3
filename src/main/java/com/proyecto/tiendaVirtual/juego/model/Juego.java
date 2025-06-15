package com.proyecto.tiendaVirtual.juego.model;

import com.proyecto.tiendaVirtual.desarrolladora.model.Desarrolladora;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Size;
import lombok.NonNull;

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
    @Size(max = 40, message = "El nombre del juego no puede exceder de 40 caracteres")
    private String nombre;

    private LocalDate fechaLanzamiento;

    @Min(value =0, message = "La cantidad unitaria no puede ser menor a 0")
    @Max(value = 999999999, message = "el valor no puede ser mayor a 999999999")
    @NotNull(message = "el precio actual puede estar vacio")
    private Double precio;

    @Enumerated(EnumType.STRING)
    private Categoria categoria;

    @ManyToOne
    @JoinColumn(name = "id_desarrolladora", nullable = false)
    private Desarrolladora desarrolladora;
}
