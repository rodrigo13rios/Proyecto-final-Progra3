package com.proyecto.tiendaVirtual.carrito.service;

import com.proyecto.tiendaVirtual.carrito.dto.CarroDeComprasDTO;
import com.proyecto.tiendaVirtual.carrito.dto.NombreJuegoDTO;
import com.proyecto.tiendaVirtual.carrito.model.CarroDeCompras;
import com.proyecto.tiendaVirtual.compra.model.Compra;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CarroDeComprasService {
    CarroDeComprasDTO getCarroByClienteDTO();
    void clearCarroDeCompras();
    void removeJuego_Id(Long id_juego);
    CarroDeCompras addJuego(NombreJuegoDTO nombreJuego);
    Long getCountByCliente_Id();
    CarroDeComprasDTO convertirACarroDTO(CarroDeCompras compras);
    CarroDeCompras getCarroByCliente();

}
