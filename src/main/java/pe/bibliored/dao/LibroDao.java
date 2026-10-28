package pe.bibliored.dao; import pe.bibliored.model.Libro; import org.springframework.data.jpa.repository.JpaRepository;
/** Contrato DAO para persistencia de Libro. */
public interface LibroDao extends JpaRepository<Libro,Long> {}
