package DAO;

import DTO.TaiKhoanNhanVienDTO;
import Utils.JDBCConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class TaiKhoanNhanVienDAO {

    // Lấy danh sách kết hợp với bảng PhanQuyen để lấy Tên Quyền
    public ArrayList<TaiKhoanNhanVienDTO> selectAll() {
        ArrayList<TaiKhoanNhanVienDTO> ketQua = new ArrayList<>();
        String sql = "SELECT tk.*, pq.TenQuyen " +
                     "FROM TaiKhoanNhanVien tk " +
                     "JOIN PhanQuyen pq ON tk.MaQuyen = pq.MaID";
        
        try (Connection conn = JDBCConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            
            while (rs.next()) {
                TaiKhoanNhanVienDTO tk = new TaiKhoanNhanVienDTO();
                tk.setMaID(rs.getInt("MaID"));
                tk.setMaTKNV(rs.getString("MaTKNV"));
                tk.setMaQuyen(rs.getInt("MaQuyen"));
                tk.setTenDangNhap(rs.getString("TenDangNhap"));
                tk.setMatKhau(rs.getString("MatKhau"));
                tk.setTrangThai(rs.getString("TrangThai"));
                tk.setNgayTao(rs.getTimestamp("NgayTao"));
                
                // Lấy tên quyền từ lệnh JOIN
                tk.setTenQuyen(rs.getString("TenQuyen")); 
                
                ketQua.add(tk);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    public boolean insert(TaiKhoanNhanVienDTO tk) {
        // Không insert MaID, MaTKNV và NgayTao vì SQL Server tự động tạo
        String sql = "INSERT INTO TaiKhoanNhanVien (MaQuyen, TenDangNhap, MatKhau, TrangThai) VALUES (?, ?, ?, ?)";
        try (Connection conn = JDBCConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            
            pst.setInt(1, tk.getMaQuyen());
            pst.setString(2, tk.getTenDangNhap());
            pst.setString(3, tk.getMatKhau());
            pst.setString(4, tk.getTrangThai());
            
            return pst.executeUpdate() > 0;
        } catch (Exception e) {
            System.err.println("Lỗi Insert Tài Khoản: " + e.getMessage());
            return false;
        }
    }

    public boolean update(TaiKhoanNhanVienDTO tk) {
        // Thường người ta không cho đổi Tên Đăng Nhập, chỉ cho đổi Quyền, Mật Khẩu và Trạng Thái
        String sql = "UPDATE TaiKhoanNhanVien SET MaQuyen=?, MatKhau=?, TrangThai=? WHERE MaID=?";
        try (Connection conn = JDBCConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            
            pst.setInt(1, tk.getMaQuyen());
            pst.setString(2, tk.getMatKhau());
            pst.setString(3, tk.getTrangThai());
            pst.setInt(4, tk.getMaID());
            
            return pst.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Xóa vĩnh viễn (Nên cẩn thận vì nếu tài khoản đã gán cho Nhân viên thì sẽ bị vướng Khóa Ngoại)
    public boolean delete(int maID) {
        String sql = "DELETE FROM TaiKhoanNhanVien WHERE MaID=?";
        try (Connection conn = JDBCConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            
            pst.setInt(1, maID);
            return pst.executeUpdate() > 0;
        } catch (Exception e) {
            System.err.println("Lỗi Xóa Tài Khoản (Vướng khóa ngoại): " + e.getMessage());
            return false;
        }
    }
}