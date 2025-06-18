package com.proyecto.tiendaVirtual.user.dto;

import com.proyecto.tiendaVirtual.user.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVerDTO {
    private Long id;
    private String email;
    private String nombre;
    private String apellido;
    private Role role;
    private String nickName;
    private String nombreDesarrolladora;
}
