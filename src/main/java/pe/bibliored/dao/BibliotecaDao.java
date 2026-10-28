package pe.bibliored.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.bibliored.model.Biblioteca;

/** Contrato DAO para persistencia de Biblioteca. */
public interface BibliotecaDao extends JpaRepository<Biblioteca, Long> {}
