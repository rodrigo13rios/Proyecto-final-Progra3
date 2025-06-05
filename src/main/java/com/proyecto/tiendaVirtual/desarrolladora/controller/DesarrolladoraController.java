package com.proyecto.tiendaVirtual.desarrolladora.controller;

import com.proyecto.tiendaVirtual.desarrolladora.model.Desarrolladora;
import com.proyecto.tiendaVirtual.desarrolladora.service.DesarrolladoraService;
import com.proyecto.tiendaVirtual.exceptions.ElementoYaExistenteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/desarrolladora")
public class DesarrolladoraController {
    @Autowired
    private DesarrolladoraService service;

    @GetMapping
    public List<Desarrolladora> getAll() {
        return service.getAll();
    }

    @PostMapping
    public void create(@RequestBody Desarrolladora desarrolladora) {
        service.create(desarrolladora);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable Long id, @RequestBody Desarrolladora desarrolladora) {
        service.update(id,desarrolladora);
    }
}
