package com.proyecto.tiendaVirtual.desarrolladora.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.proyecto.tiendaVirtual.juego.model.Juego;
import com.proyecto.tiendaVirtual.user.model.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "desarrolladoras")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Desarrolladora {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 2, max = 50)
    private String nombre;
    @Size(min = 2, max = 50)
    private String paisOrigen;

    @OneToMany(mappedBy = "desarrolladora")
    @JsonIgnore
    private List<Juego> juegos = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "user_id",nullable = false)
    @JsonIgnore
    private User user;
}
