package com.proyecto.tiendaVirtual.compra.event;

import com.proyecto.tiendaVirtual.compra.model.Compra;

public class CompraRealizadaEvent {
    private final Compra compra;

    public CompraRealizadaEvent(Compra compra) {
        this.compra = compra;
    }

    public Compra getCompra() {
        return compra;
    }
}
