package pe.bibliored.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.bibliored.model.Prestamo;

/** Contrato DAO para persistencia de Prestamo. */
public interface PrestamoDao extends JpaRepository<Prestamo, Long> {}
