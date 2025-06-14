package com.proyecto.tiendaVirtual.perfil.model;

import com.proyecto.tiendaVirtual.billetera.model.Billetera;
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
    @OneToOne (cascade = CascadeType.ALL)
    @JoinColumn(name = "billetera_id")
    private Billetera billetera;


    @ManyToMany
    @JoinTable(
            name = "perfil_juego",
            joinColumns = @JoinColumn(name = "id_perfil"),
            inverseJoinColumns = @JoinColumn(name = "id_juego")
    )
    private List<Juego> juegos = new ArrayList<>();
}
