package com.proyecto.tiendaVirtual.compra.service;

import com.proyecto.tiendaVirtual.compra.dto.PedidoCompra;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CompraService {
    void comprar(List<Long> juegosIds);
}
