package pe.bibliored.model;

import jakarta.persistence.*;

/** Entidad persistente Devolucion del dominio bibliotecario. */
@Entity
@Table(name = "devoluciones")
public class Devolucion {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public Long id;

  @Version public Long version;

  @Column(unique = true)
  public Long prestamoId;

  public java.time.LocalDate fechaDevolucion;
  public String observacion;
}
