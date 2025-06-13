package com.proyecto.tiendaVirtual.perfil.controller;

import com.proyecto.tiendaVirtual.perfil.model.Perfil;
import com.proyecto.tiendaVirtual.perfil.service.PerfilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/perfil")
public class PerfilController {
    @Autowired
    private PerfilService service;

    @GetMapping
    public List<Perfil> getAll(){return service.getAll();}

    @PostMapping
    public ResponseEntity<Perfil> create(@RequestBody Perfil perfil){
        Perfil result = service.create(perfil);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PutMapping("/{id")
    public ResponseEntity<Perfil> update(@PathVariable Long id, @RequestBody Perfil perfil){
        Perfil result = service.update(id, perfil);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }
}
