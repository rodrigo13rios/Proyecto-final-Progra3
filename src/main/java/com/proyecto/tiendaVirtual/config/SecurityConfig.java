package com.proyecto.tiendaVirtual.config;

import com.proyecto.tiendaVirtual.user.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userService)
                .passwordEncoder(passwordEncoder)
                .and()
                .build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{
        return http
                .csrf(csrf->csrf.disable())
                .authorizeHttpRequests(auth->auth
                        .requestMatchers("/api/test/**").authenticated()
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
                        .hasRole("DESARROLLADORA")
                        .anyRequest().authenticated()

                ).httpBasic(Customizer.withDefaults())
                .build();
    }
}
