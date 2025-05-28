package com.proyecto.tiendaVirtual.desarrolladora.controller;

import com.proyecto.tiendaVirtual.desarrolladora.model.Desarrolladora;
import com.proyecto.tiendaVirtual.desarrolladora.service.DesarrolladoraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/desarrolladora")
public class DesarrolladoraController {
    @Autowired
    private DesarrolladoraService desarrolladoraService;

//    @GetMapping
//    public ResponseEntity<List<Desarrolladora>> getAllDesarrolladoras(){
//        List<Desarrolladora> desarrolladoras = desarrolladoraService.getAllDesarrolladoras();
//        return ResponseEntity.ok(desarrolladoras);
//    }
}
