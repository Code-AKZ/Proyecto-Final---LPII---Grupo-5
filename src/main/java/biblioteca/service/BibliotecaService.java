package biblioteca.service;

import biblioteca.dao.CategoriaDAO;
import biblioteca.dao.LectorDAO;
import biblioteca.dao.LibroDAO;
import biblioteca.dao.PrestamoDAO;
import biblioteca.model.Categoria;
import biblioteca.model.Lector;
import biblioteca.model.Libro;
import biblioteca.model.Prestamo;

import java.util.List;

public class BibliotecaService {

    private final CategoriaDAO categoriaDAO = new CategoriaDAO();
    private final LibroDAO libroDAO = new LibroDAO();
    private final LectorDAO lectorDAO = new LectorDAO();
    private final PrestamoDAO prestamoDAO = new PrestamoDAO();

    public void registrarCategoria(String nombre, String descripcion) {
        if (nombre == null || nombre.isBlank()) {
            System.out.println("El nombre de la categoria es obligatorio.");
            return;
        }
        categoriaDAO.guardar(new Categoria(nombre.trim(), descripcion == null ? "" : descripcion.trim()));
        System.out.println("Categoria registrada.");
    }

    public void actualizarCategoria(Long id, String nombre, String descripcion) {
        Categoria c = categoriaDAO.buscarPorId(id);
        if (c == null) {
            System.out.println("No existe la categoria.");
            return;
        }
        if (nombre != null && !nombre.isBlank()) c.setNombre(nombre.trim());
        if (descripcion != null) c.setDescripcion(descripcion.trim());
        categoriaDAO.actualizar(c);
        System.out.println("Categoria actualizada.");
    }

    public void eliminarCategoria(Long id) {
        categoriaDAO.eliminar(id);
        System.out.println("Categoria eliminada.");
    }

    public List<Categoria> listarCategorias() {
        return categoriaDAO.listarTodas();
    }

    public void registrarLibro(String titulo, String isbn, Double precio, Long categoriaId) {
        if (titulo == null || titulo.isBlank() || isbn == null || isbn.isBlank()) {
            System.out.println("Titulo e ISBN son obligatorios.");
            return;
        }
        Categoria categoria = categoriaDAO.buscarPorId(categoriaId);
        if (categoria == null) {
            System.out.println("No existe la categoria " + categoriaId);
            return;
        }
        libroDAO.guardar(new Libro(titulo.trim(), isbn.trim(), precio, categoria));
        System.out.println("Libro registrado.");
    }

    public void actualizarLibro(Long id, String titulo, String isbn, Double precio, Long categoriaId) {
        Libro libro = libroDAO.buscarPorId(id);
        if (libro == null) {
            System.out.println("No existe el libro.");
            return;
        }
        if (titulo != null && !titulo.isBlank()) libro.setTitulo(titulo.trim());
        if (isbn != null && !isbn.isBlank()) libro.setIsbn(isbn.trim());
        if (precio != null) libro.setPrecio(precio);
        if (categoriaId != null) {
            Categoria categoria = categoriaDAO.buscarPorId(categoriaId);
            if (categoria == null) {
                System.out.println("No existe la categoria.");
                return;
            }
            libro.setCategoria(categoria);
        }
        libroDAO.actualizar(libro);
        System.out.println("Libro actualizado.");
    }

    public void eliminarLibro(Long id) {
        libroDAO.eliminar(id);
        System.out.println("Libro eliminado.");
    }

    public List<Libro> listarTodosLosLibros() { return libroDAO.listarTodos(); }
    public List<Libro> listarLibrosDisponibles() { return libroDAO.listarDisponibles(); }
    public List<Libro> consultarPorTituloOIsbn(String criterio) { return libroDAO.buscarPorTituloOIsbn(criterio); }
    public List<Libro> consultarPorCategoria(Long categoriaId) { return libroDAO.listarPorCategoria(categoriaId); }

    public void registrarLector(String nombres, String dni, String correo) {
        if (nombres == null || nombres.isBlank() || dni == null || dni.isBlank()) {
            System.out.println("Nombres y DNI son obligatorios.");
            return;
        }
        lectorDAO.guardar(new Lector(nombres.trim(), dni.trim(), correo == null ? "" : correo.trim()));
        System.out.println("Lector registrado.");
    }

    public void actualizarLector(Long id, String nombres, String dni, String correo, Boolean activo) {
        Lector lector = lectorDAO.buscarPorId(id);
        if (lector == null) {
            System.out.println("No existe el lector.");
            return;
        }
        if (nombres != null && !nombres.isBlank()) lector.setNombres(nombres.trim());
        if (dni != null && !dni.isBlank()) lector.setDni(dni.trim());
        if (correo != null) lector.setCorreo(correo.trim());
        if (activo != null) lector.setActivo(activo);
        lectorDAO.actualizar(lector);
        System.out.println("Lector actualizado.");
    }

    public void eliminarLector(Long id) {
        lectorDAO.eliminar(id);
        System.out.println("Lector eliminado.");
    }

    public List<Lector> listarLectores() { return lectorDAO.listarTodos(); }

    public void procesarPrestamo(Long libroId, Long lectorId) {
        try {
            Prestamo p = prestamoDAO.registrarPrestamo(libroId, lectorId);
            System.out.println("Prestamo registrado: " + p);
        } catch (Exception e) {
            System.out.println("No se pudo registrar el prestamo: " + e.getMessage());
        }
    }

    public void procesarDevolucion(Long prestamoId) {
        try {
            Prestamo p = prestamoDAO.registrarDevolucion(prestamoId);
            System.out.println("Devolucion registrada: " + p);
        } catch (Exception e) {
            System.out.println("No se pudo registrar la devolucion: " + e.getMessage());
        }
    }

    public List<Prestamo> listarPrestamos() { return prestamoDAO.listarTodos(); }
    public List<Prestamo> listarPrestamosActivos() { return prestamoDAO.listarActivos(); }
}
