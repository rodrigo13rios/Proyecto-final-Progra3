package com.proyecto.tiendaVirtual.MercadoPago.controller;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.resources.preference.Preference;
import com.proyecto.tiendaVirtual.billetera.dto.BilleteraDTO;
import com.proyecto.tiendaVirtual.billetera.service.BilleteraService;
import com.proyecto.tiendaVirtual.perfil.model.Perfil;
import com.proyecto.tiendaVirtual.utils.SecurityUtils;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {
    @Autowired
    SecurityUtils securityUtils;
    private final BilleteraService billeteraService;
    @Value("${mercadopago.access.token}")
    private String mptoken;
    private final String NGROK_URL = "https://taren-aureate-sparkle.ngrok-free.dev"; // URL de Ngrok

    @PostConstruct
    public void init(){
        MercadoPagoConfig.setAccessToken(mptoken);
    }

    @PostMapping("/wallet-preference")
    public ResponseEntity<?> createWalletPreference(@RequestBody BilleteraDTO dto)throws Exception{

        if (dto.getMonto() == null || dto.getMonto() <= 0) {
            return ResponseEntity.badRequest().body("Monto inválido");
        }

        Perfil perfil = securityUtils.getLoggedUser().getPerfil();

        PreferenceItemRequest item =
                PreferenceItemRequest.builder()
                        .title("Recarga billetera")
                        .quantity(1)
                        .currencyId("ARS")
                        .unitPrice(BigDecimal.valueOf(dto.getMonto()))
                        .build();


        PreferenceRequest prefReq =
                PreferenceRequest.builder()
                        .items(List.of(item))
                        .externalReference("wallet-" + perfil.getId() + "-" + dto.getMonto())
                        .notificationUrl(NGROK_URL+"/api/mp/webhook")
                        .backUrls(
                                PreferenceBackUrlsRequest.builder()
                                        .success(NGROK_URL+"/wallet-ok")
                                        .failure(NGROK_URL+"/wallet-error")
                                        .pending(NGROK_URL+"/wallet-pending")
                                        .build()
                        )
                        .autoReturn("approved")
                        .build();

        Preference pref = new PreferenceClient().create(prefReq);

        return ResponseEntity.ok(Map.of(
                "initPoint", pref.getInitPoint(),
                "sandboxInitPoint", pref.getSandboxInitPoint()
        ));
    }
    }

