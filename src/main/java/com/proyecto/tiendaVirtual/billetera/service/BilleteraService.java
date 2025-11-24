package com.proyecto.tiendaVirtual.billetera.service;

import com.proyecto.tiendaVirtual.billetera.dto.SaldoDTO;
import org.springframework.stereotype.Service;

@Service
public interface BilleteraService {
    Double consultarSaldo();
    SaldoDTO obtenerSaldo();
    Double restarSaldo(Double pago);
    Double cargarSaldo(Double carga);

}
