package pe.bibliored.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.bibliored.model.Multa;

/** Contrato DAO para persistencia de Multa. */
public interface MultaDao extends JpaRepository<Multa, Long> {}
