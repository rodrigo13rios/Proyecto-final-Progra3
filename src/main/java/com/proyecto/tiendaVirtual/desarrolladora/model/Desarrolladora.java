package com.proyecto.tiendaVirtual.desarrolladora.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.proyecto.tiendaVirtual.juego.model.Juego;
import com.proyecto.tiendaVirtual.user.model.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    @NotNull(message = "El nombre no puede ser nulo")
    @Size(max = 40, message = "El nombre del juego no puede exceder de 50 caracteres")
    private String nombre;
    @NotNull(message = "El nombre no puede ser nulo")
    @Size(max = 40, message = "El nombre del juego no puede exceder de 40 caracteres")
    private String paisOrigen;

    @OneToMany(mappedBy = "desarrolladora")
    @JsonIgnore
    private List<Juego> juegos = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "user_id",nullable = false)
    @JsonIgnore
    private User user;
}
