package config;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Pengadaan;

public class PengadaanDAO {

    public void tambah(Pengadaan pengadaan) {
        String query = "INSERT INTO pengadaan (id_pengadaan, tanggal, alamat) VALUES (?, ?, ?)";
        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, pengadaan.getIdPengadaan());
            ps.setDate(2, java.sql.Date.valueOf(
                java.time.LocalDate.parse(pengadaan.getTanggal(), Pengadaan.FORMAT_TANGGAL)));
            ps.setString(3, pengadaan.getAlamat());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Gagal menambah pengadaan via JDBC: " + e.getMessage());
        }
    }

    public List<Pengadaan> semua() {
        List<Pengadaan> list = new ArrayList<>();
        String query = "SELECT * FROM pengadaan ORDER BY id_pengadaan";
        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(petakan(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Gagal mengambil data pengadaan via JDBC: " + e.getMessage(), e);
        }
        return list;
    }

    public Pengadaan cariById(int idPengadaan) {
        String query = "SELECT * FROM pengadaan WHERE id_pengadaan = ?";
        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, idPengadaan);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return petakan(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Gagal mencari pengadaan via JDBC: " + e.getMessage(), e);
        }
        return null;
    }

    private Pengadaan petakan(ResultSet rs) throws SQLException {
        String tanggal = rs.getDate("tanggal").toLocalDate().format(Pengadaan.FORMAT_TANGGAL);
        return new Pengadaan(rs.getInt("id_pengadaan"), tanggal, rs.getString("alamat"));
    }

    public void update(Pengadaan pengadaan) {
        String query = "UPDATE pengadaan SET tanggal = ?, alamat = ? WHERE id_pengadaan = ?";
        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setDate(1, java.sql.Date.valueOf(
                java.time.LocalDate.parse(pengadaan.getTanggal(), Pengadaan.FORMAT_TANGGAL)));
            ps.setString(2, pengadaan.getAlamat());
            ps.setInt(3, pengadaan.getIdPengadaan());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Gagal update pengadaan via JDBC: " + e.getMessage());
        }
    }

    public boolean hapus(int idPengadaan) {
        String query = "DELETE FROM pengadaan WHERE id_pengadaan = ?";
        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, idPengadaan);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Gagal menghapus pengadaan via JDBC: " + e.getMessage());
        }
    }
}