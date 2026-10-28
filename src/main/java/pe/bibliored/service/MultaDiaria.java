package pe.bibliored.service;import java.math.BigDecimal;import java.time.*;import java.time.temporal.ChronoUnit;import org.springframework.stereotype.Component;
/** Tarifa académica de S/ 1 por día, reemplazable mediante PoliticaMulta. */
@Component public class MultaDiaria implements PoliticaMulta {public BigDecimal calcular(LocalDate limite,LocalDate devolucion){return BigDecimal.valueOf(Math.max(0,ChronoUnit.DAYS.between(limite,devolucion))).setScale(2);}}
