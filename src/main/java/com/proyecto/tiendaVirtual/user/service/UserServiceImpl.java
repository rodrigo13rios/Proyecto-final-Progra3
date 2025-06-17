package com.proyecto.tiendaVirtual.user.service;

import com.proyecto.tiendaVirtual.config.PasswordConfig;
import com.proyecto.tiendaVirtual.desarrolladora.model.Desarrolladora;
import com.proyecto.tiendaVirtual.desarrolladora.service.DesarrolladoraService;
import com.proyecto.tiendaVirtual.exceptions.ElementoNoEncontradoException;
import com.proyecto.tiendaVirtual.exceptions.ElementoYaExistenteException;
import com.proyecto.tiendaVirtual.perfil.model.Perfil;
import com.proyecto.tiendaVirtual.perfil.service.PerfilService;
import com.proyecto.tiendaVirtual.user.dto.UserDTO;
import com.proyecto.tiendaVirtual.user.dto.UserUpdateDTO;
import com.proyecto.tiendaVirtual.user.dto.UserVerDTO;
import com.proyecto.tiendaVirtual.user.model.Role;
import com.proyecto.tiendaVirtual.user.model.User;
import com.proyecto.tiendaVirtual.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    @Autowired
    UserRepository repo;
    @Autowired
    PerfilService perfilService;
    @Autowired
    DesarrolladoraService desarrolladoraService;
    @Autowired
    PasswordEncoder passwordEncoder;


    public User createUser(UserDTO dto){
        if(repo.existByEmail(dto.getEmail()))throw new ElementoYaExistenteException("Ya se encuentra un usuario con el email "+dto.getEmail()+" cargado");
        User user;
        if (dto.getRole().equals(Role.PERFIL)){
           user = createUserPerfil(dto);
        }else {
           user = createUserDesarrolladora(dto);
        }
        return user;
    }
    @Override
    public User createUserPerfil(UserDTO dto) {


        Perfil perfil = perfilService.create(dto);

        User user = new User();
        user.setNombre(dto.getNombre());
        user.setApellido(dto.getApellido());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(dto.getRole());
        user.setPerfil(perfil);


        perfil.setUser(user);

        return repo.save(user);
    }

    public User createUserDesarrolladora(UserDTO dto){
        //cambiar en otra rama Desarrolladora desarrolladora = desarrolladoraService.create(dto);
        Desarrolladora desarrolladora = new Desarrolladora();
        desarrolladora.setNombre(dto.getNombreDesarrolladora());
        desarrolladora.setPaisOrigen(dto.getPaisOrigen());


        User user = new User();
        user.setNombre(dto.getNombre());
        user.setApellido(dto.getApellido());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(dto.getRole());
        user.setDesarrolladora(desarrolladora);

        return repo.save(user);
    }
    @Override
    public List<UserVerDTO> getAll() {
        return repo.findAll().stream().map(this::convertirAVerDTO).toList();
    }

    @Override
    public Optional<User> getById(Long id) {
        Optional<User> optional = repo.findById(id);
        if (optional.isEmpty())throw new ElementoNoEncontradoException("User con el ID "+ id +" no fue encontrado");
        return optional;
    }

    @Override
    public User update(Long id, UserUpdateDTO nuevo) {

        User existente = repo.findById(id)
                        .orElseThrow(()->new ElementoNoEncontradoException("User con ID "+id+" no fue encontrado"));

        if (nuevo.getNombre()!=null) existente.setNombre(nuevo.getNombre());
        if (nuevo.getApellido()!=null) existente.setApellido(nuevo.getApellido());
        if (nuevo.getPassword()!=null) existente.setPassword(passwordEncoder.encode(nuevo.getPassword()));
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
        Optional<User> optional = repo.findByEmail(email);
        if (optional.isEmpty())throw new ElementoNoEncontradoException("User con email "+email+" no fue encontrado");
        return optional;
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

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> optional = repo.findByEmail(email);
        User user = optional.orElseThrow(()-> new ElementoNoEncontradoException("El email no se encuentra registrado"));
        if (user.getRole().equals(Role.DESARROLLADORA)){

            return new org.springframework.security.core.userdetails.User(
                    user.getEmail(),
                    user.getPassword(),
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_DESARROLLADORA"))
            );
        }
        else return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_PERFIL"))
        );

    }
}
