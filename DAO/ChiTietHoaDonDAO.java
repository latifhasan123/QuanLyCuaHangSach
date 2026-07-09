package dao;

import dto.ChiTietHoaDonDTO;
import Utils.JDBCConnection; // ĐÃ SỬA THÀNH JDBCConnection

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ChiTietHoaDonDAO {
    public List<ChiTietHoaDonDTO> getByMaHD(int maHD) {
        List<ChiTietHoaDonDTO> list = new ArrayList<>();
        String sql = "SELECT ct.MaCTDH, ct.MaDH, ct.MaSach, s.TenSach, ct.SoLuong, ct.DonGia, ct.ThanhTien " +
                "FROM ChiTietDonHang ct " +
                "JOIN Sach s ON ct.MaSach = s.MaID " +
                "WHERE ct.MaDH = ?";

        try (Connection conn = JDBCConnection.getConnection(); // ĐÃ SỬA
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maHD);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ChiTietHoaDonDTO dto = new ChiTietHoaDonDTO();
                    dto.setMaCTHD(rs.getInt("MaCTDH"));
                    dto.setMaHD(rs.getInt("MaDH"));
                    dto.setMaSach(rs.getInt("MaSach"));
                    dto.setTenSach(rs.getString("TenSach"));
                    dto.setSoLuong(rs.getInt("SoLuong"));
                    dto.setDonGia(rs.getBigDecimal("DonGia"));
                    dto.setThanhTien(rs.getBigDecimal("ThanhTien"));
                    list.add(dto);
                }
            }

        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public boolean insert(ChiTietHoaDonDTO chiTiet) {
        String sql = "INSERT INTO ChiTietDonHang (MaDH, MaSach, SoLuong, DonGia) VALUES (?, ?, ?, ?)";

        try (Connection conn = JDBCConnection.getConnection(); // ĐÃ SỬA
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, chiTiet.getMaHD());
            ps.setInt(2, chiTiet.getMaSach());
            ps.setInt(3, chiTiet.getSoLuong());
            ps.setBigDecimal(4, chiTiet.getDonGia());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }
}