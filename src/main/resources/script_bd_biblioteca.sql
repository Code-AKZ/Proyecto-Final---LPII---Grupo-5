CREATE DATABASE IF NOT EXISTS bd_biblioteca CHARACTER SET utf8mb4;
USE bd_biblioteca;

CREATE TABLE IF NOT EXISTS tb_categoria (
  id BIGINT NOT NULL AUTO_INCREMENT,
  nombre VARCHAR(50) NOT NULL,
  descripcion VARCHAR(150),
  PRIMARY KEY (id),
  UNIQUE KEY uk_categoria_nombre (nombre)
);

CREATE TABLE IF NOT EXISTS tb_libro (
  id BIGINT NOT NULL AUTO_INCREMENT,
  titulo VARCHAR(100) NOT NULL,
  isbn VARCHAR(20) NOT NULL,
  precio DOUBLE NOT NULL,
  disponible BIT NOT NULL DEFAULT 1,
  categoria_id BIGINT NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY uk_libro_isbn (isbn),
  CONSTRAINT fk_libro_categoria FOREIGN KEY (categoria_id) REFERENCES tb_categoria (id)
);

CREATE TABLE IF NOT EXISTS tb_lector (
  id BIGINT NOT NULL AUTO_INCREMENT,
  nombres VARCHAR(80) NOT NULL,
  dni VARCHAR(12) NOT NULL,
  correo VARCHAR(80),
  activo BIT NOT NULL DEFAULT 1,
  PRIMARY KEY (id),
  UNIQUE KEY uk_lector_dni (dni)
);

CREATE TABLE IF NOT EXISTS tb_prestamo (
  id BIGINT NOT NULL AUTO_INCREMENT,
  libro_id BIGINT NOT NULL,
  lector_id BIGINT NOT NULL,
  fecha_prestamo DATE NOT NULL,
  fecha_devolucion DATE NULL,
  estado VARCHAR(20) NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT fk_prestamo_libro FOREIGN KEY (libro_id) REFERENCES tb_libro (id),
  CONSTRAINT fk_prestamo_lector FOREIGN KEY (lector_id) REFERENCES tb_lector (id)
);
