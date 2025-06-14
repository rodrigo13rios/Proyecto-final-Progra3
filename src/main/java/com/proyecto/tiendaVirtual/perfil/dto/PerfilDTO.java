package com.proyecto.tiendaVirtual.perfil.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PerfilDTO {
    @NotBlank
    @Size(min = 2, max = 50)
    private String nickName;
}
