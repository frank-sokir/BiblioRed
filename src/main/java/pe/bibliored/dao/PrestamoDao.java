package pe.bibliored.dao; import pe.bibliored.model.Prestamo; import org.springframework.data.jpa.repository.JpaRepository;
/** Contrato DAO para persistencia de Prestamo. */
public interface PrestamoDao extends JpaRepository<Prestamo,Long> {}
