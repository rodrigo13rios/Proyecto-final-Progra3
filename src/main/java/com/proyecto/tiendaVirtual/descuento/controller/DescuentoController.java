package com.proyecto.tiendaVirtual.descuento.controller;

import com.proyecto.tiendaVirtual.descuento.dto.CreateDescuentoDTO;
import com.proyecto.tiendaVirtual.descuento.dto.DescuentoResponseDTO;
import com.proyecto.tiendaVirtual.descuento.service.DescuentoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/descuentos")
@RequiredArgsConstructor
public class DescuentoController {
    private final DescuentoService descuentoService;

    @PostMapping
    public ResponseEntity<DescuentoResponseDTO> crear(@Valid @RequestBody CreateDescuentoDTO dto){
        return ResponseEntity.ok(descuentoService.crearDescuento(dto));
    }
}
