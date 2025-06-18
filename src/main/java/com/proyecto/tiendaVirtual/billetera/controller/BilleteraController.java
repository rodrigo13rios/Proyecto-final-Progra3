package com.proyecto.tiendaVirtual.billetera.controller;


import com.proyecto.tiendaVirtual.billetera.dto.BilleteraDTO;

import com.proyecto.tiendaVirtual.billetera.service.BilleteraService;
import com.proyecto.tiendaVirtual.perfil.service.PerfilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping ("api/billetera")
public class BilleteraController {
    @Autowired
    BilleteraService service;
    @Autowired
    PerfilService perfilService;

    /// No POST: Billetera se crea desde Perfil
    /// No DELETE: Billetera se borra junto a Perfil

    @GetMapping
    public ResponseEntity<Double> consultar() {
        return ResponseEntity.ok(service.consultarSaldo());
    }

    @PostMapping("/cargar")

    public ResponseEntity<Double> cargar(@RequestBody BilleteraDTO dto) {//Se recive desde Postman {"monto":1234}
        Double nuevoSaldo = service.cargarSaldo(dto.getMonto());

        return ResponseEntity.ok(nuevoSaldo);
    }


    //Con fines de testeo. En la práctica solo se descontaría saldo al comprar un juego
    @PostMapping("/restar")

    public ResponseEntity<Double> restar(@RequestBody BilleteraDTO dto) {
        Double nuevoSaldo = service.restarSaldo(dto.getMonto());

        return ResponseEntity.ok(nuevoSaldo);
    }

}
