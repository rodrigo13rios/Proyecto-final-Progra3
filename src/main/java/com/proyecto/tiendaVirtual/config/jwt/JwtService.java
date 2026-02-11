package com.proyecto.tiendaVirtual.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {


    // 🔐 CLAVE JWT (mínimo 256 bits)
    private static final String SECRET_KEY =
            "EstaEsLaClaveConLaQueJWTVaAEncriptarLosDatos";

    // ⏰ 1 hora
    private static final long EXPIRATION_TIME = 1000 * 60 * 60;

    // ========================
    // GENERAR TOKEN
    // ========================
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // ========================
    // EXTRAER USERNAME
    // ========================
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // ========================
    // VALIDAR TOKEN
    // ========================
    public boolean isTokenValid(String token) {
        return !isTokenExpired(token);
    }

    // ========================
    // HELPERS
    // ========================
    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }

    private <T> T extractClaim(String token, Function<Claims, T> resolver) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return resolver.apply(claims);
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
