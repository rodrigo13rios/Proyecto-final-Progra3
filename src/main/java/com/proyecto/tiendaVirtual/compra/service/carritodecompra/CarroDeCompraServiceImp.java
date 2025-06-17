package com.proyecto.tiendaVirtual.compra.service.carritodecompra;

import com.proyecto.tiendaVirtual.compra.dto.CarroDeComprasDTO;
import com.proyecto.tiendaVirtual.compra.model.CarroDeCompras;
import com.proyecto.tiendaVirtual.compra.repository.CarroDeCompraRepository;
import com.proyecto.tiendaVirtual.exceptions.ListaVaciaException;
import com.proyecto.tiendaVirtual.juego.model.Juego;
import com.proyecto.tiendaVirtual.juego.repository.JuegoRepository;
import com.proyecto.tiendaVirtual.perfil.model.Perfil;
import com.proyecto.tiendaVirtual.perfil.repository.PerfilRepository;
import com.proyecto.tiendaVirtual.user.model.User;
import com.proyecto.tiendaVirtual.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CarroDeCompraServiceImp implements CarroDeCompraService {
    @Autowired
    CarroDeCompraRepository carroDeCompraRepository;
    @Autowired
    JuegoRepository juegoRepository;
    @Autowired
    UserRepository userRepository;

    public List<CarroDeCompras> getCarroByCliente_Email(String email)throws ListaVaciaException{
        List<CarroDeCompras> compras=this.carroDeCompraRepository.findByCliente_Email(email);
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
    public CarroDeCompras addJuego(CarroDeComprasDTO dto,String email){
        Juego juego = juegoRepository.findByNombre(dto.getNombreJuego())
                .orElseThrow(() -> new RuntimeException("Juego no encontrado"));
        User cliente = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        CarroDeCompras carro = new CarroDeCompras();
        carro.setJuego(juego);
        carro.setCliente(cliente);
        carro.setCantidad(dto.getCantidad());
        return this.carroDeCompraRepository.save(carro);
    }

    public Long getCountByCliente_Id(Long id_cliente){
        return this.carroDeCompraRepository.countByCliente_Id(id_cliente);
    }
}
