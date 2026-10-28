package pe.bibliored.dao; import pe.bibliored.model.Devolucion; import org.springframework.data.jpa.repository.JpaRepository;
/** Contrato DAO para persistencia de Devolucion. */
public interface DevolucionDao extends JpaRepository<Devolucion,Long> {}
