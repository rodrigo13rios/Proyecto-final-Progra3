package com.proyecto.tiendaVirtual.user.dto;

import com.proyecto.tiendaVirtual.user.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateDTO {
    @Size(min = 2,max = 50)
    private String nombre;

    @Size(min = 2,max = 50)
    private String apellido;

    private String password;
}
