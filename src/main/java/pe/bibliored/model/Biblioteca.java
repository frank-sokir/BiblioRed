package pe.bibliored.model;

import jakarta.persistence.*;

/** Entidad persistente Biblioteca del dominio bibliotecario. */
@Entity
@Table(name = "bibliotecas")
public class Biblioteca {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public Long id;

  @Version public Long version;
  public String nombre;
  public String direccion;
  public String telefono;
}
