package com.proyecto.tiendaVirtual.user.model;

import com.proyecto.tiendaVirtual.perfil.model.Perfil;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String apellido;
    private String email;
    private String password;
    @OneToOne
    @JoinColumn(name = "id_perfil")
    private Perfil perfil;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",joinColumns = @JoinColumn(name="id_user"),inverseJoinColumns = @JoinColumn(name = "id_role"))
    private Set<Role> roles= new HashSet<>();
}
