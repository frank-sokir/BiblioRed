package pe.bibliored;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import pe.bibliored.service.MultaDiaria;

class MultaDiariaTest {
  final MultaDiaria regla = new MultaDiaria();
  final LocalDate limite = LocalDate.of(2026, 10, 20);

  @Test
  void sinRetraso() {
    assertEquals(new BigDecimal("0.00"), regla.calcular(limite, limite));
  }

  @Test
  void entregaAnticipada() {
    assertEquals(new BigDecimal("0.00"), regla.calcular(limite, limite.minusDays(2)));
  }

  @Test
  void tresDiasRetraso() {
    assertEquals(new BigDecimal("3.00"), regla.calcular(limite, limite.plusDays(3)));
  }
}
