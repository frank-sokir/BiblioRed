package pe.bibliored.model;

import jakarta.persistence.*;

/** Entidad persistente Prestamo del dominio bibliotecario. */
@Entity
@Table(name = "prestamos")
public class Prestamo {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public Long id;

  @Version public Long version;
  public Long usuarioId;
  public Long ejemplarId;
  public java.time.LocalDate fechaSolicitud;
  public java.time.LocalDate fechaRecojo;
  public java.time.LocalDate fechaPrestamo;
  public java.time.LocalDate fechaLimite;
  public String estado = "SOLICITADO";
}
