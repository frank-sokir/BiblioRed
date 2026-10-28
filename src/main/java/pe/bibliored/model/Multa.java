package pe.bibliored.model; import jakarta.persistence.*;
/** Entidad persistente Multa del dominio bibliotecario. */
@Entity @Table(name="multas") public class Multa {@Id @GeneratedValue(strategy=GenerationType.IDENTITY) public Long id; @Version public Long version; @Column(unique=true) public Long prestamoId; public java.math.BigDecimal monto; public String motivo; public String estado="PENDIENTE";}
