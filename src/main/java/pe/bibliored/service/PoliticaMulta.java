package pe.bibliored.service;

import java.math.BigDecimal;
import java.time.LocalDate;

/** Estrategia para calcular el importe por retraso. */
public interface PoliticaMulta {
  BigDecimal calcular(LocalDate limite, LocalDate devolucion);
}
