package com.proyecto.tiendaVirtual.user.service;

import com.proyecto.tiendaVirtual.user.dto.UserDTO;
import com.proyecto.tiendaVirtual.user.dto.UserUpdateDTO;
import com.proyecto.tiendaVirtual.user.dto.UserVerDTO;
import com.proyecto.tiendaVirtual.user.model.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService  {
    User createUser(UserDTO dto);
    List<UserVerDTO> getAll();
    Optional<User> getById(Long id);
    User update(Long id, UserUpdateDTO nuevo);
    void delete(Long id);
    Optional<User> getByEmail(String email);
    UserVerDTO convertirAVerDTO(User user);
}
