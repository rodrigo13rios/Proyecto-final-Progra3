package com.proyecto.tiendaVirtual.billetera.service;

import com.proyecto.tiendaVirtual.billetera.model.Billetera;
import com.proyecto.tiendaVirtual.billetera.repository.BilleteraRepository;
import com.proyecto.tiendaVirtual.exceptions.ElementoNoEncontradoException;
import com.proyecto.tiendaVirtual.exceptions.NumeroInvalidoException;
import com.proyecto.tiendaVirtual.perfil.model.Perfil;
import com.proyecto.tiendaVirtual.perfil.repository.PerfilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BilleteraServiceImpl implements BilleteraService{
    //hace falta repo? :/
    @Autowired
    private BilleteraRepository repo;
    @Autowired
    private PerfilRepository perfilRepository;

    @Override
    public double consultarSaldo(Long perfilId) {
        Perfil perfil = perfilRepository.findById(perfilId)
                .orElseThrow(()->new ElementoNoEncontradoException("No se encontro un perfil asociado"));
        return perfil.getBilletera().getSaldo();
    }

    @Override
    public void descontarSaldo(double pago, Long perfilId) {

        if (pago <0) throw new NumeroInvalidoException("El mondo del pago debe ser mayor o igual a cero");
        Perfil perfil = perfilRepository.findById(perfilId).orElseThrow(()->new ElementoNoEncontradoException("Perfil no encontrado"));
        Billetera billetera = perfil.getBilletera();

        if (billetera.getSaldo() < pago) throw new NumeroInvalidoException("El saldo es insuficiente");

        billetera.setSaldo(billetera.getSaldo() - pago);
        //actualizar
    }

    @Override
    public void cargarSaldo(double carga, Long perfilId) {
        if (carga <= 0) throw new NumeroInvalidoException("El monto de la carga debe ser mayor a cero");
        Perfil perfil = perfilRepository.findById(perfilId)
                .orElseThrow(()->new ElementoNoEncontradoException("Perfil no encontrado"));
        Billetera billetera = perfil.getBilletera();
        billetera.setSaldo(billetera.getSaldo() + carga);

        //actualizar
    }
}
