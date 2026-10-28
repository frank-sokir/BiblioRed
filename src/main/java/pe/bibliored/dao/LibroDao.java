package pe.bibliored.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.bibliored.model.Libro;

/** Contrato DAO para persistencia de Libro. */
public interface LibroDao extends JpaRepository<Libro, Long> {}
