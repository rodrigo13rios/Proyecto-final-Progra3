package com.proyecto.tiendaVirtual.carrito.controller;

import com.proyecto.tiendaVirtual.carrito.service.CarritoService;
import com.proyecto.tiendaVirtual.juego.dto.JuegoDTO;
import com.proyecto.tiendaVirtual.juego.dto.JuegoVerDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("api/carrito")
public class CarritoController {
    @Autowired
    private CarritoService service;

    @PostMapping("/{juegoId}")
    public ResponseEntity<List<JuegoVerDTO>> agregarJuego(@PathVariable Long juegoId){
        List<JuegoVerDTO> result = service.agregarJuego(juegoId);
        return ResponseEntity.ok(result);
    }

    @GetMapping
    public ResponseEntity<List<JuegoVerDTO>> getAll() {
        List<JuegoVerDTO> result = service.getAll();
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{juegoId}")
    public ResponseEntity<List<JuegoVerDTO>> borrarJuego(@PathVariable Long juegoId) {
        List<JuegoVerDTO> result = service.borrarJuego(juegoId);
        return ResponseEntity.ok(result);
    }


    @DeleteMapping
    public ResponseEntity<Void> clear() {
        service.limpiar();
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<Void> comprarTodo() {
        service.comprar();
        return ResponseEntity.ok().build();
    }
}

