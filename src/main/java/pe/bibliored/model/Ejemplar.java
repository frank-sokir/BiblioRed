package pe.bibliored.model;

import jakarta.persistence.*;

/** Entidad persistente Ejemplar del dominio bibliotecario. */
@Entity
@Table(name = "ejemplares")
public class Ejemplar {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public Long id;

  @Version public Long version;
  public Long bibliotecaId;
  public Long libroId;

  @Column(unique = true)
  public String codigo;

  public String estado = "DISPONIBLE";
}
