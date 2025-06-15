package com.proyecto.tiendaVirtual.perfil.model;

import com.proyecto.tiendaVirtual.billetera.model.Billetera;
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
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nickName;
    @OneToOne (cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "billetera_id")
    private Billetera billetera;

    @OneToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @ManyToMany
    @JoinTable(
            name = "perfil_juego",
            joinColumns = @JoinColumn(name = "id_perfil"),
            inverseJoinColumns = @JoinColumn(name = "id_juego")
    )
    private List<Juego> juegos = new ArrayList<>();
}
