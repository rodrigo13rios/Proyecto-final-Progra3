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

@RestController
@RequestMapping("/api/perfil")
public class PerfilController {
    @Autowired
    private PerfilService service;

    /// No POST: Perfil se crea desde User
    /// No DELETE: Perfil se borra junto a User

//    Get ALL
    @GetMapping
    public ResponseEntity<List<Perfil>> getAll(){
        List<Perfil> perfiles = service.getAll();
        return ResponseEntity.ok(perfiles);
    }

//    Get By ID
    @GetMapping("/id/{id}")
    public ResponseEntity<Perfil> getById(@PathVariable Long id) {
        Perfil desarrolladora = service.getById(id)
                .orElseThrow(() -> new ElementoNoEncontradoException("No se encuentra una desarrolladora con ese ID"));
        return ResponseEntity.ok(desarrolladora);
    }

//    Get By Nickname
    @GetMapping("/{nickName}")
    public ResponseEntity<Perfil> getByNickName(@PathVariable String nickName){
        Perfil optional = service.getByNickName(nickName)
                        .orElseThrow(()-> new ElementoNoEncontradoException("No se encuentra un perfil con el nickName "+nickName));
        return ResponseEntity.ok(optional);
    }

//    Get Juegos By Perfil_ID
    @GetMapping("/juegos/{id}")
    public ResponseEntity<List<Juego>> getJuegosByPerfilId(@PathVariable Long id){
        List<Juego> juegos = service.obtenerJuegos(id);
        return ResponseEntity.ok(juegos);
    }

//    Get Juegos del Perfil loggeado
    @GetMapping("/juegos")
    public ResponseEntity<List<Juego>> getJuegos(){
        List<Juego> juegos = service.obtenerJuegos();
        return ResponseEntity.ok(juegos);
    }

//    Update
    @PutMapping
    public ResponseEntity<Perfil> update(@RequestBody PerfilDTO perfil){
        Perfil result = service.update(perfil);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }
}
