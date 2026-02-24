package com.proyecto.tiendaVirtual.compra.event;

import com.proyecto.tiendaVirtual.compra.model.Compra;
import com.proyecto.tiendaVirtual.email.CompraEmailData;
import com.proyecto.tiendaVirtual.email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class CompraEmailListener {
    @Autowired
    EmailService emailService;

    @Async
    @TransactionalEventListener(
            phase = TransactionPhase.AFTER_COMMIT
    )
    public void onCompraRealizada(CompraRealizadaEvent event) {
        Compra compra = event.getCompra();
        CompraEmailData emailData = new CompraEmailData(
                compra.getUser().getEmail(),
                compra.getUser().getPerfil().getNickName(),
                compra.getTotal(),
                compra.getItems()
        );

        emailService.enviarConfirmacionCompra(emailData);
    }
}
