package config;

import model.manager.EntityManagerProvider; // <-- Tambahkan import ini di atas

public class TestKoneksiOrm {

    public static void main(String[] args) {
        System.out.println("Mengecek koneksi ke database (ORM)...");

        if (EntityManagerProvider.siap()) {
            System.out.println("STATUS: BERHASIL! ORM siap digunakan.");
        } else {
            System.out.println("STATUS: GAGAL! Periksa persistence.xml dan pastikan service MySQL sudah menyala.");
        }

        EntityManagerProvider.tutup();
    }
}