package biblioteca.dao;

import biblioteca.config.JPAUtil;
import biblioteca.model.Libro;
import biblioteca.model.Lector;
import biblioteca.model.Prestamo;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

public class PrestamoDAO {

    public Prestamo registrarPrestamo(Long libroId, Long lectorId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();

            Libro libro = em.find(Libro.class, libroId);
            Lector lector = em.find(Lector.class, lectorId);

            if (libro == null) {
                throw new IllegalArgumentException("No existe el libro con ID " + libroId);
            }
            if (lector == null) {
                throw new IllegalArgumentException("No existe el lector con ID " + lectorId);
            }
            if (!Boolean.TRUE.equals(lector.getActivo())) {
                throw new IllegalStateException("El lector no está activo.");
            }
            if (!Boolean.TRUE.equals(libro.getDisponible())) {
                throw new IllegalStateException("El libro no está disponible.");
            }

            libro.setDisponible(false);
            em.merge(libro);

            Prestamo prestamo = new Prestamo(libro, lector);
            em.persist(prestamo);

            em.getTransaction().commit();
            return prestamo;
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public Prestamo registrarDevolucion(Long prestamoId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();

            Prestamo prestamo = em.find(Prestamo.class, prestamoId);
            if (prestamo == null) {
                throw new IllegalArgumentException("No existe el préstamo con ID " + prestamoId);
            }
            if (!"ACTIVO".equals(prestamo.getEstado())) {
                throw new IllegalStateException("El préstamo ya fue cerrado.");
            }

            prestamo.setEstado("DEVUELTO");
            prestamo.setFechaDevolucion(LocalDate.now());
            em.merge(prestamo);

            Libro libro = prestamo.getLibro();
            libro.setDisponible(true);
            em.merge(libro);

            em.getTransaction().commit();
            return prestamo;
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public List<Prestamo> listarTodos() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery(
                    "SELECT p FROM Prestamo p JOIN FETCH p.libro JOIN FETCH p.lector ORDER BY p.id DESC",
                    Prestamo.class).getResultList();
        } finally {
            em.close();
        }
    }

    public List<Prestamo> listarActivos() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery(
                    "SELECT p FROM Prestamo p JOIN FETCH p.libro JOIN FETCH p.lector "
                            + "WHERE p.estado = 'ACTIVO' ORDER BY p.fechaPrestamo",
                    Prestamo.class).getResultList();
        } finally {
            em.close();
        }
    }
}
