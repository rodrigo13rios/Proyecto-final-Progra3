package com.proyecto.tiendaVirtual.user.dto;

import com.proyecto.tiendaVirtual.user.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    @NotBlank
    @Size (min = 2,max = 50)
    private String nombre;
    @NotBlank
    @Size(min = 2,max = 50)
    private String apellido;
    @NotBlank
    @Email(message = "Debe ingresar un formato valido")
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    private Role role;
    private String nickname;
    private String nombreDesarrolladora;
    private String paisOrigen;
}
