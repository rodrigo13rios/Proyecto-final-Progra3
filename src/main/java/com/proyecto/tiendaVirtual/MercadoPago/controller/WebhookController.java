package com.proyecto.tiendaVirtual.MercadoPago.controller;

import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.resources.payment.Payment;
import com.proyecto.tiendaVirtual.billetera.model.Billetera;
import com.proyecto.tiendaVirtual.billetera.repository.BilleteraRepository;
import com.proyecto.tiendaVirtual.billetera.service.BilleteraService;
import com.proyecto.tiendaVirtual.carrito.service.CarritoService;
import com.proyecto.tiendaVirtual.perfil.service.PerfilService;
import com.proyecto.tiendaVirtual.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/mp")
@RequiredArgsConstructor
public class WebhookController {

    private BilleteraRepository billeteraRepo;
    private PerfilService perfilService;

    @PostMapping("/webhook")
    public ResponseEntity<?> mpWebhook(@RequestBody Map<String, String> params) throws  Exception{

        if (!"payment".equals(params.get("type"))){
           return ResponseEntity.ok().build();
       }

       String paymentId = params.get("data.id");

        Payment payment = new PaymentClient().get(Long.parseLong(paymentId));

        if (!"approved".equals(payment.getStatus())){
            return ResponseEntity.ok().build();
        }

        String ref = payment.getExternalReference();
        // wallet-USERID-MONTO

        String[] parts = ref.split("-");
        Long userId = Long.parseLong(parts[1]);
        Double monto = Double.parseDouble(parts[2]);

        Billetera billetera = perfilService.getById(userId).get().getBilletera();

        billetera.setSaldo(billetera.getSaldo() + monto);
        billeteraRepo.save(billetera);

        return ResponseEntity.ok().build();
    }
}
