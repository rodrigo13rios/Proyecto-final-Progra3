package com.proyecto.tiendaVirtual.user.service;

import com.proyecto.tiendaVirtual.exceptions.ElementoYaExistenteException;
import com.proyecto.tiendaVirtual.exceptions.ElementoNoEncontradoException;
import com.proyecto.tiendaVirtual.user.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User create(User user) throws ElementoYaExistenteException;
    List<User> getAll();
    Optional<User> getById(Long id) throws ElementoNoEncontradoException;
    Optional<User> findByUsername(String username) ;

    Optional<User> getByEmail(String email) throws ElementoNoEncontradoException;

    void update(Long id, User userUpdated) throws ElementoNoEncontradoException;
    void delete(Long id) throws ElementoNoEncontradoException;

}
