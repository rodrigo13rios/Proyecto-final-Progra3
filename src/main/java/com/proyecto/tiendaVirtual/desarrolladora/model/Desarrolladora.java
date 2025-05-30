package com.proyecto.tiendaVirtual.desarrolladora.model;

import com.proyecto.tiendaVirtual.juego.model.Juego;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "desarrolladoras")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Desarrolladora {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String paisOrigen;

    @OneToMany(mappedBy = "desarrolladora")
    private List<Juego> juegos = new ArrayList<>();

}
