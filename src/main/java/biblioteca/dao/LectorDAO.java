package biblioteca.dao;

import biblioteca.config.JPAUtil;
import biblioteca.model.Lector;
import jakarta.persistence.EntityManager;
import java.util.List;

public class LectorDAO {

    public void guardar(Lector lector) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(lector);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public void actualizar(Lector lector) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(lector);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public void eliminar(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Lector lector = em.find(Lector.class, id);
            if (lector != null) {
                em.remove(lector);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public Lector buscarPorId(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(Lector.class, id);
        } finally {
            em.close();
        }
    }

    public List<Lector> listarTodos() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT l FROM Lector l ORDER BY l.nombres", Lector.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }
}
