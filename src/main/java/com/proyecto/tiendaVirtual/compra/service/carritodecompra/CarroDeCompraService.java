package com.proyecto.tiendaVirtual.compra.service.carritodecompra;

import com.proyecto.tiendaVirtual.compra.model.CarroDeCompras;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CarroDeCompraService {

    List<CarroDeCompras> getCarroByCliente_nickName(String nickName);
    void clearCarroDeComprasByCliente_Id(Long id);
    void removeJuego_Id(Long id_juego);
    void addJuego(CarroDeCompras carroDeCompras);
    Long getCountByCliente_Id(Long id_clioente);
}
