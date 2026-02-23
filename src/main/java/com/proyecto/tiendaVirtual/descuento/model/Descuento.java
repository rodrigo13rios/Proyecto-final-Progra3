package com.proyecto.tiendaVirtual.descuento.model;

import com.proyecto.tiendaVirtual.desarrolladora.model.Desarrolladora;
import com.proyecto.tiendaVirtual.juego.model.Juego;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "descuentos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Descuento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer porcentaje;

    @Column(nullable = false)
    private LocalDateTime fechaInicio;

    @Column(nullable = false)
    private LocalDateTime fechaFin;

    @ManyToOne(optional = false)
    @JoinColumn(name = "desarrolladora_id")
    private Desarrolladora desarrolladora;

    @ManyToOne(optional = false)
    @JoinColumn(name = "juego_id")
    private Juego juego;

    private Boolean activo = true;
}
