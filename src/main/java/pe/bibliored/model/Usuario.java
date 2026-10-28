package pe.bibliored.model; import jakarta.persistence.*;
/** Entidad persistente Usuario del dominio bibliotecario. */
@Entity @Table(name="usuarios") public class Usuario {@Id @GeneratedValue(strategy=GenerationType.IDENTITY) public Long id; @Version public Long version; public Long bibliotecaId; public String nombre; @Column(unique=true,nullable=false) public String dni; @Column(unique=true,nullable=false) public String correo; @com.fasterxml.jackson.annotation.JsonIgnore public String passwordHash; public String rol="USUARIO"; public boolean activo=true;}
