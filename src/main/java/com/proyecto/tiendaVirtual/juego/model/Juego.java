package com.proyecto.tiendaVirtual.juego.model;

import com.proyecto.tiendaVirtual.desarrolladora.model.Desarrolladora;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Juegos")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Juego {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private Integer anio;
    private Double precio;
    private Categoria categoria;
    @ManyToOne
    private Desarrolladora desarrolladora;
}
