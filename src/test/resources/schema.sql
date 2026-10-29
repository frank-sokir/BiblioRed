ALTER TABLE usuarios ADD CONSTRAINT IF NOT EXISTS fk_usuarios_biblioteca_id FOREIGN KEY (biblioteca_id) REFERENCES bibliotecas(id);
ALTER TABLE ejemplares ADD CONSTRAINT IF NOT EXISTS fk_ejemplares_biblioteca_id FOREIGN KEY (biblioteca_id) REFERENCES bibliotecas(id);
ALTER TABLE ejemplares ADD CONSTRAINT IF NOT EXISTS fk_ejemplares_libro_id FOREIGN KEY (libro_id) REFERENCES libros(id);
ALTER TABLE prestamos ADD CONSTRAINT IF NOT EXISTS fk_prestamos_usuario_id FOREIGN KEY (usuario_id) REFERENCES usuarios(id);
ALTER TABLE prestamos ADD CONSTRAINT IF NOT EXISTS fk_prestamos_ejemplar_id FOREIGN KEY (ejemplar_id) REFERENCES ejemplares(id);
ALTER TABLE devoluciones ADD CONSTRAINT IF NOT EXISTS fk_devoluciones_prestamo_id FOREIGN KEY (prestamo_id) REFERENCES prestamos(id);
ALTER TABLE multas ADD CONSTRAINT IF NOT EXISTS fk_multas_prestamo_id FOREIGN KEY (prestamo_id) REFERENCES prestamos(id);