package pe.bibliored.controller;

import java.util.Map;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class Errores {
  @ExceptionHandler(IllegalArgumentException.class)
  ResponseEntity<?> regla(IllegalArgumentException e) {
    return ResponseEntity.badRequest().body(Map.of("mensaje", e.getMessage()));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  ResponseEntity<?> validacion(Exception e) {
    return ResponseEntity.badRequest().body(Map.of("mensaje", "Revisa los campos y sus formatos"));
  }

  @ExceptionHandler({
    DataIntegrityViolationException.class,
    ObjectOptimisticLockingFailureException.class
  })
  ResponseEntity<?> conflicto(Exception e) {
    return ResponseEntity.status(409)
        .body(
            Map.of("mensaje", "El registro ya existe o cambió. Actualiza e inténtalo nuevamente"));
  }
}
