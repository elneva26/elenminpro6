package model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("NON_ELEKTRONIK")
public class BarangNonElektronik extends Barang {

    @Column(name = "kategori", length = 50)
    private String kategori;

    // Constructor kosong hanya dipakai ORM
    protected BarangNonElektronik() {
    }

    public BarangNonElektronik(int idBarang, String nama, int stok, String kategori) {
        super(idBarang, nama, stok);
        setKategori(kategori);
    }

    public String getKategori() {
        return kategori;
    }

    private void setKategori(String kategori) {
        if (kategori == null || kategori.trim().isEmpty()) {
            throw new IllegalArgumentException("Kategori tidak boleh kosong!");
        }
        this.kategori = kategori.trim();
    }

    // ---- Overriding abstract method dari Barang ----
    @Override
    public String getJenis() {
        return "BARANG NON-ELEKTRONIK";
    }

    @Override
    public String getLabelDetail() {
        return "Kategori";
    }

    @Override
    public String getNilaiDetail() {
        return getKategori();
    }
}
