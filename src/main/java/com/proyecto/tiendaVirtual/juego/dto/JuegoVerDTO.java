package com.proyecto.tiendaVirtual.juego.dto;


import com.proyecto.tiendaVirtual.desarrolladora.dto.DesarrolladoraDTO;
import com.proyecto.tiendaVirtual.juego.model.Categoria;
import com.proyecto.tiendaVirtual.juego.model.Juego;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JuegoVerDTO {
    private Long id;
    private String nombre;
    private LocalDate fechaLanzamiento;
    private Double precio;
    private Categoria categoria;
    private String foto;
    private DesarrolladoraDTO desarrolladora;

    static public JuegoVerDTO convertirAVerDTO(Juego juego){
        JuegoVerDTO dto = new JuegoVerDTO();

        dto.setId(juego.getId());
        dto.setNombre(juego.getNombre());
        dto.setFechaLanzamiento(juego.getFechaLanzamiento());
        dto.setPrecio(juego.getPrecio());
        dto.setCategoria(juego.getCategoria());
        dto.setFoto(juego.getFoto());
        dto.setDesarrolladora(new DesarrolladoraDTO( //Convierto a DesarrolladoraDTO
                juego.getDesarrolladora().getId(),
                juego.getDesarrolladora().getNombre(),
                juego.getDesarrolladora().getPaisOrigen()
        ));

        return dto;
    }
}

