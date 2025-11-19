package com.proyecto.tiendaVirtual.compraDetalle.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.proyecto.tiendaVirtual.compra.model.Compra;
import com.proyecto.tiendaVirtual.juego.model.Juego;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "compras_detalle")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CompraDetalle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "juego_id")
    private Juego juego;

    private Double total;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "compra_id")
    @JsonIgnore
    private Compra compra;

    public CompraDetalle(Compra compra, Juego juego, Double total) {
        this.compra = compra;
        this.juego = juego;
        this.total = total;
    }
}
