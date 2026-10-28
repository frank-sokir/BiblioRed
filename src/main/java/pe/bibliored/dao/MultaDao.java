package pe.bibliored.dao; import pe.bibliored.model.Multa; import org.springframework.data.jpa.repository.JpaRepository;
/** Contrato DAO para persistencia de Multa. */
public interface MultaDao extends JpaRepository<Multa,Long> {}
