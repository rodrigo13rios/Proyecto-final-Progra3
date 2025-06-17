package com.proyecto.tiendaVirtual.juego.controller;


import com.proyecto.tiendaVirtual.juego.model.Juego;
import com.proyecto.tiendaVirtual.juego.service.JuegoService;
import com.proyecto.tiendaVirtual.utils.ApiResponse;
import com.proyecto.tiendaVirtual.utils.ValueMapper;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Slf4j
@Controller
@RequestMapping("api/juego")
public class JuegoController {

    private final JuegoService juegoService;

    public JuegoController(JuegoService juegoService) {
        this.juegoService = juegoService;
    }

    @GetMapping("/get")
    public ResponseEntity<ApiResponse>getJuegos(){
        List<Juego> juegos=juegoService.getAll();
        if(!juegos.isEmpty()){
            ApiResponse<List<Juego>>juegoApiResponse=new ApiResponse<>(juegos);
            log.info("JuegoController::getJuegos response{}", ValueMapper.jsonAsString(juegoApiResponse));
            return new ResponseEntity<>(juegoApiResponse, HttpStatus.OK);
        }
        log.info("ProductController::getJuegos - No se encontraron juegos, returning NO_CONTENT.");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // toDO: Fecha de lanzamiento ingresa la fecha en la cual se crea. Se deberia poder ingresar la fecha que el usuario quiera y validarlo.
    @PostMapping("/create")
    @PreAuthorize("hasRole('DESARROLLADORA')")
    public ResponseEntity<ApiResponse> createNewJuego(@RequestBody @Valid Juego juegoNuevo){
        log.info("JuegoController::createNewJuego peticion{}",ValueMapper.jsonAsString(juegoNuevo));
        juegoNuevo.setFechaLanzamiento(LocalDate.now());
        juegoService.create(juegoNuevo);
        ApiResponse<Juego>JuegoApiResponse=new ApiResponse<>(juegoNuevo);
        log.info("juegoController::createNewJuego response{}", ValueMapper.jsonAsString(JuegoApiResponse));
        return new ResponseEntity<>(JuegoApiResponse,HttpStatus.CREATED);

    }

    @PutMapping("/update/{id_juego}")
    @PreAuthorize("hasRole('DESARROLLADORA')")
    public ResponseEntity<ApiResponse>updateJuego(@Valid @PathVariable Long id_juego,@RequestBody Juego juegoUpdate){
        log.info("JuegoController::updateJuego response {}",ValueMapper.jsonAsString(id_juego));
        Juego juegoNuevo=juegoService.update(id_juego,juegoUpdate);

        ApiResponse<Juego> juegoApiResponse=new ApiResponse<>(juegoNuevo);
        log.info("JuegoController::updateJuego response {}",ValueMapper.jsonAsString(juegoApiResponse));
        return new ResponseEntity<>(juegoApiResponse,HttpStatus.OK);

    }

    @DeleteMapping("/delete/{id_juego}")
    @PreAuthorize("hasRole('DESARROLLADORA')")
    public ResponseEntity<ApiResponse>deleteJuego(@PathVariable Long id_juego){
        log.info("JuegoController::deleteJuego peticion iniciada con el id:{}", ValueMapper.jsonAsString(id_juego));
        Optional<Juego> juego=juegoService.findById(id_juego);
        if (juego.isPresent()){
            juegoService.delete(id_juego);
            ApiResponse<String> juegoResponse= new ApiResponse<>("Juego eliminado");
            log.info("JuegoController::deleteJuego respuesta{}",ValueMapper.jsonAsString(juegoResponse));
            return  new ResponseEntity<>(juegoResponse,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @GetMapping("/buscarPorId/{id_juego}")
    public ResponseEntity<ApiResponse>findById(@PathVariable Long id_juego){
        log.info("JuegoController::findById peticion iniciada con id: {}",ValueMapper.jsonAsString(id_juego));
        Optional<Juego> juego=juegoService.findById(id_juego);

        if (juego.isPresent()){
            ApiResponse<Juego>juegoApiResponse=new ApiResponse<>(juego.get());
            log.info("JuegoController::findById response{}",ValueMapper.jsonAsString(juegoApiResponse));
            return new ResponseEntity(juegoApiResponse,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @GetMapping("/buscarPorNombre/{nombre}")
    public ResponseEntity<ApiResponse>findByNombre(@PathVariable String nombre_juego){
        log.info("JuegoController::findByNombre peticion iniciada con nombre: {}",ValueMapper.jsonAsString(nombre_juego));
        Optional<Juego>juego=juegoService.findByName(nombre_juego);

        if (juego.isPresent()){
            ApiResponse<Juego> juegoApiResponse=new ApiResponse<>(juego.get());
            log.info("JuegoController::findByNombre response{}",ValueMapper.jsonAsString(juegoApiResponse));
            return new ResponseEntity<>(juegoApiResponse,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }



}
