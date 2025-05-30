package com.proyecto.tiendaVirtual.user.service;

import com.proyecto.tiendaVirtual.exceptions.ElementoExistenteException;
import com.proyecto.tiendaVirtual.exceptions.ElementoNoEncontradoException;
import com.proyecto.tiendaVirtual.exceptions.ListaVaciaException;
import com.proyecto.tiendaVirtual.user.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User createUser(User user) throws ElementoExistenteException;
    List<User> getAllUser() throws ListaVaciaException;
    Optional<User> getUserById(Long id) throws ElementoNoEncontradoException;
    Optional<User> findByUsername(String username) ;

    Optional<User> getUserByEmail(String email) throws ElementoNoEncontradoException;

    void updateUser(Long id, User userUpdated) throws ElementoNoEncontradoException;
    void deleteUser(Long id) throws ElementoNoEncontradoException;

}
