package com.proyecto.tiendaVirtual.MercadoPago.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class PaymentRedirectController {

    @GetMapping("/wallet-ok")
    public void walletOk(HttpServletResponse response) throws IOException{
        response.sendRedirect("https://localhost:4200/wallet-ok");
    }
    @GetMapping("/wallet-error")
    public void walletError(HttpServletResponse response) throws IOException {
        response.sendRedirect("https://localhost:4200/wallet-error");
    }

    @GetMapping("/wallet-pending")
    public void walletPending(HttpServletResponse response) throws IOException {
        response.sendRedirect("https://localhost:4200/wallet-pending");
    }
}
