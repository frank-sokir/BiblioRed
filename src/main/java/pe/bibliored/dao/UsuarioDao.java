package pe.bibliored.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.bibliored.model.Usuario;

/** Contrato DAO para persistencia de Usuario. */
public interface UsuarioDao extends JpaRepository<Usuario, Long> {
  java.util.Optional<Usuario> findByCorreoIgnoreCase(String correo);

  boolean existsByCorreoIgnoreCase(String correo);

  boolean existsByDni(String dni);
}
