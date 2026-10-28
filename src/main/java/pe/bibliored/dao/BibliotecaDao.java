package pe.bibliored.dao; import pe.bibliored.model.Biblioteca; import org.springframework.data.jpa.repository.JpaRepository;
/** Contrato DAO para persistencia de Biblioteca. */
public interface BibliotecaDao extends JpaRepository<Biblioteca,Long> {}
