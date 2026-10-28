CREATE DATABASE IF NOT EXISTS bibliored CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE bibliored;
CREATE TABLE bibliotecas(id BIGINT PRIMARY KEY AUTO_INCREMENT,version BIGINT,nombre VARCHAR(255),direccion VARCHAR(255),telefono VARCHAR(255));
CREATE TABLE usuarios(id BIGINT PRIMARY KEY AUTO_INCREMENT,version BIGINT,biblioteca_id BIGINT NOT NULL,nombre VARCHAR(255),dni VARCHAR(255) NOT NULL UNIQUE,correo VARCHAR(255) NOT NULL UNIQUE,password_hash VARCHAR(255),rol VARCHAR(255),activo BOOLEAN NOT NULL,FOREIGN KEY(biblioteca_id) REFERENCES bibliotecas(id));
CREATE TABLE libros(id BIGINT PRIMARY KEY AUTO_INCREMENT,version BIGINT,titulo VARCHAR(255),autor VARCHAR(255),isbn VARCHAR(255) UNIQUE,categoria VARCHAR(255),editorial VARCHAR(255));
CREATE TABLE ejemplares(id BIGINT PRIMARY KEY AUTO_INCREMENT,version BIGINT,biblioteca_id BIGINT NOT NULL,libro_id BIGINT NOT NULL,codigo VARCHAR(255) UNIQUE,estado VARCHAR(255),FOREIGN KEY(biblioteca_id) REFERENCES bibliotecas(id),FOREIGN KEY(libro_id) REFERENCES libros(id));
CREATE TABLE prestamos(id BIGINT PRIMARY KEY AUTO_INCREMENT,version BIGINT,usuario_id BIGINT NOT NULL,ejemplar_id BIGINT NOT NULL,fecha_solicitud DATE,fecha_recojo DATE,fecha_prestamo DATE,fecha_limite DATE,estado VARCHAR(255),FOREIGN KEY(usuario_id) REFERENCES usuarios(id),FOREIGN KEY(ejemplar_id) REFERENCES ejemplares(id));
CREATE TABLE devoluciones(id BIGINT PRIMARY KEY AUTO_INCREMENT,version BIGINT,prestamo_id BIGINT NOT NULL UNIQUE,fecha_devolucion DATE,observacion VARCHAR(255),FOREIGN KEY(prestamo_id) REFERENCES prestamos(id));
CREATE TABLE multas(id BIGINT PRIMARY KEY AUTO_INCREMENT,version BIGINT,prestamo_id BIGINT NOT NULL UNIQUE,monto NUMERIC(38,2),motivo VARCHAR(255),estado VARCHAR(255),FOREIGN KEY(prestamo_id) REFERENCES prestamos(id));
CREATE INDEX idx_prestamos_usuario ON prestamos(usuario_id);
CREATE INDEX idx_ejemplares_libro ON ejemplares(libro_id);
-- Registrar bibliotecas y usuarios con hash BCrypt antes de activar el perfil mysql.
-- No insertar contraseñas en texto plano. El perfil mysql no carga cuentas demo.
