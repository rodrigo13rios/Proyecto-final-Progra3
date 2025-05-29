package com.proyecto.tiendaVirtual.perfil.model;

import com.proyecto.tiendaVirtual.juego.model.Juego;
import com.proyecto.tiendaVirtual.user.model.User;
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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nickName;

    @OneToOne
    @JoinColumn(name = "id_usuario")
    private User user;

    @ManyToMany
    @JoinTable(
            name = "perfil_juego",
            joinColumns = @JoinColumn(name = "perfil_id"),
            inverseJoinColumns = @JoinColumn(name = "juego_id")
    )
    private List<Juego> juegos = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "perfil_amigos",
            joinColumns = @JoinColumn(name = "perfil_id"),
            inverseJoinColumns = @JoinColumn(name = "amigo_id")
    )
    private List<Perfil> amigos = new ArrayList<>();
}
