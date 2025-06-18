package com.proyecto.tiendaVirtual.billetera.service;

import com.proyecto.tiendaVirtual.billetera.model.Billetera;
import com.proyecto.tiendaVirtual.billetera.repository.BilleteraRepository;
import com.proyecto.tiendaVirtual.exceptions.ElementoNoEncontradoException;
import com.proyecto.tiendaVirtual.exceptions.ElementoYaExistenteException;
import com.proyecto.tiendaVirtual.exceptions.NumeroInvalidoException;
import com.proyecto.tiendaVirtual.perfil.model.Perfil;
import com.proyecto.tiendaVirtual.perfil.service.PerfilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BilleteraServiceImpl implements BilleteraService{

    @Autowired
    private BilleteraRepository repo;

    @Autowired
    private PerfilService perfilService;


    @Override
    public double consultarSaldo(Long perfilId) {
        Optional<Perfil> perfil = perfilService.getById(perfilId);

        Optional<Billetera> billetera = repo.findById(perfil.get().getBilletera().getId());

        if (billetera.isEmpty()) throw new ElementoNoEncontradoException("No se pudo asociar ninguna billetera");

        return billetera.get().getSaldo();
    }

    @Override
    public void descontarSaldo(double pago, Long perfilId) {

        if (pago <0) throw new NumeroInvalidoException("El monto del pago debe ser mayor o igual a cero");

        Optional<Perfil> perfil = perfilService.getById(perfilId);

        Billetera billetera = perfil.get().getBilletera();

        if (billetera.getSaldo() < pago) throw new NumeroInvalidoException("El saldo es insuficiente");

        billetera.setSaldo(billetera.getSaldo() - pago);

        actualizar(perfil.get().getBilletera().getId(), billetera);

    }


    public void actualizar(Long id, Billetera nueva){
        Billetera existente = repo.findById(id)
                            .orElseThrow(()-> new ElementoNoEncontradoException("Error al cargar la billetera"));
        if (nueva.getSaldo()!=null){
            existente.setSaldo(nueva.getSaldo());
        }

        repo.save(existente);
    }


    @Override
    public Double cargarSaldo(double carga, Long perfilId) {

        if (carga < 0) throw new NumeroInvalidoException("El monto de la carga debe ser mayor a cero");
        Optional<Perfil> perfil = perfilService.getById(perfilId);

        Billetera billetera = perfil.get().getBilletera();
        billetera.setSaldo(billetera.getSaldo() + carga);

        actualizar(perfil.get().getBilletera().getId(), billetera);
        return billetera.getSaldo();
    }

}
