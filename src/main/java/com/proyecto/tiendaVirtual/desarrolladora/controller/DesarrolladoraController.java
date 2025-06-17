package com.proyecto.tiendaVirtual.desarrolladora.controller;

import com.proyecto.tiendaVirtual.desarrolladora.model.Desarrolladora;
import com.proyecto.tiendaVirtual.desarrolladora.service.DesarrolladoraService;
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

    @GetMapping("/get")
    public List<Desarrolladora> getAll() {
        return service.getAll();
    }

    @PostMapping("/create")
    public ResponseEntity<Desarrolladora> create(@RequestBody Desarrolladora desarrolladora) {
        Desarrolladora result = service.create(desarrolladora);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Desarrolladora> update(@PathVariable Long id, @RequestBody Desarrolladora desarrolladora) {
        Desarrolladora result = service.update(id,desarrolladora);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
