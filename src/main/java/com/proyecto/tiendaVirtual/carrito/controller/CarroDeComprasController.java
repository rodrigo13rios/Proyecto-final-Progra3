package com.proyecto.tiendaVirtual.carrito.controller;

import com.proyecto.tiendaVirtual.carrito.dto.CarroDeComprasDTO;
import com.proyecto.tiendaVirtual.carrito.model.CarroDeCompras;
import com.proyecto.tiendaVirtual.carrito.service.CarroDeComprasService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/carrito")
public class CarroDeComprasController {

    @Autowired
    private CarroDeComprasService service;


    // Obtener el carrito del usuario logeado
    @GetMapping
    public ResponseEntity<CarroDeCompras> getCarrito() {
        CarroDeCompras carro = service.getCarroByCliente();
        return ResponseEntity.ok(carro);
    }


    // Agregar un juego
    @PostMapping("/add")
    public ResponseEntity<CarroDeCompras> addJuegoAlCarro(
            @RequestBody @Valid CarroDeComprasDTO dto) {

        CarroDeCompras carro = service.addJuego(dto);
        return new ResponseEntity<>(carro, HttpStatus.CREATED);
    }


    // Eliminar un juego por ID
    @DeleteMapping("/remove/{id_juego}")
    public ResponseEntity<Void> removeItem(@PathVariable("id_juego") Long idJuego) {
        service.removeJuego_Id(idJuego);
        return ResponseEntity.noContent().build();
    }


    // Vaciar carrito
    @DeleteMapping("/clear")
    public ResponseEntity<Void> limpiarCarro() {
        service.clearCarroDeCompras();
        return ResponseEntity.noContent().build();
    }
}
