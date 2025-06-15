package com.proyecto.tiendaVirtual.perfil.controller;

import com.proyecto.tiendaVirtual.exceptions.ElementoNoEncontradoException;
import com.proyecto.tiendaVirtual.juego.model.Juego;
import com.proyecto.tiendaVirtual.perfil.dto.PerfilDTO;
import com.proyecto.tiendaVirtual.perfil.model.Perfil;
import com.proyecto.tiendaVirtual.perfil.service.PerfilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/perfil")
public class PerfilController {
    @Autowired
    private PerfilService service;

    @GetMapping
    public ResponseEntity<List<Perfil>> getAll(){
        List<Perfil> perfiles = service.getAll();
        return ResponseEntity.ok(perfiles);
    }

    @GetMapping("/{nickName}/nickName")
    public ResponseEntity<Perfil> getByNickName(@PathVariable String nickName){
        Perfil optional = service.getByNickName(nickName)
                        .orElseThrow(()-> new ElementoNoEncontradoException("No se encuentra un perfil con el nickName "+nickName));
        return ResponseEntity.ok(optional);
    }

    @GetMapping("/{id}/juegos")
    public ResponseEntity<List<Juego>> obtenerJuegos(@PathVariable Long id){
        List<Juego> juegos = service.obtenerJuegos(id);
        return ResponseEntity.ok(juegos);
    }

//    @PostMapping
//    public ResponseEntity<Perfil> create(@RequestBody PerfilDTO dto){
//        Perfil result = service.create(dto);
//        return new ResponseEntity<>(result, HttpStatus.CREATED);
//    }

    @PutMapping("/{id}")
    public ResponseEntity<Perfil> update(@PathVariable Long id, @RequestBody PerfilDTO perfil){
        Perfil result = service.update(id, perfil);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete (@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
