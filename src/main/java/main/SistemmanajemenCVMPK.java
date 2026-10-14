package main;

import controller.MenuController;
import java.util.Scanner;
import model.manager.EntityManagerProvider;

public class SistemmanajemenCVMPK {

    public static void main(String[] args) {
        // Meredam log startup Hibernate agar konsol bersih
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(java.util.logging.Level.SEVERE);

        Scanner scanner = new Scanner(System.in);

        // Langsung jalankan menu utama sistem
        new MenuController(scanner).jalankan();

        // Tutup koneksi ORM saat program berhenti
        EntityManagerProvider.tutup();
        scanner.close();
    }
}