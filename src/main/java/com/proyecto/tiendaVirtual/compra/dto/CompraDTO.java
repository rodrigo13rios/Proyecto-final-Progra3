package com.proyecto.tiendaVirtual.compra.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompraDTO {
    private String nombreCliente;
    private String apellidoCliente;
    private LocalDate fechaDeCompra;
    private double total;
}
