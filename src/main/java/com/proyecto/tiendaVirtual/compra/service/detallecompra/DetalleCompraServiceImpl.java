package com.proyecto.tiendaVirtual.compra.service.detallecompra;

import com.proyecto.tiendaVirtual.compra.model.DetalleCompra;
import com.proyecto.tiendaVirtual.compra.repository.DetalleCompraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetalleCompraServiceImpl implements DetalleCompraService{
    @Autowired
    DetalleCompraRepository detalleCompraRepository;

    public void crear(DetalleCompra detalleCompra){
        this.detalleCompraRepository.save(detalleCompra);
    }
    public List<DetalleCompra> getByCompra(Long id_Compra){
        return detalleCompraRepository.findByCompra_Id(id_Compra);
    }


}
