package com.proyecto.tiendaVirtual.billetera.controller;

import com.proyecto.tiendaVirtual.billetera.model.Billetera;
import com.proyecto.tiendaVirtual.billetera.service.BilleteraService;
import com.proyecto.tiendaVirtual.billetera.service.BilleteraServiceImpl;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping ("api/billetera")
public class BilleteraController {
    @Autowired
    BilleteraService service;

    @PostMapping
    public ResponseEntity<Billetera> create(@RequestBody Billetera billetera){
        Billetera result = service.create(billetera);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping("/{perfilId}")
    public ResponseEntity<Map> consultarSaldo (@PathVariable Long perfilId){
        double saldo = service.consultarSaldo(perfilId);
        return ResponseEntity.ok(Map.of("Saldo",saldo));
    }

    @PutMapping("/recargar/{perfilId}")
    public ResponseEntity<String> recargarSaldo(@PathVariable Long perfilId, @RequestBody double carga){
        service.cargarSaldo(carga,perfilId);
        return ResponseEntity.ok("Recarga Exitosa");
    }

    @PutMapping("/descontar/{perfilId}")
    public ResponseEntity<String> descontarSaldo(@PathVariable Long perfilId, @RequestBody double pago){
        service.descontarSaldo(pago, perfilId);
        return ResponseEntity.ok("Pago realizado");
    }

    @DeleteMapping(("/{id}"))
    public ResponseEntity<Void> delete (@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
