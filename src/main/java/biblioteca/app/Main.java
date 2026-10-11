package biblioteca.app;

import biblioteca.config.JPAUtil;
import biblioteca.model.Categoria;
import biblioteca.model.Lector;
import biblioteca.model.Libro;
import biblioteca.model.Prestamo;
import biblioteca.service.BibliotecaService;

import java.util.List;
import java.util.Scanner;

public class Main {

    private static final BibliotecaService service = new BibliotecaService();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("==============================================");
        System.out.println("  GESTOR DE BIBLIOTECA  |  JPA + Hibernate    ");
        System.out.println("==============================================");
        boolean salir = false;
        while (!salir) {
            mostrarMenu();
            System.out.print("Opcion: ");
            switch (scanner.nextLine()) {
                case "1" -> registrarCategoria();
                case "2" -> listarCategorias();
                case "3" -> actualizarCategoria();
                case "4" -> eliminarCategoria();
                case "5" -> registrarLibro();
                case "6" -> listarTodosLosLibros();
                case "7" -> actualizarLibro();
                case "8" -> eliminarLibro();
                case "9" -> registrarLector();
                case "10" -> listarLectores();
                case "11" -> actualizarLector();
                case "12" -> eliminarLector();
                case "13" -> consultarPorTituloOIsbn();
                case "14" -> consultarPorCategoria();
                case "15" -> prestarLibro();
                case "16" -> devolverPrestamo();
                case "17" -> listarPrestamos();
                case "18" -> listarPrestamosActivos();
                case "0" -> salir = true;
                default -> System.out.println("Opcion invalida.");
            }
            System.out.println();
        }
        JPAUtil.shutdown();
        scanner.close();
        System.out.println("Aplicacion finalizada.");
    }

    private static void mostrarMenu() {
        System.out.println("----------------------------------------------");
        System.out.println("MANTENIMIENTO CATEGORIA");
        System.out.println(" 1. Registrar categoria     2. Listar categorias");
        System.out.println(" 3. Editar categoria        4. Eliminar categoria");
        System.out.println("MANTENIMIENTO LIBRO");
        System.out.println(" 5. Registrar libro         6. Listar libros");
        System.out.println(" 7. Editar libro            8. Eliminar libro");
        System.out.println("MANTENIMIENTO LECTOR");
        System.out.println(" 9. Registrar lector       10. Listar lectores");
        System.out.println("11. Editar lector          12. Eliminar lector");
        System.out.println("CONSULTAS");
        System.out.println("13. Buscar libro por titulo o ISBN");
        System.out.println("14. Listar libros por categoria");
        System.out.println("TRANSACCION PRESTAMO");
        System.out.println("15. Registrar prestamo     16. Registrar devolucion");
        System.out.println("17. Listar prestamos       18. Listar prestamos activos");
        System.out.println(" 0. Salir");
        System.out.println("----------------------------------------------");
    }

    private static Long leerLong(String etiqueta) {
        System.out.print(etiqueta);
        return Long.parseLong(scanner.nextLine());
    }

    private static void registrarCategoria() {
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Descripcion: ");
        service.registrarCategoria(nombre, scanner.nextLine());
    }

    private static void listarCategorias() {
        List<Categoria> lista = service.listarCategorias();
        if (lista.isEmpty()) System.out.println("No hay categorias.");
        else lista.forEach(System.out::println);
    }

    private static void actualizarCategoria() {
        Long id = leerLong("ID categoria: ");
        System.out.print("Nuevo nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Nueva descripcion: ");
        service.actualizarCategoria(id, nombre, scanner.nextLine());
    }

    private static void eliminarCategoria() {
        service.eliminarCategoria(leerLong("ID categoria a eliminar: "));
    }

    private static void registrarLibro() {
        if (service.listarCategorias().isEmpty()) {
            System.out.println("Debe existir al menos una categoria.");
            return;
        }
        listarCategorias();
        System.out.print("Titulo: ");
        String titulo = scanner.nextLine();
        System.out.print("ISBN: ");
        String isbn = scanner.nextLine();
        System.out.print("Precio: ");
        double precio = Double.parseDouble(scanner.nextLine());
        service.registrarLibro(titulo, isbn, precio, leerLong("ID categoria: "));
    }

    private static void listarTodosLosLibros() {
        List<Libro> libros = service.listarTodosLosLibros();
        if (libros.isEmpty()) System.out.println("No hay libros.");
        else libros.forEach(System.out::println);
    }

    private static void actualizarLibro() {
        Long id = leerLong("ID libro: ");
        System.out.print("Nuevo titulo: ");
        String titulo = scanner.nextLine();
        System.out.print("Nuevo ISBN: ");
        String isbn = scanner.nextLine();
        System.out.print("Nuevo precio: ");
        Double precio = Double.parseDouble(scanner.nextLine());
        service.actualizarLibro(id, titulo, isbn, precio, leerLong("ID categoria: "));
    }

    private static void eliminarLibro() {
        service.eliminarLibro(leerLong("ID libro a eliminar: "));
    }

    private static void registrarLector() {
        System.out.print("Nombres: ");
        String nombres = scanner.nextLine();
        System.out.print("DNI: ");
        String dni = scanner.nextLine();
        System.out.print("Correo: ");
        service.registrarLector(nombres, dni, scanner.nextLine());
    }

    private static void listarLectores() {
        List<Lector> lista = service.listarLectores();
        if (lista.isEmpty()) System.out.println("No hay lectores.");
        else lista.forEach(System.out::println);
    }

    private static void actualizarLector() {
        Long id = leerLong("ID lector: ");
        System.out.print("Nombres: ");
        String nombres = scanner.nextLine();
        System.out.print("DNI: ");
        String dni = scanner.nextLine();
        System.out.print("Correo: ");
        String correo = scanner.nextLine();
        System.out.print("Activo? (s/n): ");
        boolean activo = scanner.nextLine().equalsIgnoreCase("s");
        service.actualizarLector(id, nombres, dni, correo, activo);
    }

    private static void eliminarLector() {
        service.eliminarLector(leerLong("ID lector a eliminar: "));
    }

    private static void consultarPorTituloOIsbn() {
        System.out.print("Criterio (titulo o ISBN): ");
        List<Libro> resultado = service.consultarPorTituloOIsbn(scanner.nextLine());
        if (resultado.isEmpty()) System.out.println("Sin resultados.");
        else resultado.forEach(System.out::println);
    }

    private static void consultarPorCategoria() {
        listarCategorias();
        List<Libro> resultado = service.consultarPorCategoria(leerLong("ID categoria: "));
        if (resultado.isEmpty()) System.out.println("Sin resultados.");
        else resultado.forEach(System.out::println);
    }

    private static void prestarLibro() {
        listarTodosLosLibros();
        listarLectores();
        service.procesarPrestamo(leerLong("ID libro: "), leerLong("ID lector: "));
    }

    private static void devolverPrestamo() {
        listarPrestamosActivos();
        service.procesarDevolucion(leerLong("ID prestamo a devolver: "));
    }

    private static void listarPrestamos() {
        List<Prestamo> lista = service.listarPrestamos();
        if (lista.isEmpty()) System.out.println("No hay prestamos.");
        else lista.forEach(System.out::println);
    }

    private static void listarPrestamosActivos() {
        List<Prestamo> lista = service.listarPrestamosActivos();
        if (lista.isEmpty()) System.out.println("No hay prestamos activos.");
        else lista.forEach(System.out::println);
    }
}
