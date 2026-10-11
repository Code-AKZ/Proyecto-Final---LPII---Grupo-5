package biblioteca.dao;

import biblioteca.config.JPAUtil;
import biblioteca.model.Libro;
import jakarta.persistence.EntityManager;
import java.util.List;

public class LibroDAO {

    public void guardar(Libro libro) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(libro);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public void actualizar(Libro libro) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(libro);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public Libro buscarPorId(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery(
                    "SELECT l FROM Libro l JOIN FETCH l.categoria WHERE l.id = :id", Libro.class)
                    .setParameter("id", id)
                    .getResultStream()
                    .findFirst()
                    .orElse(null);
        } finally {
            em.close();
        }
    }

    public List<Libro> listarTodos() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT l FROM Libro l JOIN FETCH l.categoria ORDER BY l.titulo", Libro.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<Libro> listarDisponibles() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery(
                    "SELECT l FROM Libro l JOIN FETCH l.categoria WHERE l.disponible = true ORDER BY l.titulo",
                    Libro.class).getResultList();
        } finally {
            em.close();
        }
    }

    public List<Libro> buscarPorTituloOIsbn(String criterio) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery(
                    "SELECT l FROM Libro l JOIN FETCH l.categoria "
                            + "WHERE LOWER(l.titulo) LIKE LOWER(:c) OR LOWER(l.isbn) LIKE LOWER(:c) "
                            + "ORDER BY l.titulo",
                    Libro.class)
                    .setParameter("c", "%" + criterio + "%")
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<Libro> listarPorCategoria(Long categoriaId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery(
                    "SELECT l FROM Libro l JOIN FETCH l.categoria WHERE l.categoria.id = :cid ORDER BY l.titulo",
                    Libro.class)
                    .setParameter("cid", categoriaId)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public void eliminar(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Libro libro = em.find(Libro.class, id);
            if (libro != null) em.remove(libro);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }
}
