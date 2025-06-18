package com.proyecto.tiendaVirtual.billetera.controller;

import com.proyecto.tiendaVirtual.billetera.dto.BilleteraDTO;
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

@RestController
@RequestMapping ("api/billetera")
@PreAuthorize("hasRole('PERFIL')")
public class BilleteraController {
    @Autowired
    BilleteraService service;
    @Autowired
    PerfilService perfilService;

    /// No POST: Billetera se crea desde Perfil
    /// No DELETE: Billetera se borra junto a Perfil

    @GetMapping("/controlarSaldo/{perfilId}")
    public ResponseEntity<Double> consultarSaldo (@PathVariable Long perfilId){
        validarPropietario(perfilId);
        Double saldo = service.consultarSaldo(perfilId);
        return ResponseEntity.ok(saldo);
    }

    @PutMapping("/recargar/{perfilId}")
    public ResponseEntity<Double> recargarSaldo(@PathVariable Long perfilId, @RequestBody BilleteraDTO billeteraDTO){
        validarPropietario(perfilId);
        Double saldoActualizado = service.cargarSaldo(billeteraDTO.getMonto(), perfilId);
        return ResponseEntity.ok(saldoActualizado);
    }

    @PutMapping("/descontar/{perfilId}")
    public ResponseEntity<String> descontarSaldo(@PathVariable Long perfilId, @RequestBody BilleteraDTO billeteraDTO){
        service.descontarSaldo(billeteraDTO.getMonto(), perfilId);
        return ResponseEntity.ok("Pago realizado");
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
