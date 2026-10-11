# Gestor de Biblioteca (prototipo JPA)

Proyecto Maven de consola para el curso Lenguaje de Programación II (CIBERTEC).

## Requisitos
- JDK 21
- Maven 3.9+
- MySQL 8 (usuario `root` / clave `root` por defecto en `persistence.xml`)

## Base de datos
```sql
CREATE DATABASE IF NOT EXISTS bd_biblioteca CHARACTER SET utf8mb4;
```
Hibernate actualiza el esquema al iniciar (`update`). También puede ejecutarse `src/main/resources/script_bd_biblioteca.sql`.

## Ejecución
```bash
mvn compile
mvn exec:java
```

## Funcionalidades
- 3 mantenimientos CRUD: Categoría, Libro, Lector
- 2 consultas: por título/ISBN y por categoría
- Caso transaccional: préstamo (inserta `tb_prestamo` y marca el libro no disponible) y devolución (cierra el préstamo y libera el libro)
