package controller;

import config.PengadaanDAO; // Mengambil dari package config
import model.Pengadaan;
import view.PengadaanView;

public class PengadaanController implements Kelola {

    private final PengadaanDAO dao;
    private final PengadaanView view;

    public PengadaanController(PengadaanView view, PengadaanDAO dao) {
        this.view = view;
        this.dao = dao;
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
                case 5 -> kembali = true;
                default -> view.pesan("Pilihan tidak valid!");
            }
        }
    }

    @Override
    public void tambah() {
        view.judul("TAMBAH DATA PENGADAAN");

        int id = view.inputIdBaru();
        if (id == 0) {
            view.pesan("Penambahan pengadaan dibatalkan!");
            return;
        }

        try {
            if (dao.cariById(id) != null) {
                view.pesan("ID pengadaan sudah digunakan!");
                return;
            }

            String tanggal = view.inputTanggal();
            String alamat = view.inputAlamat();

            dao.tambah(new Pengadaan(id, tanggal, alamat));
            view.pesan("Pengadaan baru berhasil ditambahkan!");
        } catch (IllegalArgumentException e) {
            view.pesan(e.getMessage());
        } catch (Exception e) { // Mengganti DatabaseException dengan Exception umum
            view.pesan("Kesalahan database: " + e.getMessage());
        }
    }

    @Override
    public void tampilkan() {
        try {
            view.tampilDaftar(dao.semua());
        } catch (Exception e) {
            view.pesan("Kesalahan database: " + e.getMessage());
        }
    }

    @Override
    public void hapus() {
        try {
            if (dao.hapus(view.inputId())) {
                view.pesan("Pengadaan berhasil dihapus!");
            } else {
                view.pesan("ID pengadaan tidak ditemukan!");
            }
        } catch (Exception e) {
            view.pesan("Kesalahan database: " + e.getMessage());
        }
    }

    @Override
    public void update() {
        try {
            Pengadaan pengadaan = dao.cariById(view.inputId());

            if (pengadaan == null) {
                view.pesan("ID pengadaan tidak ditemukan!");
                return;
            }

            pengadaan.setTanggal(view.inputTanggalBaru());
            pengadaan.setAlamat(view.inputAlamatBaru());

            dao.update(pengadaan);
            view.pesan("Data pengadaan berhasil diperbarui!");
        } catch (IllegalArgumentException e) {
            view.pesan(e.getMessage());
        } catch (Exception e) {
            view.pesan("Kesalahan database: " + e.getMessage());
        }
    }
}