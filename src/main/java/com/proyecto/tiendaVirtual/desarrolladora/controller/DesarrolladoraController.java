package com.proyecto.tiendaVirtual.desarrolladora.controller;

import com.proyecto.tiendaVirtual.desarrolladora.model.Desarrolladora;
import com.proyecto.tiendaVirtual.desarrolladora.service.DesarrolladoraService;
import com.proyecto.tiendaVirtual.exceptions.ElementoNoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/desarrolladora")
public class DesarrolladoraController {
    @Autowired
    private DesarrolladoraService service;

    /// No POST: Desarrolladora se crea desde User
    /// No DELETE: Desarrolladora se borra junto a User

//    Get ALL
    @GetMapping
    public List<Desarrolladora> getAll() {
        return service.getAll();
    }

//    Get By ID
    @GetMapping("/{id}")
    public ResponseEntity<Desarrolladora> getById(@PathVariable Long id) {
        Desarrolladora desarrolladora = service.findById(id)
                .orElseThrow(() -> new ElementoNoEncontradoException("No se encuentra una desarrolladora con ese ID"));
        return ResponseEntity.ok(desarrolladora);
    }

//    Update
    @PutMapping
    public ResponseEntity<Desarrolladora> update(@RequestBody Desarrolladora desarrolladora) {
        Desarrolladora result = service.update(desarrolladora);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
