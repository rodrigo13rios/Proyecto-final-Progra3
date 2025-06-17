package com.proyecto.tiendaVirtual.billetera.service;

import com.proyecto.tiendaVirtual.billetera.model.Billetera;
import org.springframework.stereotype.Service;

@Service
public interface BilleteraService {
    Billetera create(Billetera billetera);
    void actualizar(Long id, Billetera nueva);
    void delete(Long id);
    double consultarSaldo(Long perfilId);
    void descontarSaldo(double pago, Long perfilId);
    void cargarSaldo(double carga, Long perfilId);
}
