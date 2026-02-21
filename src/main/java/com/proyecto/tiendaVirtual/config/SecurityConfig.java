package com.proyecto.tiendaVirtual.config;

import com.proyecto.tiendaVirtual.config.jwt.JwtAuthenticationFilter;
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
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    // ============================
    // AuthenticationManager
    // ============================
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {

        AuthenticationManagerBuilder authBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);

        authBuilder
                .userDetailsService(userService)
                .passwordEncoder(passwordEncoder);

        return authBuilder.build();
    }

    // ============================
    // CORS
    // ============================
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOrigins(List.of("http://localhost:4200", "https://localhost:4200"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", config);
        return source;
    }

    // ============================
    // Security Filter Chain
    // ============================
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                .cors(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable())

                // 🔐 JWT = STATELESS
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                .authorizeHttpRequests(auth -> auth

                        // AUTH
                        .requestMatchers("/auth/**").permitAll()

                        // Testeo
                        .requestMatchers("/api/test/**").permitAll()

                        // User
                        .requestMatchers(HttpMethod.POST, "/api/users").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/users/me").authenticated()

                        // Desarrolladora
                        .requestMatchers(HttpMethod.GET, "/api/desarrolladora/**").authenticated()
                        .requestMatchers("/api/desarrolladora/**").hasRole("DESARROLLADORA")

                        // Perfil
                        .requestMatchers(HttpMethod.GET, "/api/perfil/**").authenticated()
                        .requestMatchers("/api/perfil/**").hasRole("PERFIL")

                        // Billetera
                        .requestMatchers("/api/billetera/**").hasRole("PERFIL")

                        // Juego
                        .requestMatchers(HttpMethod.GET, "/api/juego/**").authenticated()
                        .requestMatchers("/api/juego/*/comprar").hasRole("PERFIL")
                        .requestMatchers("/api/juego/**").hasRole("DESARROLLADORA")

                        // Carrito
                        .requestMatchers(HttpMethod.GET, "/api/carrito").hasRole("PERFIL")
                        .requestMatchers(HttpMethod.POST, "/api/carrito/add").hasRole("PERFIL")
                        .requestMatchers(HttpMethod.DELETE, "/api/carrito/remove/**").hasRole("PERFIL")
                        .requestMatchers(HttpMethod.DELETE, "/api/carrito/clear").hasRole("PERFIL")

                        // Compras
                        .requestMatchers("/api/compra/**").hasRole("PERFIL")

                        //Mercado pago
                        .requestMatchers("/api/payments/**").hasRole("PERFIL")

                        //Webhook
                        .requestMatchers("/api/mp/webhook").permitAll()

                        //MercadoPago redirects
                        .requestMatchers(
                                "/wallet-ok",
                                "/wallet-error",
                                "/wallet-pending"
                        ).permitAll()

                        .anyRequest().authenticated()
                )

                // 🧠 JWT FILTER
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                )

                // ❌ NO Basic Auth
                .build();
    }
}
