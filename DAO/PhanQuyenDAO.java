package DAO;

import DTO.PhanQuyenDTO;
import Utils.JDBCConnection;
import java.sql.*;
import java.util.ArrayList;

public class PhanQuyenDAO {
    
    public ArrayList<PhanQuyenDTO> selectAll() {
        ArrayList<PhanQuyenDTO> ds = new ArrayList<>();
        String sql = "SELECT * FROM PhanQuyen";
        try (Connection con = JDBCConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            
            while (rs.next()) {
                PhanQuyenDTO pq = new PhanQuyenDTO();
                pq.setMaID(rs.getInt("MaID"));
                pq.setMaPQ(rs.getString("MaPQ"));
                pq.setTenQuyen(rs.getString("TenQuyen"));
                pq.setMoTa(rs.getString("MoTa"));
                
                pq.setQlSach(rs.getInt("QlSach"));
                pq.setQlThuocTinh(rs.getInt("QlThuocTinh"));
                pq.setQlBanHang(rs.getInt("QlBanHang"));
                pq.setQlKhuyenMai(rs.getInt("QlKhuyenMai"));
                pq.setQlHoaDon(rs.getInt("QlHoaDon"));
                pq.setQlPhieuDoiTra(rs.getInt("QlPhieuDoiTra"));
                pq.setQlNhapHang(rs.getInt("QlNhapHang"));
                pq.setQlPhieuNhap(rs.getInt("QlPhieuNhap"));
                pq.setQlKhachHang(rs.getInt("QlKhachHang"));
                pq.setQlNCC(rs.getInt("QlNCC"));
                pq.setQlNhanVien(rs.getInt("QlNhanVien"));
                pq.setQlTaiKhoan(rs.getInt("QlTaiKhoan"));
                pq.setQlPhanQuyen(rs.getInt("QlPhanQuyen"));
                pq.setQlThongKe(rs.getInt("QlThongKe"));
                
                ds.add(pq);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ds;
    }

    public boolean updateQuyen(PhanQuyenDTO pq) {
        String sql = "UPDATE PhanQuyen SET QlSach=?, QlThuocTinh=?, QlBanHang=?, QlKhuyenMai=?, QlHoaDon=?, QlPhieuDoiTra=?, QlNhapHang=?, QlPhieuNhap=?, QlKhachHang=?, QlNCC=?, QlNhanVien=?, QlTaiKhoan=?, QlPhanQuyen=?, QlThongKe=? WHERE MaID=?";
        try (Connection con = JDBCConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            
            pst.setInt(1, pq.getQlSach()); 
            pst.setInt(2, pq.getQlThuocTinh()); 
            pst.setInt(3, pq.getQlBanHang());
            pst.setInt(4, pq.getQlKhuyenMai()); 
            
            // ĐÃ FIX LỖI CHÍNH TẢ Ở ĐÂY: getQlHoaDon() thay vì getHoaDon()
            pst.setInt(5, pq.getQlHoaDon()); 
            
            pst.setInt(6, pq.getQlPhieuDoiTra());
            pst.setInt(7, pq.getQlNhapHang()); 
            pst.setInt(8, pq.getQlPhieuNhap()); 
            pst.setInt(9, pq.getQlKhachHang());
            pst.setInt(10, pq.getQlNCC()); 
            pst.setInt(11, pq.getQlNhanVien()); 
            pst.setInt(12, pq.getQlTaiKhoan());
            pst.setInt(13, pq.getQlPhanQuyen()); 
            pst.setInt(14, pq.getQlThongKe()); 
            pst.setInt(15, pq.getMaID());
            
            return pst.executeUpdate() > 0;
        } catch (SQLException e) { 
            return false; 
        }
    }
}