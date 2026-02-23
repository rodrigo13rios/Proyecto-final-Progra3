package com.proyecto.tiendaVirtual.MercadoPago.controller;

import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.resources.payment.Payment;
import com.proyecto.tiendaVirtual.billetera.model.Billetera;
import com.proyecto.tiendaVirtual.billetera.repository.BilleteraRepository;
import com.proyecto.tiendaVirtual.billetera.service.BilleteraService;

import com.proyecto.tiendaVirtual.perfil.service.PerfilService;
import com.proyecto.tiendaVirtual.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final BilleteraRepository billeteraRepo;
    private final PerfilService perfilService;

    @PostMapping("/webhook")
    public ResponseEntity<?> mpWebhook(@RequestBody Map<String, Object> body) throws  Exception{

        if (!"payment".equals(body.get("type"))) {
            return ResponseEntity.ok().build();
        }

        // 2️⃣ obtener id correctamente
        Map<String, Object> data = (Map<String, Object>) body.get("data");
        String paymentId = data.get("id").toString();

        // 3️⃣ consultar pago en MP
        Payment payment = new PaymentClient().get(Long.parseLong(paymentId));

        if (!"approved".equals(payment.getStatus())) {
            return ResponseEntity.ok().build();
        }

        // 4️⃣ leer external reference
        String ref = payment.getExternalReference();
        // formato: wallet-USERID-MONTO

        String[] parts = ref.split("-");
        Long userId = Long.parseLong(parts[1]);
        Double monto = Double.parseDouble(parts[2]);

        // 5️⃣ acreditar saldo
        Billetera billetera = perfilService
                .getById(userId)
                .orElseThrow()
                .getBilletera();

        billetera.setSaldo(billetera.getSaldo() + monto);
        billeteraRepo.save(billetera);

        return ResponseEntity.ok().build();
    }

}
