package com.proyecto.tiendaVirtual.billetera.service;

import org.springframework.stereotype.Service;

@Service
public interface BilleteraService {
    Double consultarSaldo();
    Double restarSaldo(Double pago);
    Double cargarSaldo(Double carga);
}
