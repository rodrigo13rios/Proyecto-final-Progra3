package com.proyecto.tiendaVirtual.compra.service;

import com.proyecto.tiendaVirtual.billetera.service.BilleteraService;
import com.proyecto.tiendaVirtual.exceptions.ElementoNoEncontradoException;
import com.proyecto.tiendaVirtual.exceptions.ElementoYaExistenteException;
import com.proyecto.tiendaVirtual.juego.model.Juego;
import com.proyecto.tiendaVirtual.juego.repository.JuegoRepository;
import com.proyecto.tiendaVirtual.perfil.model.Perfil;
import com.proyecto.tiendaVirtual.perfil.repository.PerfilRepository;
import com.proyecto.tiendaVirtual.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CompraServiceImpl implements CompraService {
    @Autowired
    SecurityUtils securityUtils;
    @Autowired
    JuegoRepository juegoRepo;
    @Autowired
    PerfilRepository perfilRepo;
    @Autowired
    BilleteraService billeteraService;


    @Override
    @Transactional
    public void comprar(List<Long> juegosIds) {

        Perfil perfil = securityUtils.getLoggedUser().getPerfil();

        // 1. Traer todos los juegos
        List<Juego> juegos = juegoRepo.findAllById(juegosIds);

        if (juegos.size() != juegosIds.size()) {
            throw new ElementoNoEncontradoException("Uno o más juegos no existen");
        }

        // 2. Validar que no los tenga ya
        for (Juego juego : juegos) {
            if (perfil.getJuegos().contains(juego)) {
                throw new ElementoYaExistenteException(
                        "Ya tienes el juego: " + juego.getNombre()
                );
            }
        }

        // 3. Calcular total (Double)
        double total = juegos.stream()
                .mapToDouble(Juego::getPrecio)
                .sum();

        // 4. Cobrar UNA SOLA VEZ
        billeteraService.restarSaldo(total);

        // 5. Agregar todos los juegos
        perfil.getJuegos().addAll(juegos);

        // 6. Guardar
        perfilRepo.save(perfil);
    }
}
