package com.proyecto.tiendaVirtual.carrito.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.proyecto.tiendaVirtual.juego.model.Juego;
import com.proyecto.tiendaVirtual.user.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "carro_de_compras")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarroDeCompras {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;


    @OneToOne
    @JoinColumn(name = "user_id",nullable = false)
    @JsonIgnore
    private User cliente;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "carrito_juegos",
            joinColumns = @JoinColumn(name = "carrito_id"),
            inverseJoinColumns = @JoinColumn(name = "juego_id")
    )
    private List<Juego> juegos = new ArrayList<>();

}
