package com.proyecto.tiendaVirtual.desarrolladora.controller;

import com.proyecto.tiendaVirtual.desarrolladora.model.Desarrolladora;
import com.proyecto.tiendaVirtual.desarrolladora.service.DesarrolladoraService;
import com.proyecto.tiendaVirtual.utils.ApiResponse;
import com.proyecto.tiendaVirtual.utils.ValueMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/desarrolladora")
public class DesarrolladoraController {
    @Autowired
    private DesarrolladoraService service;

    @GetMapping("/get")
    public ResponseEntity<ApiResponse> getAll() {
        List<Desarrolladora> desarrolladoras=service.getAll();
        if (!desarrolladoras.isEmpty()){
            ApiResponse<List<Desarrolladora>>desarrolladoraApiResponse=new ApiResponse<>(desarrolladoras);
            log.info("DesarrolladoraController::getAll", ValueMapper.jsonAsString(desarrolladoraApiResponse));
            return new ResponseEntity<>(desarrolladoraApiResponse,HttpStatus.CREATED);
        }
        log.info("DesarrolladoraController::getAll - No se encontraron juegos, returning No_CONTENT.");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
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
