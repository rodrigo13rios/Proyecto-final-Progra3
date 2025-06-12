package com.proyecto.tiendaVirtual.billetera.controller;

import com.proyecto.tiendaVirtual.billetera.service.BilleteraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping ("api/billetera")
public class BilleteraController {
    @Autowired
    BilleteraService service;

    @GetMapping("/saldo")
    public ResponseEntity<Map> consultarSaldo (@PathVariable Long perfilId){
        double saldo = service.consultarSaldo(perfilId);
        return ResponseEntity.ok(Map.of("Saldo",saldo));
    }

    @PutMapping("/recargar")
    public ResponseEntity<String> recargarSaldo(@PathVariable Long perfilId, @RequestBody double carga){
        service.cargarSaldo(carga,perfilId);
        return ResponseEntity.ok("Recarga Exitosa");
    }
}
