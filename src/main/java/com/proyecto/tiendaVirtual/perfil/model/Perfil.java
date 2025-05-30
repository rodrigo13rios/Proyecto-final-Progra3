package com.proyecto.tiendaVirtual.perfil.model;

import com.proyecto.tiendaVirtual.juego.model.Juego;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "perfiles")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Perfil {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nickName;

    @OneToOne
    @JoinColumn(name = "id_usuario")
    //private Cuenta cuenta;


    @ManyToMany
    @JoinTable(
            name = "perfil_juego",
            joinColumns = @JoinColumn(name = "id_perfil"),
            inverseJoinColumns = @JoinColumn(name = "id_juego")
    )
    private List<Juego> juegos = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "perfil_amigos",
            joinColumns = @JoinColumn(name = "id_perfil"),
            inverseJoinColumns = @JoinColumn(name = "id_perfil_amigo")
    )
    private List<Perfil> amigos = new ArrayList<>();
}
