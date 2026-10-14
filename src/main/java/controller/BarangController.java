package controller;

import java.util.List;
import model.Barang;
import model.BarangElektronik;
import model.BarangNonElektronik;
import model.manager.BarangManager; // Diperbaiki: arahkan ke sub-package model.manager
import view.BarangView;

public class BarangController implements Kelola {

    private final BarangManager barangmanager; 
    private final BarangView view;

    // Konstruktor menerima BarangManager
    public BarangController(BarangView view, BarangManager barangmanager) {
        this.view = view;
        this.barangmanager = barangmanager;
    }

    @Override
    public void jalankanMenu() {
        boolean kembali = false;
        while (!kembali) {
            view.tampilMenu();
            switch (view.pilihMenu()) {
                case 1 -> tambah();
                case 2 -> tampilkan();
                case 3 -> hapus();
                case 4 -> update();
                case 5 -> cari();
                case 6 -> kembali = true;
                default -> view.pesan("Pilihan tidak valid!");
            }
        }
    }

    @Override
    public void tambah() {
        view.judul("TAMBAH DATA BARANG");
        int id = view.inputIdBaru();
        if (id == 0) {
            view.pesan("Penambahan barang dibatalkan");
            return;
        }

        try {
            if (barangmanager.cariById(id) != null) {
                view.pesan("ID barang sudah digunakan!");
                return;
            }

            String nama = view.inputNama();
            int stok = view.inputStok("Stok Barang");
            int jenis = view.inputJenis();

            Barang barangBaru;
            if (jenis == 1) {
                barangBaru = new BarangElektronik(id, nama, stok, view.inputGaransi());
            } else {
                barangBaru = new BarangNonElektronik(id, nama, stok, view.inputKategori());
            }

            // Diubah dari barangDAO menjadi barangmanager
            barangmanager.tambah(barangBaru);
            view.pesan("Barang baru berhasil ditambahkan!");
        } catch (Exception e) {
            view.pesan("Gagal menambah data: " + e.getMessage());
        }
    }

    @Override
    public void tampilkan() {
        try {
            List<Barang> daftarBarang = barangmanager.semua();
            view.tampilDaftar(daftarBarang);
        } catch (Exception e) {
            view.pesan("Gagal mengambil data: " + e.getMessage());
        }
    }

    @Override
    public void hapus() {
        try {
            int id = view.inputId();
            if (barangmanager.hapus(id)) {
                view.pesan("Barang berhasil dihapus!");
            } else {
                view.pesan("ID barang tidak ditemukan!");
            }
        } catch (Exception e) {
            view.pesan("Gagal menghapus data: " + e.getMessage());
        }
    }

    @Override
    public void update() {
        try {
            int id = view.inputId();
            Barang barang = barangmanager.cariById(id);
            if (barang == null) {
                view.pesan("ID barang tidak ditemukan!");
                return;
            }

            int stokBaru = view.inputStok("Stok Baru");
            barang.setStok(stokBaru);
            barangmanager.updateStok(barang);
            view.pesan("Stok barang berhasil diperbarui!");
        } catch (Exception e) {
            view.pesan("Gagal memperbarui data: " + e.getMessage());
        }
    }

    private void cari() {
        view.judul("CARI DATA BARANG");
        try {
            if (view.inputCaraCari() == 1) {
                int id = view.inputId();
                Barang barang = barangmanager.cariById(id);
                if (barang == null) {
                    view.pesan("ID barang tidak ditemukan!");
                    return;
                }
                view.tampilDaftar(List.of(barang), "HASIL PENCARIAN");
            } else {
                String keyword = view.inputKeyword();
                List<Barang> hasil = barangmanager.cariByNama(keyword);
                if (hasil.isEmpty()) {
                    view.pesan("Barang tidak ditemukan!");
                    return;
                }
                view.tampilDaftar(hasil, "HASIL PENCARIAN");
            }
        } catch (Exception e) {
            view.pesan("Gagal mencari data: " + e.getMessage());
        }
    }
}