package pe.bibliored.dao; import pe.bibliored.model.Usuario; import org.springframework.data.jpa.repository.JpaRepository;
/** Contrato DAO para persistencia de Usuario. */
public interface UsuarioDao extends JpaRepository<Usuario,Long> {java.util.Optional<Usuario> findByCorreoIgnoreCase(String correo); boolean existsByCorreoIgnoreCase(String correo); boolean existsByDni(String dni);}
