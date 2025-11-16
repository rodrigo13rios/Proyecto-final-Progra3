package com.proyecto.tiendaVirtual.carrito.service;

import com.proyecto.tiendaVirtual.carrito.dto.CarroDeComprasDTO;
import com.proyecto.tiendaVirtual.carrito.dto.JuegoToCarroDTO;
import com.proyecto.tiendaVirtual.carrito.dto.NombreJuegoDTO;
import com.proyecto.tiendaVirtual.carrito.model.CarroDeCompras;
import com.proyecto.tiendaVirtual.carrito.repository.CarroDeComprasRepository;
import com.proyecto.tiendaVirtual.compra.dto.CompraDTO;
import com.proyecto.tiendaVirtual.compra.model.Compra;
import com.proyecto.tiendaVirtual.exceptions.ElementoNoEncontradoException;
import com.proyecto.tiendaVirtual.exceptions.ElementoYaExistenteException;
import com.proyecto.tiendaVirtual.juego.model.Juego;
import com.proyecto.tiendaVirtual.juego.repository.JuegoRepository;
import com.proyecto.tiendaVirtual.user.model.User;
import com.proyecto.tiendaVirtual.user.repository.UserRepository;
import com.proyecto.tiendaVirtual.utils.SecurityUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarroDeComprasServiceImp implements CarroDeComprasService {

    @Autowired
    private CarroDeComprasRepository carroDeCompraRepository;
    @Autowired
    private JuegoRepository juegoRepository;
    @Autowired
    private SecurityUtils securityUtils;


    // get carrito del usuario logeado
    public CarroDeComprasDTO getCarroByClienteDTO() {
        User cliente = securityUtils.getLoggedUser();

        CarroDeCompras compras=carroDeCompraRepository.findByCliente_Id(cliente.getId())
                .orElseThrow(() -> new ElementoNoEncontradoException("El carrito está vacío o no existe"));
        return convertirACarroDTO(compras);
    }

    // Obtener carrito del usuario logeado
    public CarroDeCompras getCarroByCliente() {
        User cliente = securityUtils.getLoggedUser(); return carroDeCompraRepository.findByCliente_Id(cliente.getId())
                .orElseThrow(() -> new ElementoNoEncontradoException("El carrito está vacío o no existe")); }

    // Vaciar carrito
    @Transactional
    public void clearCarroDeCompras() {
        CarroDeCompras carro = getCarroByCliente();
        carro.getJuegos().clear();
        carroDeCompraRepository.save(carro);
    }


    // Eliminar un juego del carrito
    @Transactional
    public void removeJuego_Id(Long idJuego) {
        CarroDeCompras carro = getCarroByCliente();

        boolean eliminado = carro.getJuegos().removeIf(j -> j.getId().equals(idJuego));
        if (!eliminado) {
            throw new ElementoNoEncontradoException("El juego no está en el carrito");
        }

        carroDeCompraRepository.save(carro);
    }


    // Agregar juego al carrito
    @Transactional
    public CarroDeCompras addJuego(NombreJuegoDTO nombreJugo) {

        User cliente = securityUtils.getLoggedUser();
        Juego juego = juegoRepository.findByNombre(nombreJugo.getNombreJuego())
                .orElseThrow(() -> new ElementoNoEncontradoException("Juego no encontrado"));

        // Busca carrito del usuario
        CarroDeCompras carro = carroDeCompraRepository.findByCliente_Id(cliente.getId())
                .orElseGet(() -> {
                    // Si no existe, crea uno nuevo
                    CarroDeCompras nuevo = new CarroDeCompras();
                    nuevo.setCliente(cliente);
                    return carroDeCompraRepository.save(nuevo);
                });

        // Validación para que NO se repita el juego
        if (carro.getJuegos().contains(juego)) {
            throw new IllegalArgumentException("El juego ya está en el carrito");
        }
        if (cliente.getPerfil().getJuegos().contains(juego)){
            throw new ElementoYaExistenteException("Ya tienes el juego en tu biblioteca");
        }

        // Agrega el juego
        carro.getJuegos().add(juego);
        return carroDeCompraRepository.save(carro);
    }


    // Contar juegos del carrito
    public Long getCountByCliente_Id() {
        CarroDeCompras carro = getCarroByCliente();
        return (long) carro.getJuegos().size();
    }

    @Override
    public CarroDeComprasDTO convertirACarroDTO(CarroDeCompras compra) {

        List<JuegoToCarroDTO> juegosDTO = compra.getJuegos()
                .stream()
                .map(juego -> new JuegoToCarroDTO(
                        juego.getNombre(),
                        juego.getPrecio(),
                        juego.getFoto()
                ))
                .toList();

        return new CarroDeComprasDTO(compra.getId(),juegosDTO);
    }



}