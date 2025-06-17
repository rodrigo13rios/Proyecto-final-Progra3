package com.proyecto.tiendaVirtual.config;

import com.proyecto.tiendaVirtual.user.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
    @Autowired
    private UserServiceImpl userService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{
        return http
                .csrf(csrf->csrf.disable())
                .authorizeHttpRequests(auth->auth
                        .requestMatchers(HttpMethod.POST,"/api/users/create")
                                        .permitAll()
                        .requestMatchers("/api/users/**")
                                        .authenticated()
                        .requestMatchers("/api/juego/get",
                                         "/api/juego/buscarPorId"
                                        ,"/api/juego/buscarPorNombre")
                                        .permitAll()
                        .requestMatchers("/api/juegos/create",
                                         "/api/juegos/update",
                                         "/api/juegos/delete")
                                        .hasRole("DESARROLLADORA")
                        .requestMatchers("/api/perfil/**")
                                        .hasRole("PERFIL")
                        .requestMatchers("/api/billetera/**")
                                        .hasRole("PERFIL")
                        .requestMatchers("/api/desarrolladora/**")
                                        .hasRole("Desarrolladora")
                        .anyRequest().authenticated()

                ).build();
        //La parte de crear usuario, ver los juegos son de acceso publico.
        //La creacion, actualizacion y borrar los videojuegos solo desde el rol DESARROLLADORA
        //Lo que es el perfil y la billetera es accesible desde el rol Perfil.
    }
}
