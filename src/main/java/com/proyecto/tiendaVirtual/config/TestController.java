package com.proyecto.tiendaVirtual.config;

import com.proyecto.tiendaVirtual.user.dto.UserVerDTO;
import com.proyecto.tiendaVirtual.user.model.User;
import com.proyecto.tiendaVirtual.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/test")
public class TestController {
    @Autowired
    UserRepository userRepo;


    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("Autenticado correctamente!");
    }

    @GetMapping("/getUsers")
    public ResponseEntity<List<User>> getAll(){
        List<User> users = userRepo.findAll();
        return ResponseEntity.ok(users);
    }
}
