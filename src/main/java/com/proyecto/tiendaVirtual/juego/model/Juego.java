package com.proyecto.tiendaVirtual.juego.model;

import com.proyecto.tiendaVirtual.desarrolladora.model.Desarrolladora;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "juegos")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Juego {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private LocalDate fechaLanzamiento;
    private Double precio;
    private Categoria categoria;

    @ManyToOne
    @JoinColumn(name = "id_desarrolladora", nullable = false)
    private Desarrolladora desarrolladora;
}
