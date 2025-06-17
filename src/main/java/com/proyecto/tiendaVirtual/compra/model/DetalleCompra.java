package com.proyecto.tiendaVirtual.compra.model;

import com.proyecto.tiendaVirtual.juego.model.Juego;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "detalle_compras")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetalleCompra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional=false, cascade = CascadeType.DETACH, fetch=FetchType.EAGER)
    private Juego juego;

    @ManyToOne(optional=false, cascade = CascadeType.DETACH, fetch=FetchType.EAGER)
    private Compra compra;

    @Min(value =0, message = "La cantidad unitaria no puede ser menor a 0")
    @Max(value = 999999999, message = "el valor no puede ser mayor a 999999999")
    @NotNull(message = "La cantidad no puede estar vacio")
    private int cantidad;


}
