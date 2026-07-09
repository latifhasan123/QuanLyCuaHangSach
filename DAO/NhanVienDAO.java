package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import DTO.NhanVienDTO; 
import Utils.JDBCConnection; 

public class NhanVienDAO {

    public ArrayList<NhanVienDTO> selectAll() {
        ArrayList<NhanVienDTO> ketQua = new ArrayList<>();
        
        // ĐỔI THÀNH LEFT JOIN: Để nhân viên bảo vệ (không có tài khoản, không có mã quyền) vẫn hiện lên bảng
        String sql = "SELECT nv.*, cv.TenChucVu, tk.MaTKNV AS MaTaiKhoan_Chu " +
                     "FROM NhanVien nv " +
                     "LEFT JOIN ChucVu cv ON nv.MaChucVu = cv.MaID " +
                     "LEFT JOIN TaiKhoanNhanVien tk ON nv.MaTaiKhoanNV = tk.MaID";
        
        try (Connection conn = JDBCConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            
            while (rs.next()) {
                NhanVienDTO nv = new NhanVienDTO();
                nv.setMaID(rs.getInt("MaID"));
                nv.setMaNV(rs.getString("MaNV")); 
                nv.setMaChucVu(rs.getInt("MaChucVu"));
                nv.setMaTaiKhoanNV(rs.getInt("MaTaiKhoanNV"));
                nv.setHoTen(rs.getString("HoTen"));
                nv.setEmail(rs.getString("Email"));
                nv.setSoDienThoai(rs.getString("SoDienThoai"));
                
                // 1. XỬ LÝ HIỂN THỊ CHỨC VỤ (Lấy chữ Sếp gõ tay HOẶC chữ trong hệ thống)
                int maCV = rs.getInt("MaChucVu");
                if (rs.wasNull() || maCV == 0) {
                    nv.setMaChucVu_Chu(rs.getString("TenChucVuCustom")); 
                } else {
                    nv.setMaChucVu_Chu(rs.getString("TenChucVu")); 
                }

                // 2. XỬ LÝ HIỂN THỊ TÀI KHOẢN
                nv.setMaTaiKhoan_Chu(rs.getString("MaTaiKhoan_Chu"));
                
                ketQua.add(nv);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    public boolean insert(NhanVienDTO nv) {
        // THÊM CỘT TenChucVuCustom VÀO CÂU LỆNH SQL
        String sql = "INSERT INTO NhanVien (MaChucVu, MaTaiKhoanNV, HoTen, Email, SoDienThoai, TenChucVuCustom) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = JDBCConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            
            // XỬ LÝ CỘT CHỨC VỤ (Tham số 1 và 6)
            if (nv.getMaChucVu() == 0) {
                pst.setNull(1, java.sql.Types.INTEGER); // Không có mã chức vụ ERP
                pst.setString(6, nv.getMaChucVu_Chu()); // Lưu chữ "Bảo vệ" vào cột Custom
            } else {
                pst.setInt(1, nv.getMaChucVu());        // Truyền mã chức vụ ERP bình thường
                pst.setNull(6, java.sql.Types.NVARCHAR);// Xóa rỗng Custom
            }

            // XỬ LÝ CỘT TÀI KHOẢN (Tham số 2)
            if (nv.getMaTaiKhoanNV() == 0) {
                pst.setNull(2, java.sql.Types.INTEGER); // Truyền RỖNG (NULL) để không bị lỗi Foreign Key
            } else {
                pst.setInt(2, nv.getMaTaiKhoanNV());    
            }
            
            pst.setString(3, nv.getHoTen());
            pst.setString(4, nv.getEmail());
            pst.setString(5, nv.getSoDienThoai());
            
            return pst.executeUpdate() > 0; 
        } catch (Exception e) {
            System.err.println("Lỗi Insert DAO: " + e.getMessage());
            return false;
        }
    }

    public boolean update(NhanVienDTO nv) {
        // THÊM CỘT TenChucVuCustom VÀO CÂU LỆNH SQL
        String sql = "UPDATE NhanVien SET MaChucVu=?, MaTaiKhoanNV=?, HoTen=?, Email=?, SoDienThoai=?, TenChucVuCustom=? WHERE MaID=?";
        try (Connection conn = JDBCConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            
            // XỬ LÝ CỘT CHỨC VỤ (Tham số 1 và 6)
            if (nv.getMaChucVu() == 0) {
                pst.setNull(1, java.sql.Types.INTEGER);
                pst.setString(6, nv.getMaChucVu_Chu());
            } else {
                pst.setInt(1, nv.getMaChucVu());
                pst.setNull(6, java.sql.Types.NVARCHAR);
            }

            // XỬ LÝ CỘT TÀI KHOẢN (Tham số 2)
            if (nv.getMaTaiKhoanNV() == 0) {
                pst.setNull(2, java.sql.Types.INTEGER);
            } else {
                pst.setInt(2, nv.getMaTaiKhoanNV());
            }
            
            pst.setString(3, nv.getHoTen());
            pst.setString(4, nv.getEmail());
            pst.setString(5, nv.getSoDienThoai());
            pst.setInt(7, nv.getMaID()); // Tham số 7 là MaID
            
            return pst.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int maID) {
        String sql = "DELETE FROM NhanVien WHERE MaID=?";
        try (Connection conn = JDBCConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            
            pst.setInt(1, maID);
            return pst.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}