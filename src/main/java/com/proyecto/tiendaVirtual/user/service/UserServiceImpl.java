package com.proyecto.tiendaVirtual.user.service;

import com.proyecto.tiendaVirtual.desarrolladora.model.Desarrolladora;
import com.proyecto.tiendaVirtual.desarrolladora.service.DesarrolladoraService;
import com.proyecto.tiendaVirtual.exceptions.ElementoNoEncontradoException;
import com.proyecto.tiendaVirtual.exceptions.ElementoYaExistenteException;
import com.proyecto.tiendaVirtual.perfil.model.Perfil;
import com.proyecto.tiendaVirtual.perfil.repository.PerfilRepository;
import com.proyecto.tiendaVirtual.perfil.service.PerfilService;
import com.proyecto.tiendaVirtual.user.dto.UserDTO;
import com.proyecto.tiendaVirtual.user.dto.UserUpdateDTO;
import com.proyecto.tiendaVirtual.user.dto.UserVerDTO;
import com.proyecto.tiendaVirtual.user.model.Role;
import com.proyecto.tiendaVirtual.user.model.User;
import com.proyecto.tiendaVirtual.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository repo;
    @Autowired
    PerfilService perfilService;
    @Autowired
    DesarrolladoraService desarrolladoraService;


    @Override
    @Transactional
    public User createUser(UserDTO userDTO) {
        if (getByEmail(userDTO.getEmail()).isPresent()) {
            throw new ElementoYaExistenteException("Ya existe un usuario con ese email");
        }

        User user = new User();
        user.setNombre(userDTO.getNombre());
        user.setApellido(userDTO.getApellido());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setRole(userDTO.getRole());

        // 🔁 Guardar primero el usuario para que tenga ID
        User result = repo.save(user);

        // Si el usuario es una desarrolladora
        if (result.getRole().equals(Role.DESARROLLADORA)) {
            Desarrolladora desarrolladora = new Desarrolladora();
            desarrolladora.setNombre(userDTO.getNombreDesarrolladora());
            desarrolladora.setPaisOrigen(userDTO.getPaisOrigen());
            desarrolladora.setUser(result);
            //Guardo la Desarrolladora
            Desarrolladora desResult = desarrolladoraService.create(desarrolladora);

            // Guardo nuevamente el usuario ahora con su Desarrolladora almacenada.
            result.setDesarrolladora(desResult);
            repo.save(result);
        }

        // Si el usuario es un perfil común
        else if (result.getRole().equals(Role.PERFIL)) {
            Perfil perfil = new Perfil();
            perfil.setNickName(userDTO.getNickname());
            perfil.setUser(result);
            //Inicializo y Guardo el Perfil
            Perfil perResult = perfilService.create(perfil); // <- Esta función también crea dependencias necesarias de Perfil (como Billetera)

            // Guardo nuevamente el usuario ahora con su Perfil almacenado.
            result.setPerfil(perResult);
            repo.save(result);
        }

        else {//Si el rol ingresado no existe
            throw new RuntimeException("El rol no existe");
        }

        return result;
    }

    @Override
    public List<UserVerDTO> getAll() {
        return repo.findAll().stream().map(this::convertirAVerDTO).toList();
    }

    @Override
    public Optional<User> getById(Long id) {
        return repo.findById(id);
    }

    @Override
    public User update(Long id, UserUpdateDTO nuevo) {

        User existente = repo.findById(id)
                        .orElseThrow(()->new ElementoNoEncontradoException("User con ID "+id+" no fue encontrado"));

        if (nuevo.getNombre()!=null) existente.setNombre(nuevo.getNombre());
        if (nuevo.getApellido()!=null) existente.setApellido(nuevo.getApellido());
        if (nuevo.getPassword()!=null) existente.setPassword(nuevo.getPassword());
        return repo.save(existente);
    }

    @Override
    public void delete(Long id) {
        if (repo.existsById(id)){
            repo.deleteById(id);
        }else throw new ElementoNoEncontradoException("User con ID "+id+" no fue encontrado");
    }

    @Override
    public Optional<User> getByEmail(String email) {
        return repo.findByEmail(email);
    }

    public UserVerDTO convertirAVerDTO(User user){
        UserVerDTO dto = new UserVerDTO();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());

        if (user.getPerfil()!=null){
            dto.setNickName(user.getPerfil().getNickName());
        } else if (user.getDesarrolladora()!=null) {
            dto.setNombreDesarrolladora(user.getDesarrolladora().getNombre());
        }
        return dto;
    }
}
