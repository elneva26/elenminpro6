package model.manager;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import model.Barang;
import java.util.List;

public class BarangManager {

    public void tambah(Barang barang) {
        EntityManager em = EntityManagerProvider.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(barang);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public Barang cariById(int id) {
        EntityManager em = EntityManagerProvider.getEntityManager();
        try {
            return em.find(Barang.class, id);
        } finally {
            em.close();
        }
    }

    public List<Barang> semua() {
        EntityManager em = EntityManagerProvider.getEntityManager();
        try {
            TypedQuery<Barang> query = em.createQuery("SELECT b FROM Barang b", Barang.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public boolean hapus(int id) {
        EntityManager em = EntityManagerProvider.getEntityManager();
        try {
            em.getTransaction().begin();
            Barang b = em.find(Barang.class, id);
            if (b != null) {
                em.remove(b);
                em.getTransaction().commit();
                return true;
            }
            em.getTransaction().commit();
            return false;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public void updateStok(Barang barang) {
        EntityManager em = EntityManagerProvider.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(barang);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public List<Barang> cariByNama(String keyword) {
        EntityManager em = EntityManagerProvider.getEntityManager();
        try {
            TypedQuery<Barang> query = em.createQuery("SELECT b FROM Barang b WHERE b.nama LIKE :keyword", Barang.class);
            query.setParameter("keyword", "%" + keyword + "%");
            return query.getResultList();
        } finally {
            em.close();
        }
    }
}