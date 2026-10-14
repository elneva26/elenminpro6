package model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;

/**
 * Entity ORM: satu tabel "barang" dipakai bersama oleh BarangElektronik dan
 * BarangNonElektronik. Kolom "jenis" menjadi pembeda (discriminator).
 */
@Entity
@Table(name = "barang")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "jenis", discriminatorType = DiscriminatorType.STRING)
public abstract class Barang {

    @Id
    @Column(name = "id_barang")
    private int idBarang;

    @Column(name = "nama", nullable = false, length = 100)
    private String nama;

    @Column(name = "stok", nullable = false)
    private int stok;

    // Constructor kosong hanya dipakai ORM untuk membuat objek dari baris tabel.
    protected Barang() {
    }

    protected Barang(int idBarang, String nama, int stok) {
        if (idBarang <= 0) {
            throw new IllegalArgumentException("ID barang tidak valid!");
        }
        this.idBarang = idBarang;
        setNama(nama);
        setStok(stok);
    }

    public int getIdBarang() {
        return idBarang;
    }

    public String getNama() {
        return nama;
    }

    public int getStok() {
        return stok;
    }

    private void setNama(String nama) {
        if (nama == null || nama.trim().isEmpty()) {
            throw new IllegalArgumentException("Nama barang tidak boleh kosong!");
        }
        this.nama = nama.trim();
    }

    public void setStok(int stok) {
        if (stok < 0) {
            throw new IllegalArgumentException("Stok tidak boleh kurang dari 0!");
        }
        this.stok = stok;
    }

    // ---- Abstract method: wajib diisi oleh setiap subclass ----
    public abstract String getJenis();

    public abstract String getLabelDetail();

    public abstract String getNilaiDetail();
}
