package com.proyecto.tiendaVirtual.notificacion.model;

import com.proyecto.tiendaVirtual.perfil.model.Perfil;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "notificaciones")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notificacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String mensaje;

    private Boolean leida = false;

    private LocalDateTime fecha;

    @ManyToOne(optional = false)
    @JoinColumn(name = "perfil_id")
    private Perfil perfil;
}
