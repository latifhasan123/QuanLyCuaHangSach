package DAO;

import DTO.TaiKhoanKhachHangDTO;
import Utils.JDBCConnection;
import java.sql.*;
import java.util.ArrayList;

public class TaiKhoanKhachHangDAO {
    
    // Lấy toàn bộ danh sách tài khoản
    public ArrayList<TaiKhoanKhachHangDTO> selectAll() {
        ArrayList<TaiKhoanKhachHangDTO> ds = new ArrayList<>();
        String sql = "SELECT * FROM TaiKhoanKhachHang";
        try (Connection con = JDBCConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            
            while (rs.next()) {
                ds.add(new TaiKhoanKhachHangDTO(
                    rs.getInt("MaID"), 
                    rs.getString("MaTKKH"),
                    rs.getString("TenDangNhap"), 
                    rs.getString("MatKhau"),
                    rs.getString("TrangThai"), 
                    rs.getTimestamp("NgayTao")
                ));
            }
        } catch (SQLException e) { 
            System.out.println("Lỗi lấy danh sách TKKH: " + e.getMessage());
        }
        return ds;
    }

    // Khóa hoặc Mở khóa tài khoản
    public boolean updateStatus(int maID, String trangThai) {
        String sql = "UPDATE TaiKhoanKhachHang SET TrangThai = ? WHERE MaID = ?";
        try (Connection con = JDBCConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            
            pst.setString(1, trangThai);
            pst.setInt(2, maID);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) { 
            return false; 
        }
    }

    // Reset mật khẩu về mặc định
    public boolean resetPassword(int maID, String newPass) {
        String sql = "UPDATE TaiKhoanKhachHang SET MatKhau = ? WHERE MaID = ?";
        try (Connection con = JDBCConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            
            pst.setString(1, newPass);
            pst.setInt(2, maID);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) { 
            return false; 
        }
    }
}   