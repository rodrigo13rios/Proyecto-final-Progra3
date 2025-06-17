package com.proyecto.tiendaVirtual.compra.controller;

import com.proyecto.tiendaVirtual.compra.model.Compra;
import com.proyecto.tiendaVirtual.compra.service.compra.CompraService;
import com.proyecto.tiendaVirtual.utils.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/compra")
public class CompraController {
    @Autowired
    CompraService service;

    @GetMapping("/client")
    public ResponseEntity<List<Compra>> getByCliente(){
        UserDetails userDetails=(UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email= userDetails.getUsername();
        List<Compra>compras=service.getCompraByCliente(email);
        return new ResponseEntity<>(compras, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse>createCompra(){
        UserDetails userDetails=(UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email= userDetails.getUsername();
        ApiResponse<String>apiResponse=new ApiResponse<>(email);
        service.createCompra(email);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


}
