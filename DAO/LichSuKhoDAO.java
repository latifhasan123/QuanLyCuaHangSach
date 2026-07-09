package DAO;

import DTO.LichSuKhoDTO;
import Utils.JDBCConnection;
import java.sql.*;
import java.util.ArrayList;

public class LichSuKhoDAO {
    
    public ArrayList<LichSuKhoDTO> selectAll() {
        ArrayList<LichSuKhoDTO> list = new ArrayList<>();
        // ĐÃ SỬA: Lấy MaID, MaLSK và JOIN bảng Sach để lấy Tên sách
        String sql = "SELECT ls.MaID, ls.MaLSK, ls.MaSach AS MaSachID, s.MaSach AS MaSach_Chu, s.TenSach, " +
                     "ls.LoaiGiaoDich, ls.MaChungTu, ls.SoLuongThayDoi, ls.NgayGioTao, ls.GhiChu " +
                     "FROM LichSuKho ls JOIN Sach s ON ls.MaSach = s.MaID " +
                     "ORDER BY ls.NgayGioTao DESC";

        try (Connection conn = JDBCConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                LichSuKhoDTO ls = new LichSuKhoDTO();
                ls.setMaID(rs.getInt("MaID"));
                ls.setMaLSK(rs.getString("MaLSK"));
                ls.setMaSachID(rs.getInt("MaSachID"));
                ls.setMaSach_Chu(rs.getString("MaSach_Chu"));
                ls.setTenSach(rs.getString("TenSach"));
                ls.setLoaiGiaoDich(rs.getString("LoaiGiaoDich"));
                ls.setMaChungTu(rs.getInt("MaChungTu"));
                ls.setSoLuongThayDoi(rs.getInt("SoLuongThayDoi"));
                if (rs.getTimestamp("NgayGioTao") != null) {
                    ls.setNgayGioTao(rs.getTimestamp("NgayGioTao").toLocalDateTime());
                }
                ls.setGhiChu(rs.getString("GhiChu"));
                list.add(ls);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insert(LichSuKhoDTO ls) {
        String sql = "INSERT INTO LichSuKho (MaSach, LoaiGiaoDich, MaChungTu, SoLuongThayDoi, GhiChu) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = JDBCConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, ls.getMaSachID());
            ps.setString(2, ls.getLoaiGiaoDich());
            ps.setInt(3, ls.getMaChungTu());
            ps.setInt(4, ls.getSoLuongThayDoi()); 
            ps.setString(5, ls.getGhiChu());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}