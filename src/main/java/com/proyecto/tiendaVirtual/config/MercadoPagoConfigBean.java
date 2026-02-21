package com.proyecto.tiendaVirtual.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MercadoPagoConfigBean {
    @Value("${mercadopago.access.token}")
    private String accessToken;


    @PostConstruct
    public void init(){
        com.mercadopago.MercadoPagoConfig.setAccessToken(accessToken);
    }
}
