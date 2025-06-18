package com.proyecto.tiendaVirtual.juego.controller;


import com.proyecto.tiendaVirtual.exceptions.ElementoNoEncontradoException;
import com.proyecto.tiendaVirtual.juego.dto.JuegoDTO;
import com.proyecto.tiendaVirtual.juego.dto.JuegoUpdateDTO;
import com.proyecto.tiendaVirtual.juego.model.Juego;
import com.proyecto.tiendaVirtual.juego.service.JuegoService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("api/juego")
public class JuegoController {
    @Autowired
    private JuegoService service;

//    Create
    @PostMapping
    public ResponseEntity<Juego> createJuego(@Valid @RequestBody JuegoDTO juegoDTO){
        Juego result = service.create(juegoDTO);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

//    Update
    @PutMapping("/{id}")
    public ResponseEntity<Juego> actualizar(@PathVariable Long id, @Valid @RequestBody JuegoUpdateDTO dto) {
        Juego actualizado = service.update(id, dto);
        return ResponseEntity.ok(actualizado);
    }

//    Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarJuego(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

//    Get ALL
    @GetMapping
    public ResponseEntity<List<Juego>> getAll(){
        List<Juego> juegos = service.getAll();
        return ResponseEntity.ok(juegos);
    }

//    Get By ID
    @GetMapping("/{id}")
    public ResponseEntity<Juego> getById(@PathVariable Long id){
        Juego juego = service.getById(id).orElseThrow(()-> new ElementoNoEncontradoException("No se encontró el juego con id:"+id));
        return ResponseEntity.ok(juego);
    }

//    Get By Nombre
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<Juego> getByNombre(@PathVariable String nombre){
        Juego juego = service.getByNombre(nombre).orElseThrow(()-> new ElementoNoEncontradoException("No se encontró el juego con el nombre '"+nombre));
        return ResponseEntity.ok(juego);
    }

//    Get All By Categoria
    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<Juego>> getByCategoria(@PathVariable String categoria){
        List<Juego> juegos = service.getByCategoria(categoria);
        return ResponseEntity.ok(juegos);
    }
}
