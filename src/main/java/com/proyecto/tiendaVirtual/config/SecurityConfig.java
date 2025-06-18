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
        AuthenticationManagerBuilder authBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authBuilder
                .userDetailsService(userService)
                .passwordEncoder(passwordEncoder);
        return authBuilder.build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{
        return http
                .csrf(csrf->csrf.disable())
                .authorizeHttpRequests(auth->auth
                        //Testeo
                        .requestMatchers("/api/test/**").authenticated()

                        //User
                        .requestMatchers(HttpMethod.POST,"/api/users").permitAll()

                        //Desarrolladora
                        .requestMatchers(HttpMethod.GET,"/api/desarrolladora/**").authenticated() //Se permite cualquier GET
                        .requestMatchers("/api/desarrolladora/**").hasRole("DESARROLLADORA") //Otros métodos POST/PUT/DEL requieren el Rol

                        //Perfil
                        .requestMatchers(HttpMethod.GET,"/api/perfil/**").authenticated() //Se permite cualquier GET
                        .requestMatchers("/api/perfil/**").hasRole("PERFIL") //Otros métodos POST/PUT/DEL requieren el Rol

                        //Billetera
                        .requestMatchers("/api/billetera/**").hasRole("PERFIL")

                        //Juego
                        .requestMatchers(HttpMethod.GET,"/api/juego/**").authenticated() //Se permite cualquier GET
                        .requestMatchers("/api/juego/*/comprar").hasRole("PERFIL") //Solo Perfiles pueden comprar Juegos
                        .requestMatchers("/api/juego/**").hasRole("DESARROLLADORA") //Otros métodos POST/PUT/DEL requieren el Rol


                        //Otras Rutas
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .build();
    }
}
