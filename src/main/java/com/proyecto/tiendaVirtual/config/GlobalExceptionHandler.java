package com.proyecto.tiendaVirtual.config;

import com.proyecto.tiendaVirtual.exceptions.AccesoDenegadoException;
import com.proyecto.tiendaVirtual.exceptions.ElementoNoEncontradoException;
import com.proyecto.tiendaVirtual.exceptions.ElementoYaExistenteException;
import com.proyecto.tiendaVirtual.exceptions.NumeroInvalidoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ElementoNoEncontradoException.class)

    public ResponseEntity<Map<String, String>> catchElementoNoEncontrado(ElementoNoEncontradoException ex) {

        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
    @ExceptionHandler(ElementoYaExistenteException.class)

    public ResponseEntity<Map<String, String>> catchElementoYaExistente(ElementoYaExistenteException ex) {

        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }
    @ExceptionHandler(NumeroInvalidoException.class)
    public ResponseEntity<Map<String,String>> catchNumeroInvalido(NumeroInvalidoException ex){
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }
    @ExceptionHandler(AccesoDenegadoException.class)
    public ResponseEntity<Map<String,String>> catchAccesoNegado(AccesoDenegadoException ex){
        Map<String,String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String, String>> catchBadCredentials(BadCredentialsException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Credenciales inválidas");
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(error);
    }


    // Manejo genérico para cualquier otra excepción no controlada
    @ExceptionHandler(Exception.class)

    public ResponseEntity<Map<String, String>> catchException(Exception ex) {

        Map<String, String> error = new HashMap<>();
//        error.put("error", "Ha ocurrido un error inesperado");
        error.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
