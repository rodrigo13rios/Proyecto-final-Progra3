package com.proyecto.tiendaVirtual.desarrolladora.service;

import com.proyecto.tiendaVirtual.desarrolladora.model.Desarrolladora;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface DesarrolladoraService {
    void createDesarrolladora(Desarrolladora desarrolladora);
    Optional<Desarrolladora> getDesarrolladoraById(Long id);
    Optional<Desarrolladora> getDesarrolladoraByName(String nombre);
    List<Desarrolladora> getAllDesarrolladoras();
    void updateDesarrolladora(Long id, Desarrolladora updatedDesarrolladora);
    void deleteDesarrolladora(Long id);
}
