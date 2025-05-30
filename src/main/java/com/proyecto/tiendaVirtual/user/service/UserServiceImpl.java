package com.proyecto.tiendaVirtual.user.service;

import com.proyecto.tiendaVirtual.exceptions.ElementoExistenteException;
import com.proyecto.tiendaVirtual.exceptions.ElementoNoEncontradoException;
import com.proyecto.tiendaVirtual.exceptions.ListaVaciaException;
import com.proyecto.tiendaVirtual.user.model.Role;
import com.proyecto.tiendaVirtual.user.model.User;
import com.proyecto.tiendaVirtual.user.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder=passwordEncoder;
    }

    public Optional<User> findByUsername(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User createUser(User user) throws ElementoExistenteException {
        if (userRepository.existsById(user.getId())){
            throw new ElementoExistenteException("El usuario ya existe");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUser() throws ListaVaciaException {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(Long id) throws ElementoNoEncontradoException {
        return Optional.empty();
    }

    @Override
    public Optional<User> getUserByEmail(String email) throws ElementoNoEncontradoException {
        Optional<User>optional=userRepository
                .findAll()
                .stream()
                .filter(x->x.getEmail().equals(email))
                .findFirst();

        if(optional.isEmpty())throw new ElementoNoEncontradoException("no se encontro un usuario con el email: "+email);
        return optional;
    }

    @Override
    public void updateUser(Long id, User userUpdated) throws ElementoNoEncontradoException {
        if(userRepository.existsById(id)){
            userRepository.deleteById(id);
            userRepository.save(userUpdated);
        }
        throw new ElementoNoEncontradoException("No se encontro el usuario ingresado.");
    }

    @Override
    public void deleteUser(Long id) throws ElementoNoEncontradoException {
        if(userRepository.existsById(id)){
            userRepository.deleteById(id);
        }
        throw new ElementoNoEncontradoException("No se encontro el usuario ingresado");
    }


    @Override
    public UserDetails loadUserByUsername(String email)throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByEmail(email);
        User user= userOptional.orElseThrow(()->new UsernameNotFoundException("No se a encontrado un usuario con el mail: "+email));

        return new org.springframework.security.core.userdetails.User(//Este es el objeto que Spring Security utiliza internamente para representar los detalles de un usuario autenticado
                user.getNombre(),
                user.getPassword(),//Spring Security se encargará de compararla con la contraseña proporcionada por el usuario
                getAuthority(user.getRoles()));
    }


                                    //Al usar un Set<Role>, aseguramos de que cada rol sea único.
    private Set<GrantedAuthority> getAuthority(Set<Role> roles){//Este es un metodo  privado que transforma los roles en un formato que Spring Security puede entender.
        return roles.stream().map(role ->
                new SimpleGrantedAuthority("ROL_"+role.getName()))
                .collect(Collectors.toSet());
    }
}
