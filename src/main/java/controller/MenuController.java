package controller;

import java.util.Scanner;
import model.manager.BarangManager;
import config.PemasokDAO;    // Menggunakan PemasokDAO dari config
import config.PengadaanDAO;  // Menggunakan PengadaanDAO dari config
import view.BarangView;
import view.MenuView;
import view.PemasokView;
import view.PengadaanView;

public class MenuController {

    private final MenuView view;
    private final Kelola kelolaBarang;
    private final Kelola kelolaPemasok;
    private final Kelola kelolaPengadaan;

    public MenuController(Scanner scanner) {
        view = new MenuView(scanner);
        
        // Inisialisasi menggunakan BarangManager (ORM) dan DAO JDBC dari config
        kelolaBarang = new BarangController(new BarangView(scanner), new BarangManager());
        kelolaPemasok = new PemasokController(new PemasokView(scanner), new PemasokDAO());
        kelolaPengadaan = new PengadaanController(new PengadaanView(scanner), new PengadaanDAO());
    }

    public void jalankan() {
        boolean berjalan = true;

        while (berjalan) {
            view.tampilMenuUtama();

            switch (view.pilihMenu()) {
                case 1 -> kelolaBarang.jalankanMenu();
                case 2 -> kelolaPemasok.jalankanMenu();
                case 3 -> kelolaPengadaan.jalankanMenu();
                case 4 -> {
                    berjalan = false;
                    view.tampilKeluar();
                }
                default -> view.pesan("Menu tidak tersedia");
            }
        }
    }
}