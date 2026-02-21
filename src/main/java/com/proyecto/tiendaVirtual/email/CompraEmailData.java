package com.proyecto.tiendaVirtual.email;

import com.proyecto.tiendaVirtual.juego.model.Juego;

import java.util.List;

public record CompraEmailData(
        String email,
        String nickname,
        Double total,
        List<Juego> juegos
) {}

