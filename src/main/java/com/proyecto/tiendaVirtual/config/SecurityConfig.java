package com.proyecto.tiendaVirtual.config;

import jakarta.servlet.FilterChain;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{
        http.
                authorizeHttpRequests(authorize -> authorize  // metodo para autorizar solicitudes
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/desarrolladora/**").hasRole("DESARROLLADOR")
                        .requestMatchers("/cliente/**").hasRole("CLIENTE")
                        .requestMatchers("/public/**").permitAll()
                        .anyRequest().authenticated()    // Cualquier otra solicitud requiere autenticación
                ).formLogin(form->form.permitAll()    // Permite a todos acceder a la página de login
                ).logout(logout->logout.logoutSuccessUrl("/login").permitAll()   // Permite a todos acceder al logout
                );
        return http.build();
    }

//    @Controller
//    public class AdminController {
//
//        @GetMapping("/admin/dashboard")
//        public String adminDashboard() {
//            return "admin/dashboard";
//        }
//    }
//
//    @Controller
//    public class UserController {
//
//        @GetMapping("/user/dashboard")
//        public String userDashboard() {
//            return "user/dashboard";
//        }
//    }


}
