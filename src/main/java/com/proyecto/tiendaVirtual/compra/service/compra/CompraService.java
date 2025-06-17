package com.proyecto.tiendaVirtual.compra.service.compra;

import com.proyecto.tiendaVirtual.compra.model.Compra;
import com.proyecto.tiendaVirtual.compra.repository.CompraRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CompraService {
    void createCompra(String email);
    List<Compra> getCompraByCliente(String email);



}
