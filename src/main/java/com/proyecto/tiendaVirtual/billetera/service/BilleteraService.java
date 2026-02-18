package com.proyecto.tiendaVirtual.billetera.service;

import com.proyecto.tiendaVirtual.billetera.model.Billetera;
import org.springframework.stereotype.Service;

@Service
public interface BilleteraService {
    Double consultarSaldo();
    Double restarSaldo(Double pago);
    Double restarSaldo(Billetera billetera, Double pago);
    Double cargarSaldo(Double carga);
    Double cargarSaldo(Billetera billetera, Double carga);
}
