package DAO;

import DTO.ThongKeDTO;
import Utils.JDBCConnection; 
import java.sql.*;
import java.util.ArrayList;
import DTO.TopSachDTO;

public class ThongKeDAO {

    public ArrayList<ThongKeDTO> getDoanhThuTheoNgay(String tuNgay, String denNgay) {
        ArrayList<ThongKeDTO> dsThongKe = new ArrayList<>();
        
        // ĐÃ SỬA: JOIN bảng HoaDon và DonHang, dùng đúng tên cột NgayThanhToan và ThanhTien
        String sql = "SELECT "
                   + "    CAST(hd.NgayThanhToan AS DATE) AS ThoiGian, "
                   + "    SUM(dh.ThanhTien) AS DoanhThu, "
                   + "    COUNT(hd.MaHD) AS SoDonHang "
                   + "FROM HoaDon hd "
                   + "JOIN DonHang dh ON hd.MaDH = dh.MaID "
                   + "WHERE CAST(hd.NgayThanhToan AS DATE) >= ? AND CAST(hd.NgayThanhToan AS DATE) <= ? "
                   + "GROUP BY CAST(hd.NgayThanhToan AS DATE) "
                   + "ORDER BY CAST(hd.NgayThanhToan AS DATE) ASC";

        try (Connection con = JDBCConnection.getConnection(); 
             PreparedStatement pst = con.prepareStatement(sql)) {
            
            pst.setString(1, tuNgay);
            pst.setString(2, denNgay);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                String thoiGian = rs.getString("ThoiGian");
                double doanhThu = rs.getDouble("DoanhThu");
                int soDon = rs.getInt("SoDonHang");
                int soSach = 0; 
                
                ThongKeDTO tk = new ThongKeDTO(thoiGian, doanhThu, soDon, soSach);
                dsThongKe.add(tk);
            }
        } catch (SQLException ex) {
            System.out.println("Lỗi gom dữ liệu thống kê DAO: " + ex.getMessage());
        }
        
        return dsThongKe;
    }
    // Nhớ import DTO.TopSachDTO; ở đầu file nhé Sếp!
    
    public ArrayList<TopSachDTO> getTopSachBanChay(String tuNgay, String denNgay) {
        ArrayList<TopSachDTO> dsTopSach = new ArrayList<>();
        // Câu lệnh SQL "Săn" Top 10 cuốn sách kiếm nhiều tiền nhất
        String sql = "SELECT TOP 10 " 
                   + "    s.TenSach, "
                   + "    SUM(ct.SoLuong) AS SoLuongBan, "
                   + "    SUM(ct.ThanhTien) AS DoanhThu "
                   + "FROM HoaDon hd "
                   + "JOIN DonHang dh ON hd.MaDH = dh.MaID "
                   + "JOIN ChiTietDonHang ct ON dh.MaID = ct.MaDH "
                   + "JOIN Sach s ON ct.MaSach = s.MaID "
                   + "WHERE CAST(hd.NgayThanhToan AS DATE) >= ? AND CAST(hd.NgayThanhToan AS DATE) <= ? "
                   + "GROUP BY s.MaID, s.TenSach "
                   + "ORDER BY SoLuongBan DESC";

        try (Connection con = JDBCConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, tuNgay);
            pst.setString(2, denNgay);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                String tenSach = rs.getString("TenSach");
                int soLuong = rs.getInt("SoLuongBan");
                double doanhThu = rs.getDouble("DoanhThu");

                dsTopSach.add(new TopSachDTO(tenSach, soLuong, doanhThu));
            }
        } catch (SQLException ex) {
            System.out.println("Lỗi gom dữ liệu Top Sách DAO: " + ex.getMessage());
        }
        return dsTopSach;
    }
}