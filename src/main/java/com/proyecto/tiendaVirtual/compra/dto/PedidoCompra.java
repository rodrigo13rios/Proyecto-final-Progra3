package com.proyecto.tiendaVirtual.compra.dto;

import lombok.Data;

import java.util.List;

@Data
public class PedidoCompra {
    private List<Long> gameIds;
}
