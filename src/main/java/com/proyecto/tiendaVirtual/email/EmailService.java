package com.proyecto.tiendaVirtual.email;

import com.proyecto.tiendaVirtual.juego.model.Juego;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void enviarConfirmacionCompra(
            String to,
            String username,
            List<Juego> juegos
    ) {

        //Mensaje del Email
        String juegosTexto = juegos.stream()
                .map(juego ->
                        "Juego: " + juego.getNombre() + "\n" +
                        "Precio: $" + juego.getPrecio() + "\n"
                ).collect(Collectors.joining("\n"));
        String texto =
                "Hola " + username + ",\n\n" +
                "Gracias por tu compra.\n\n" +
                juegosTexto +
                "\n\n¡Que lo disfrutes!\n" +
                "Ryze Games";


        //Enviar Email
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("🎮 Compra confirmada || Ryze Games");
        message.setText(texto);

        try {
            mailSender.send(message);
        } catch (Exception e) {
            System.err.println("No se pudo enviar mail de compra: " + e);
        }
    }
}
