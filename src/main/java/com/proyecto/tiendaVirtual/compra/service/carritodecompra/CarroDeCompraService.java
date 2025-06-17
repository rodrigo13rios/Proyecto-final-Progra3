package com.proyecto.tiendaVirtual.compra.service.carritodecompra;

import com.proyecto.tiendaVirtual.compra.dto.CarroDeComprasDTO;
import com.proyecto.tiendaVirtual.compra.model.CarroDeCompras;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CarroDeCompraService {

    List<CarroDeCompras> getCarroByCliente_Email(String email);
    void clearCarroDeComprasByCliente_Id(Long id);
    void removeJuego_Id(Long id_juego);
    CarroDeCompras addJuego(CarroDeComprasDTO dto,String nickname);
    Long getCountByCliente_Id(Long id_cliente);
}
