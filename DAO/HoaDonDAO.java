package dao;

import dto.HoaDonDTO;
import enums.TrangThaiGiaoDich;
import Utils.JDBCConnection; 

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane; 

public class HoaDonDAO {

    public List<HoaDonDTO> getAll() {
        List<HoaDonDTO> list = new ArrayList<>();
        // 🔥 ĐÃ SỬA: Thêm ORDER BY dh.MaID DESC để đưa hóa đơn mới nhất lên trên cùng!
        String sql = "SELECT dh.*, nv.MaNV AS CodeNV, kh.MaKH AS CodeKH " +
                     "FROM DonHang dh " +
                     "LEFT JOIN NhanVien nv ON dh.MaNV = nv.MaID " +
                     "LEFT JOIN KhachHang kh ON dh.MaKH = kh.MaID " +
                     "ORDER BY dh.MaID DESC";

        try (Connection conn = Utils.JDBCConnection.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                HoaDonDTO hd = new HoaDonDTO();
                hd.setMaHD(rs.getInt("MaID")); 
                hd.setMaNV(rs.getInt("MaNV"));
                hd.setMaKH(rs.getInt("MaKH"));
                
                // LẤY MÃ CHỮ VỪA JOIN ĐƯỢC ĐỔ VÀO DTO
                hd.setStrMaNV(rs.getString("CodeNV"));
                hd.setStrMaKH(rs.getString("CodeKH"));

                hd.setTongTien(rs.getBigDecimal("TongTien"));
                hd.setTienGiam(rs.getBigDecimal("TienGiam"));
                hd.setThanhTien(rs.getBigDecimal("ThanhTien"));
                
                // Bọc try-catch chống lỗi Enum làm sập bảng
                try {
                    hd.setLoaiHoaDon(enums.LoaiHoaDon.valueOf(rs.getString("LoaiDonHang"))); 
                } catch (Exception e) {}
                
                try {
                    hd.setTrangThai(enums.TrangThaiGiaoDich.valueOf(rs.getString("TrangThaiDon"))); 
                } catch (Exception e) {}

                java.sql.Timestamp ts = rs.getTimestamp("NgayTao");
                if (ts != null) hd.setNgayTao(ts.toLocalDateTime());

                list.add(hd);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public int insert(HoaDonDTO hd) {
        String sql = "INSERT INTO DonHang (MaNV, MaKH, MaKM, TongTien, TienGiam, ThanhTien, LoaiDonHang, TrangThaiDon, NgayTao) VALUES (?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP)";

        try (Connection conn = JDBCConnection.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {

            if (hd.getMaNV() == null || hd.getMaNV() <= 0) ps.setNull(1, java.sql.Types.INTEGER);
            else ps.setInt(1, hd.getMaNV());

            if (hd.getMaKH() != null && hd.getMaKH() > 0) ps.setInt(2, hd.getMaKH()); 
            else ps.setNull(2, java.sql.Types.INTEGER);

            if (hd.getMaKM() != null && hd.getMaKM() > 0) ps.setInt(3, hd.getMaKM()); 
            else ps.setNull(3, java.sql.Types.INTEGER);

            ps.setBigDecimal(4, hd.getTongTien());
            ps.setBigDecimal(5, hd.getTienGiam());
            ps.setBigDecimal(6, hd.getThanhTien()); 
            if (hd.getLoaiHoaDon() != null) ps.setString(7, hd.getLoaiHoaDon().name()); else ps.setString(7, "TaiQuay");
            if (hd.getTrangThai() != null) ps.setString(8, hd.getTrangThai().name()); else ps.setString(8, "HoanThanh");

            if (ps.executeUpdate() > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, 
                "Chi tiết lỗi từ SQL Server báo về:\n" + e.getMessage(), 
                "Bắt Quả Tang Lỗi SQL", 
                JOptionPane.ERROR_MESSAGE);
        }
        return -1;
    }

    public boolean update(HoaDonDTO hd) {
        String sql = "UPDATE DonHang SET MaNV = ?, MaKH = ?, MaKM = ?, TongTien = ?, TienGiam = ?, ThanhTien = ?, LoaiDonHang = ?, TrangThaiDon = ? WHERE MaID = ?";

        try (Connection conn = JDBCConnection.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, hd.getMaNV());
            if (hd.getMaKH() == null || hd.getMaKH() <= 0) ps.setNull(2, java.sql.Types.INTEGER); else ps.setInt(2, hd.getMaKH());
            if (hd.getMaKM() == null || hd.getMaKM() <= 0) ps.setNull(3, java.sql.Types.INTEGER); else ps.setInt(3, hd.getMaKM());
            ps.setBigDecimal(4, hd.getTongTien());
            ps.setBigDecimal(5, hd.getTienGiam());
            ps.setBigDecimal(6, hd.getThanhTien());
            if (hd.getLoaiHoaDon() != null) ps.setString(7, hd.getLoaiHoaDon().name()); else ps.setString(7, "TaiQuay");
            if (hd.getTrangThai() != null) ps.setString(8, hd.getTrangThai().name()); else ps.setString(8, "HoanThanh");
            ps.setInt(9, hd.getMaHD()); 

            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public boolean delete(int maHD) {
        String sql = "UPDATE DonHang SET TrangThaiDon = ? WHERE MaID = ?";
        try (Connection conn = JDBCConnection.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, enums.TrangThaiGiaoDich.DaHuy.name());
            ps.setInt(2, maHD);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }
}