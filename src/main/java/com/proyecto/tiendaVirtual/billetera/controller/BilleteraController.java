package com.proyecto.tiendaVirtual.billetera.controller;

import com.proyecto.tiendaVirtual.billetera.model.Billetera;
import com.proyecto.tiendaVirtual.billetera.service.BilleteraService;
import com.proyecto.tiendaVirtual.exceptions.AccesoNegadoException;
import com.proyecto.tiendaVirtual.perfil.model.Perfil;
import com.proyecto.tiendaVirtual.perfil.service.PerfilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping ("api/billetera")
@PreAuthorize("hasRole('PERFIL')")
public class BilleteraController {
    @Autowired
    BilleteraService service;
    @Autowired
    PerfilService perfilService;

    @PostMapping
    public ResponseEntity<Billetera> create(@RequestBody Billetera billetera){
        Billetera result = service.create(billetera);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping("/controlarSaldo/{perfilId}")
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

    @DeleteMapping(("/delete/{id}"))
    public ResponseEntity<Void> delete (@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    private void validarPropietario(Long id){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        Optional<Perfil> perfil = perfilService.getById(id);

        if (!perfil.get().getUser().getEmail().equals(email)){
            throw new AccesoNegadoException("No tenes el permiso para acceder a esta billetera");
        }
    }
}
