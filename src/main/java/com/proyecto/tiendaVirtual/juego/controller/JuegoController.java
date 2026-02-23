package com.proyecto.tiendaVirtual.juego.controller;


import com.proyecto.tiendaVirtual.exceptions.ElementoNoEncontradoException;
import com.proyecto.tiendaVirtual.juego.dto.JuegoDTO;
import com.proyecto.tiendaVirtual.juego.dto.JuegoUpdateDTO;
import com.proyecto.tiendaVirtual.juego.dto.JuegoVerDTO;
import com.proyecto.tiendaVirtual.juego.model.Juego;
import com.proyecto.tiendaVirtual.juego.service.JuegoService;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/juego")
public class JuegoController {
    @Autowired
    private JuegoService service;

//    Create
    @PostMapping
    public ResponseEntity<JuegoVerDTO> createJuego(@Valid @RequestBody JuegoDTO juegoDTO){
        JuegoVerDTO result = service.convertirAVerDTO(service.create(juegoDTO));
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

//    Update
    @PutMapping("/{id}")
    public ResponseEntity<JuegoVerDTO> actualizar(@PathVariable Long id, @Valid @RequestBody JuegoUpdateDTO dto) {
        JuegoVerDTO actualizado = service.convertirAVerDTO(service.update(id, dto));
        return ResponseEntity.ok(actualizado);
    }


//    Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarJuego(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build(); // 204 No Content

    }

//    Get ALL (get page)
    @GetMapping
    public ResponseEntity<Page<JuegoVerDTO>> getAll(
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false) String search,
            @PageableDefault(size = 20) Pageable pageable)
    {
        Page<JuegoVerDTO> juegos = service.getAll(categoria, search, pageable);
        return ResponseEntity.ok(juegos);
    }



//    Get By ID
    @GetMapping("/{id}")
    public ResponseEntity<JuegoVerDTO> getById(@PathVariable Long id){
        JuegoVerDTO juego = service.convertirAVerDTO(
                service.getById(id).orElseThrow(
                        ()-> new ElementoNoEncontradoException("No se encontró el juego con id:"+id)
                )
        );
        return ResponseEntity.ok(juego);
    }

//    Get By Nombre
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<JuegoVerDTO> getByNombre(@PathVariable String nombre){
        JuegoVerDTO juego = service.getByNombre(nombre).orElseThrow(()-> new ElementoNoEncontradoException("No se encontró el juego con el nombre '"+nombre));
        return ResponseEntity.ok(juego);
    }
}
