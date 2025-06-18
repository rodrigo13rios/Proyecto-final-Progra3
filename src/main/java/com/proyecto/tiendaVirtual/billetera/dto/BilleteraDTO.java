package com.proyecto.tiendaVirtual.billetera.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BilleteraDTO {
    @NotNull
    @Positive
    private Double monto;
}
