package pe.bibliored.dao; import pe.bibliored.model.Ejemplar; import org.springframework.data.jpa.repository.JpaRepository;
/** Contrato DAO para persistencia de Ejemplar. */
public interface EjemplarDao extends JpaRepository<Ejemplar,Long> {@org.springframework.data.jpa.repository.Lock(jakarta.persistence.LockModeType.PESSIMISTIC_WRITE) @org.springframework.data.jpa.repository.Query("select e from Ejemplar e where e.id = :id") java.util.Optional<Ejemplar> bloquear(@org.springframework.data.repository.query.Param("id") Long id);}
