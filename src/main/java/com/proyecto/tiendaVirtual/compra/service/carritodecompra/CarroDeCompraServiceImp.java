package com.proyecto.tiendaVirtual.compra.service.carritodecompra;

import com.proyecto.tiendaVirtual.compra.model.CarroDeCompras;
import com.proyecto.tiendaVirtual.compra.repository.CarroDeCompraRepository;
import com.proyecto.tiendaVirtual.exceptions.ListaVaciaException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CarroDeCompraServiceImp implements CarroDeCompraService {
    @Autowired
    CarroDeCompraRepository carroDeCompraRepository;

    public List<CarroDeCompras> getCarroByCliente_nickName(String nickName)throws ListaVaciaException{
        List<CarroDeCompras> compras=this.carroDeCompraRepository.findByCliente_nickName(nickName);
        if (compras.isEmpty()){
            throw new ListaVaciaException("La lista se encuentra vacia");
        }
        return compras;
    }

    public void clearCarroDeComprasByCliente_Id(Long id){
        this.carroDeCompraRepository.deleteByCliente_Id(id);
    }
    @Transactional
    public void removeJuego_Id(Long id_juego){
        this.carroDeCompraRepository.deleteById(id_juego);

    }
    public void addJuego(CarroDeCompras carroDeCompras){
        this.carroDeCompraRepository.save(carroDeCompras);
    }

    public Long getCountByCliente_Id(Long id_cliente){
        return this.carroDeCompraRepository.countByCliente_Id(id_cliente);
    }
}
