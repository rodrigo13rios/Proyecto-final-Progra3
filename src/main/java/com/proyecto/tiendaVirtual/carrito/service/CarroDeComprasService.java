package com.proyecto.tiendaVirtual.carrito.service;

import com.proyecto.tiendaVirtual.carrito.dto.CarroDeComprasDTO;
import com.proyecto.tiendaVirtual.carrito.model.CarroDeCompras;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CarroDeComprasService {
    CarroDeCompras getCarroByCliente();
    void clearCarroDeCompras();
    void removeJuego_Id(Long id_juego);
    CarroDeCompras addJuego(CarroDeComprasDTO dto);
    Long getCountByCliente_Id();
}
