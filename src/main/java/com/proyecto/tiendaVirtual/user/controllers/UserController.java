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

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserDTO userDTO){
        User result = service.createUser(userDTO);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<UserVerDTO>> getAll(){
        List<UserVerDTO> users = service.getAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserVerDTO> getById(@PathVariable Long id){
        User optional = service.getById(id).orElseThrow(()-> new ElementoNoEncontradoException("No se encontro el perfil"));
        UserVerDTO userVerDTO = service.convertirAVerDTO(optional);
        return ResponseEntity.ok(userVerDTO);
    }

    @GetMapping("/{id}/email")
    public ResponseEntity<UserVerDTO> getByEmail(@PathVariable String email){
        User optional = service.getByEmail(email).orElseThrow(()-> new ElementoNoEncontradoException("No se encontro el perfil"));
        UserVerDTO userVerDTO = service.convertirAVerDTO(optional);
        return ResponseEntity.ok(userVerDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserVerDTO> update(@PathVariable Long id, @RequestBody UserUpdateDTO nuevo){
        User actualizado = service.update(id,nuevo);
        return ResponseEntity.ok(service.convertirAVerDTO(actualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
