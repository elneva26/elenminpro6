package config;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Pemasok;

public class PemasokDAO {

    public void tambah(Pemasok pemasok) {
        String query = "INSERT INTO pemasok (id_pemasok, nama, alamat, no_telepon) VALUES (?, ?, ?, ?)";
        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, pemasok.getIdPemasok());
            ps.setString(2, pemasok.getNama());
            ps.setString(3, pemasok.getAlamat());
            ps.setString(4, pemasok.getNoTelepon());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Gagal menambah pemasok via JDBC: " + e.getMessage());
        }
    }

    public List<Pemasok> semua() {
        List<Pemasok> list = new ArrayList<>();
        String query = "SELECT * FROM pemasok";
        try (Connection conn = Koneksi.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                list.add(new Pemasok(
                    rs.getInt("id_pemasok"),
                    rs.getString("nama"),
                    rs.getString("alamat"),
                    rs.getString("no_telepon")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Gagal mengambil data pemasok via JDBC: " + e.getMessage());
        }
        return list;
    }

    public Pemasok cariById(int idPemasok) {
        String query = "SELECT * FROM pemasok WHERE id_pemasok = ?";
        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, idPemasok);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Pemasok(
                        rs.getInt("id_pemasok"),
                        rs.getString("nama"),
                        rs.getString("alamat"),
                        rs.getString("no_telepon")
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Gagal mencari pemasok via JDBC: " + e.getMessage());
        }
        return null;
    }

    public void update(Pemasok pemasok) {
        String query = "UPDATE pemasok SET nama = ?, alamat = ?, no_telepon = ? WHERE id_pemasok = ?";
        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, pemasok.getNama());
            ps.setString(2, pemasok.getAlamat());
            ps.setString(3, pemasok.getNoTelepon());
            ps.setInt(4, pemasok.getIdPemasok());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Gagal update pemasok via JDBC: " + e.getMessage());
        }
    }

    public boolean hapus(int idPemasok) {
        String query = "DELETE FROM pemasok WHERE id_pemasok = ?";
        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, idPemasok);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Gagal menghapus pemasok via JDBC: " + e.getMessage());
        }
    }
}