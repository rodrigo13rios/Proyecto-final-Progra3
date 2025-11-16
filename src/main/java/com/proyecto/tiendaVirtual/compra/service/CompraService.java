package com.proyecto.tiendaVirtual.compra.service;

import com.proyecto.tiendaVirtual.compra.dto.CompraDTO;
import com.proyecto.tiendaVirtual.compra.model.Compra;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CompraService {
    void createCompra();
    List<CompraDTO> getCompraByCliente();
    List<CompraDTO> getCompraByCliente_Id(Long id);
    List<CompraDTO> getAll();
    CompraDTO conventirACompraDTO(Compra compra);
}
