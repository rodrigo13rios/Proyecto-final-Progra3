package com.proyecto.tiendaVirtual.user.controllers;

import com.proyecto.tiendaVirtual.exceptions.ElementoNoEncontradoException;
import com.proyecto.tiendaVirtual.user.dto.UserDTO;
import com.proyecto.tiendaVirtual.user.dto.UserUpdateDTO;
import com.proyecto.tiendaVirtual.user.dto.UserVerDTO;
import com.proyecto.tiendaVirtual.user.model.User;
import com.proyecto.tiendaVirtual.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService service;

//    Create
    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody UserDTO userDTO){
        User result = service.createUser(userDTO);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

//    Get ALL
    @GetMapping
    public ResponseEntity<List<UserVerDTO>> getAll(){
        List<UserVerDTO> users = service.getAll();
        return ResponseEntity.ok(users);
    }

//    Get By ID
    @GetMapping("/{id}")
    public ResponseEntity<UserVerDTO> getById(@PathVariable Long id){
        User optional = service.getById(id).orElseThrow(()-> new ElementoNoEncontradoException("No se encontro el perfil"));
        UserVerDTO userVerDTO = service.convertirAVerDTO(optional);
        return ResponseEntity.ok(userVerDTO);
    }

//    Get By Email
    @GetMapping("/getByEmail/{email}")
    public ResponseEntity<UserVerDTO> getByEmail(@PathVariable String email){
        User optional = service.getByEmail(email).orElseThrow(()-> new ElementoNoEncontradoException("No se encontro el perfil"));
        UserVerDTO userVerDTO = service.convertirAVerDTO(optional);
        return ResponseEntity.ok(userVerDTO);
    }

//    Update
    @PutMapping
    public ResponseEntity<UserVerDTO> update(@RequestBody UserUpdateDTO nuevo){
        User actualizado = service.update(nuevo);
        return ResponseEntity.ok(service.convertirAVerDTO(actualizado));
    }

//    Delete
    @DeleteMapping
    public ResponseEntity<Void> delete(){
        service.delete();
        return ResponseEntity.noContent().build();
    }

}
