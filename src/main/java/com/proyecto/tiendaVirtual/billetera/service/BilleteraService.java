package com.proyecto.tiendaVirtual.billetera.service;

import org.springframework.stereotype.Service;

@Service
public interface BilleteraService {
    double consultarSaldo(Long perfilId);
    void descontarSaldo(double pago, Long perfilId);
    void cargarSaldo(double carga, Long perfilId);
}
