package controller;

import config.PemasokDAO; // Mengambil dari package config
import model.Pemasok;
import view.PemasokView;

public class PemasokController implements Kelola {

    private final PemasokDAO dao;
    private final PemasokView view;

    public PemasokController(PemasokView view, PemasokDAO dao) {
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
        view.judul("TAMBAH DATA PEMASOK");

        int id = view.inputIdBaru();
        if (id == 0) {
            view.pesan("Penambahan pemasok dibatalkan!");
            return;
        }

        try {
            if (dao.cariById(id) != null) {
                view.pesan("ID pemasok sudah digunakan!");
                return;
            }

            String nama = view.inputNama();
            String telepon = view.inputNoTelepon(); // Disesuaikan dengan view
            String alamat = view.inputAlamat();

            dao.tambah(new Pemasok(id, nama, telepon, alamat));
            view.pesan("Pemasok baru berhasil ditambahkan!");
        } catch (IllegalArgumentException e) {
            view.pesan(e.getMessage());
        } catch (Exception e) { // Menggunakan Exception umum menggantikan DatabaseException
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
                view.pesan("Pemasok berhasil dihapus!");
            } else {
                view.pesan("ID pemasok tidak ditemukan!");
            }
        } catch (Exception e) {
            view.pesan("Kesalahan database: " + e.getMessage());
        }
    }

    @Override
    public void update() {
        try {
            Pemasok pemasok = dao.cariById(view.inputId());

            if (pemasok == null) {
                view.pesan("ID pemasok tidak ditemukan!");
                return;
            }

            pemasok.setNama(view.inputNamaBaru());
            pemasok.setNoTelepon(view.inputNoTeleponBaru()); // Menggunakan inputTelepon() sesuai view
            pemasok.setAlamat(view.inputAlamatBaru());

            dao.update(pemasok);
            view.pesan("Data pemasok berhasil diperbarui!");
        } catch (IllegalArgumentException e) {
            view.pesan(e.getMessage());
        } catch (Exception e) {
            view.pesan("Kesalahan database: " + e.getMessage());
        }
    }
}