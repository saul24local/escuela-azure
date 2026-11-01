package com.gerdoc.escuela.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler
{
    // Se dispara al borrar un registro que tiene hijos (llave foránea) o al duplicar un registro único
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, String>> integridad(DataIntegrityViolationException ex)
    {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(Map.of("error",
                        "Operación rechazada por integridad referencial: el registro tiene "
                      + "relaciones con otros catálogos o ya existe."));
    }
}
