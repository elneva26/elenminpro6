package model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "pemasok")
public class Pemasok {

    @Id
    @Column(name = "id_pemasok")
    private int idPemasok;

    @Column(name = "nama", nullable = false, length = 100)
    private String nama;

    @Column(name = "alamat", nullable = false, length = 200)
    private String alamat;

    @Column(name = "no_telepon", nullable = false, length = 20)
    private String noTelepon;

    // Constructor kosong hanya dipakai ORM
    protected Pemasok() {
    }

    public Pemasok(int idPemasok, String nama, String alamat, String noTelepon) {
        if (idPemasok <= 0) {
            throw new IllegalArgumentException("ID pemasok tidak valid!");
        }
        this.idPemasok = idPemasok;
        setNama(nama);
        setAlamat(alamat);
        setNoTelepon(noTelepon);
    }

    public static boolean isNoTeleponValid(String noTelepon) {
        return noTelepon != null && noTelepon.trim().matches("\\d+");
    }

    public int getIdPemasok() {
        return idPemasok;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        if (nama == null || nama.trim().isEmpty()) {
            throw new IllegalArgumentException("Nama pemasok tidak boleh kosong!");
        }
        this.nama = nama.trim();
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        if (alamat == null || alamat.trim().isEmpty()) {
            throw new IllegalArgumentException("Alamat pemasok tidak boleh kosong!");
        }
        this.alamat = alamat.trim();
    }

    public String getNoTelepon() {
        return noTelepon;
    }

    public void setNoTelepon(String noTelepon) {
        if (noTelepon == null || noTelepon.trim().isEmpty()) {
            throw new IllegalArgumentException("No telepon tidak boleh kosong!");
        }
        if (!isNoTeleponValid(noTelepon)) {
            throw new IllegalArgumentException("No telepon harus berupa angka!");
        }
        this.noTelepon = noTelepon.trim();
    }
}
