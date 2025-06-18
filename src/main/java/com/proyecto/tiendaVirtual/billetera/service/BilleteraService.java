package com.proyecto.tiendaVirtual.billetera.service;

import com.proyecto.tiendaVirtual.billetera.model.Billetera;
import org.springframework.stereotype.Service;

@Service
public interface BilleteraService {

    double consultarSaldo(Long perfilId);
    void descontarSaldo(double pago, Long perfilId);
    Double cargarSaldo(double carga, Long perfilId);
}
