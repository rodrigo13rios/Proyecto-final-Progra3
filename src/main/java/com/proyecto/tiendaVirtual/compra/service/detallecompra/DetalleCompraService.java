package com.proyecto.tiendaVirtual.compra.service.detallecompra;

import com.proyecto.tiendaVirtual.compra.model.DetalleCompra;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface DetalleCompraService {
    public void crear(DetalleCompra detalleCompra);
    List<DetalleCompra> getByCompra(Long id_Compra);
}
