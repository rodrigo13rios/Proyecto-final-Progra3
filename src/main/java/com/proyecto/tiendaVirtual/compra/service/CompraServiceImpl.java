package com.proyecto.tiendaVirtual.compra.service;

import com.proyecto.tiendaVirtual.billetera.service.BilleteraService;
import com.proyecto.tiendaVirtual.compra.dto.PedidoCompra;
import com.proyecto.tiendaVirtual.compra.event.CompraRealizadaEvent;
import com.proyecto.tiendaVirtual.compra.model.Compra;
import com.proyecto.tiendaVirtual.compra.model.CompraItem;
import com.proyecto.tiendaVirtual.compra.repository.CompraRepository;
import com.proyecto.tiendaVirtual.email.CompraEmailData;
import com.proyecto.tiendaVirtual.exceptions.ElementoNoEncontradoException;
import com.proyecto.tiendaVirtual.exceptions.ElementoYaExistenteException;
import com.proyecto.tiendaVirtual.juego.dto.JuegoVerDTO;
import com.proyecto.tiendaVirtual.juego.model.Juego;
import com.proyecto.tiendaVirtual.juego.repository.JuegoRepository;
import com.proyecto.tiendaVirtual.juego.service.JuegoService;
import com.proyecto.tiendaVirtual.perfil.model.Perfil;
import com.proyecto.tiendaVirtual.perfil.repository.PerfilRepository;
import com.proyecto.tiendaVirtual.user.model.User;
import com.proyecto.tiendaVirtual.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CompraServiceImpl implements CompraService {
    @Autowired
    SecurityUtils securityUtils;
    @Autowired
    CompraRepository compraRepo;
    @Autowired
    JuegoRepository juegoRepo;
    @Autowired
    PerfilRepository perfilRepo;
    @Autowired
    BilleteraService billeteraService;
    @Autowired
    ApplicationEventPublisher eventPublisher;
    @Autowired
    JuegoService juegoService;


    @Override
    @Transactional
    public void comprar(List<Long> juegosIds) {
        if (juegosIds == null || juegosIds.isEmpty()) {
            throw new IllegalArgumentException("La compra no puede estar vacía");
        }

        User user = securityUtils.getLoggedUser();
        Perfil perfil = user.getPerfil();

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

        // 3. Crear la compra + items y calcular total con precioPagado (precio final en ese momento)
        Compra compra = new Compra();
        compra.setUser(user);

        double total = 0.0;

        for (Juego juego : juegos) {
            double precioPagado = juegoService.obtenerPrecioFinal(juego);

            CompraItem item = CompraItem.builder()
                    .compra(compra)
                    .juego(juego)
                    .precioPagado(precioPagado)
                    .build();

            compra.getItems().add(item);
            total += precioPagado;
        }

        // Redondeo monetario simple (Double)
        total = Math.round(total * 100.0) / 100.0;

        compra.setTotal(total);

        // 4. Cobrar UNA SOLA VEZ
        billeteraService.restarSaldo(perfil.getBilletera(), total);

        // 5. Agregar todos los juegos
        perfil.getJuegos().addAll(juegos);

        // 6. Guardar
        perfilRepo.save(perfil);
        compraRepo.save(compra);

        // 🔔 Publicar evento
        eventPublisher.publishEvent(new CompraRealizadaEvent(compra));
    }

    public List<JuegoVerDTO> preview(List<Long> gamesIds){
        List<Juego> juegos = juegoRepo.findAllById(gamesIds);

        return juegos.stream()
                .map(juegoService::convertirAVerDTO)
                .toList();
    }
}
