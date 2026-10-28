package pe.bibliored.model;

import jakarta.persistence.*;

/** Entidad persistente Libro del dominio bibliotecario. */
@Entity
@Table(name = "libros")
public class Libro {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public Long id;

  @Version public Long version;
  public String titulo;
  public String autor;

  @Column(unique = true)
  public String isbn;

  public String categoria;
  public String editorial;
}
