package com.proyecto.tiendaVirtual.utils;

import com.proyecto.tiendaVirtual.exceptions.ElementoNoEncontradoException;
import com.proyecto.tiendaVirtual.user.model.User;
import com.proyecto.tiendaVirtual.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtils {

    private final UserRepository userRepo;

    @Autowired
    public SecurityUtils(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public User getLoggedUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepo.findByEmail(email)
                .orElseThrow(() -> new ElementoNoEncontradoException("No se ha podido obtener el usuario logeado"));
    }
}
