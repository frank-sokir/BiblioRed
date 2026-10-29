-- BiblioRed: importar una sola vez en un esquema nuevo. No elimina datos existentes.
SET NAMES utf8mb4;
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

-- Datos ficticios para la demostración académica.
-- Contraseña de acceso a la página para estas tres cuentas: BiblioRed2026!
-- Se guarda únicamente el hash BCrypt de esa contraseña.
INSERT INTO bibliotecas(id,version,nombre,direccion,telefono) VALUES
(1,0,'Biblioteca Central Lima','Lima, Perú',NULL),
(2,0,'Biblioteca Los Olivos','Lima, Perú',NULL),
(3,0,'Biblioteca San Miguel','Lima, Perú',NULL);
INSERT INTO usuarios(id,version,biblioteca_id,nombre,dni,correo,password_hash,rol,activo) VALUES
(1,0,1,'Administrador demo','00000001','admin@bibliored.local','$2a$10$thG60Fnl8bKOFoWd3Xrt6eWmOPF9V/Hyh9a3AYwj6m1sewV6nEu9q','ADMIN',true),
(2,0,1,'Personal demo','00000002','personal@bibliored.local','$2a$10$thG60Fnl8bKOFoWd3Xrt6eWmOPF9V/Hyh9a3AYwj6m1sewV6nEu9q','PERSONAL',true),
(3,0,1,'Lector demo','00000003','lector@bibliored.local','$2a$10$thG60Fnl8bKOFoWd3Xrt6eWmOPF9V/Hyh9a3AYwj6m1sewV6nEu9q','USUARIO',true);
INSERT INTO libros(id,version,titulo,autor,categoria,isbn,editorial) VALUES
(1,0,'Don Quijote de la Mancha','Miguel de Cervantes','Literatura','9788420412146','Catálogo de demostración'),
(2,0,'La ciudad y los perros','Mario Vargas Llosa','Literatura','9788420471839','Catálogo de demostración'),
(3,0,'El principito','Antoine de Saint-Exupéry','Narrativa','9780156012195','Catálogo de demostración'),
(4,0,'Cien años de soledad','Gabriel García Márquez','Literatura','9780307474728','Catálogo de demostración'),
(5,0,'Historia del Perú','Jorge Basadre','Historia','9786120000010','Catálogo de demostración'),
(6,0,'Breve historia del tiempo','Stephen Hawking','Ciencia','9780553380163','Catálogo de demostración');
INSERT INTO ejemplares(id,version,biblioteca_id,libro_id,codigo,estado) VALUES
(1,0,1,1,'LIB-001','DISPONIBLE'),
(2,0,2,1,'LIB-002','DISPONIBLE'),
(3,0,3,1,'LIB-003','DISPONIBLE'),
(4,0,1,2,'LIB-004','DISPONIBLE'),
(5,0,2,2,'LIB-005','DISPONIBLE'),
(6,0,3,2,'LIB-006','DISPONIBLE'),
(7,0,1,3,'LIB-007','DISPONIBLE'),
(8,0,2,3,'LIB-008','DISPONIBLE'),
(9,0,3,3,'LIB-009','DISPONIBLE'),
(10,0,1,4,'LIB-010','DISPONIBLE'),
(11,0,2,4,'LIB-011','DISPONIBLE'),
(12,0,3,4,'LIB-012','DISPONIBLE'),
(13,0,1,5,'LIB-013','DISPONIBLE'),
(14,0,2,5,'LIB-014','DISPONIBLE'),
(15,0,3,5,'LIB-015','DISPONIBLE'),
(16,0,1,6,'LIB-016','DISPONIBLE'),
(17,0,2,6,'LIB-017','DISPONIBLE'),
(18,0,3,6,'LIB-018','DISPONIBLE');
