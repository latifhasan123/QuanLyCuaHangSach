package DAO;

import Utils.JDBCConnection;
import java.sql.*;
import java.util.*;

public class TrangChuDAO {

    // 1. Lấy Tổng doanh thu hôm nay
    public double getDoanhThuHomNay() {
        String sql = "SELECT SUM(ThanhTien) FROM DonHang WHERE TrangThaiDon = 'HoanThanh' AND CAST(NgayTao AS DATE) = CAST(GETDATE() AS DATE)";
        return getDoubleValue(sql);
    }

    // 2. Lấy số lượng đơn hàng thành công hôm nay
    public int getSoDonThanhCongHomNay() {
        String sql = "SELECT COUNT(MaID) FROM DonHang WHERE TrangThaiDon = 'HoanThanh' AND CAST(NgayTao AS DATE) = CAST(GETDATE() AS DATE)";
        return getIntValue(sql);
    }

    // 3. Đếm số đơn hàng mới đang chờ xử lý
    public int getDonHangMoi() {
        String sql = "SELECT COUNT(MaID) FROM DonHang WHERE TrangThaiDon = 'ChoXuLy'";
        return getIntValue(sql);
    }

    // 4. Đếm số đầu sách sắp hết (Tồn kho <= 10)
    public int getSachSapHet() {
        String sql = "SELECT COUNT(MaID) FROM Sach WHERE SoLuongTon <= 10 AND TrangThai = 'DangBan'";
        return getIntValue(sql);
    }

    // 5. Tính lợi nhuận gộp hôm nay (Doanh thu - Giá vốn)
    public double getLoiNhuanHomNay() {
        String sql = "SELECT SUM(ct.SoLuong * (ct.DonGia - s.GiaNhap)) " +
                     "FROM ChiTietDonHang ct " +
                     "JOIN DonHang dh ON ct.MaDH = dh.MaID " +
                     "JOIN Sach s ON ct.MaSach = s.MaID " +
                     "WHERE dh.TrangThaiDon = 'HoanThanh' AND CAST(dh.NgayTao AS DATE) = CAST(GETDATE() AS DATE)";
        return getDoubleValue(sql);
    }

    // 6. Lấy Top 5 sách bán chạy nhất trong tháng
    public Map<String, Integer> getTop5Sach() {
        Map<String, Integer> map = new LinkedHashMap<>();
        String sql = "SELECT TOP 5 s.TenSach, SUM(ct.SoLuong) AS TongBan " +
                     "FROM ChiTietDonHang ct " +
                     "JOIN DonHang dh ON ct.MaDH = dh.MaID " +
                     "JOIN Sach s ON ct.MaSach = s.MaID " +
                     "WHERE dh.TrangThaiDon = 'HoanThanh' AND MONTH(dh.NgayTao) = MONTH(GETDATE()) AND YEAR(dh.NgayTao) = YEAR(GETDATE()) " +
                     "GROUP BY s.TenSach ORDER BY TongBan DESC";
        try (Connection conn = JDBCConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                map.put(rs.getString("TenSach"), rs.getInt("TongBan"));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return map;
    }
    public double[] getDoanhThuTheoTuanTrongThang() {
        double[] doanhThuTuan = new double[4]; // Mảng chứa doanh thu 4 tuần
        
        // Chia ngày trong tháng ra làm 4 phần (tuần 1, 2, 3, 4)
        String sql = "SELECT (DAY(NgayTao) - 1) / 7 AS Tuan, SUM(ThanhTien) AS TongTien " +
                     "FROM DonHang " +
                     "WHERE TrangThaiDon = 'HoanThanh' " +
                     "AND MONTH(NgayTao) = MONTH(GETDATE()) " +
                     "AND YEAR(NgayTao) = YEAR(GETDATE()) " +
                     "GROUP BY (DAY(NgayTao) - 1) / 7";
                     
        try (Connection conn = JDBCConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
             
            while (rs.next()) {
                int index = rs.getInt("Tuan");
                if (index > 3) index = 3; // Các ngày cuối tháng (29,30,31) gộp hết vào tuần 4
                doanhThuTuan[index] += rs.getDouble("TongTien");
            }
        } catch (Exception e) { e.printStackTrace(); }
        
        return doanhThuTuan;
    }

    // --- Các hàm hỗ trợ chạy SQL cho nhanh ---
    private double getDoubleValue(String sql) {
        try (Connection conn = JDBCConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) return rs.getDouble(1);
        } catch (Exception e) { e.printStackTrace(); }
        return 0;
    }

    private int getIntValue(String sql) {
        try (Connection conn = JDBCConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) return rs.getInt(1);
        } catch (Exception e) { e.printStackTrace(); }
        return 0;
    }
}