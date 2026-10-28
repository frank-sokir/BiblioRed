package pe.bibliored.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.bibliored.model.Devolucion;

/** Contrato DAO para persistencia de Devolucion. */
public interface DevolucionDao extends JpaRepository<Devolucion, Long> {}
