package com.proyecto.tiendaVirtual.billetera.service;

import com.proyecto.tiendaVirtual.billetera.model.Billetera;
import com.proyecto.tiendaVirtual.billetera.repository.BilleteraRepository;
import com.proyecto.tiendaVirtual.exceptions.ElementoNoEncontradoException;
import com.proyecto.tiendaVirtual.exceptions.ElementoYaExistenteException;
import com.proyecto.tiendaVirtual.exceptions.NumeroInvalidoException;
import com.proyecto.tiendaVirtual.perfil.model.Perfil;
import com.proyecto.tiendaVirtual.perfil.service.PerfilService;
import com.proyecto.tiendaVirtual.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class BilleteraServiceImpl implements BilleteraService{
    @Autowired
    private BilleteraRepository repo;
    @Autowired
    private SecurityUtils securityUtils;

    @Override
    public Double consultarSaldo() {
        //Obtengo la Billetera
        Billetera billetera = securityUtils.getLoggedUser().getPerfil().getBilletera();
        if (billetera == null) {
            throw new ElementoNoEncontradoException("No se ha podido obtener la Billetera del Perfil logeado");
        }

        return billetera.getSaldo();
    }

    @Override
    @Transactional
    public Double restarSaldo(Double monto) {
        //Valido el monto
        if (monto == null || monto <= 0) {
            throw new NumeroInvalidoException("El monto debe ser mayor a cero");
        }

        //Obtengo la Billetera
        Billetera billetera = securityUtils.getLoggedUser().getPerfil().getBilletera();
        if (billetera == null) {
            throw new ElementoNoEncontradoException("No se ha podido obtener la Billetera del Perfil logeado");
        }

        //Si el saldo es menor al gasto, significa que no hay suficiente dinero para realizar la operación.
        if (billetera.getSaldo() < monto) {
            throw new NumeroInvalidoException("El Saldo de la billetera es insuficiente para realizar esta operación");
        }

        //Realizo la resta y guardo
        billetera.setSaldo(billetera.getSaldo() - monto);
        repo.save(billetera);

        return billetera.getSaldo();
    }


    @Override
    @Transactional
    public Double cargarSaldo(Double monto) {
        //Valido el monto
        if (monto == null || monto <= 0) {
            throw new NumeroInvalidoException("El monto debe ser mayor a cero");
        }

        //Obtengo la Billetera
        Billetera billetera = securityUtils.getLoggedUser().getPerfil().getBilletera();
        if (billetera == null) {
            throw new ElementoNoEncontradoException("No se ha podido obtener la Billetera del Perfil logeado");
        }

        //Realizo la suma y guardo
        billetera.setSaldo(billetera.getSaldo() + monto);
        repo.save(billetera);

        return billetera.getSaldo();
    }

}
