package model.manager;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class EntityManagerProvider {
    private static final EntityManagerFactory emf;

    static {
        try {
            emf = Persistence.createEntityManagerFactory("cvmpkPU");
        } catch (Throwable ex) {
            System.err.println("Gagal membuat EntityManagerFactory: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    // Method untuk mengecek apakah EntityManagerFactory siap dan terbuka
    public static boolean siap() {
        try {
            return emf != null && emf.isOpen();
        } catch (Exception e) {
            return false;
        }
    }

    // Method untuk menutup EntityManagerFactory
    public static void tutup() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}