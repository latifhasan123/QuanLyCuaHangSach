package DAO;

import DTO.KhachHangDTO;
import Utils.JDBCConnection;
import java.sql.*;
import java.util.ArrayList;

public class KhachHangDAO {

    // 1. LẤY TOÀN BỘ DANH SÁCH (Chỉ lấy khách đang hoạt động)
    public ArrayList<KhachHangDTO> selectAll() {
        ArrayList<KhachHangDTO> dsKH = new ArrayList<>();
        String sql = "SELECT * FROM KhachHang WHERE TrangThai = 'HoatDong'";
        
        try (Connection con = JDBCConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            
            while (rs.next()) {
                KhachHangDTO kh = new KhachHangDTO(
                    rs.getInt("MaID"),
                    rs.getString("MaKH"),
                    rs.getInt("MaTaiKhoanKH"), // Sẽ trả về 0 nếu null trong SQL
                    rs.getString("HoTen"),
                    rs.getString("SoDienThoai"),
                    rs.getString("Email"),
                    rs.getString("DiaChi"),
                    rs.getInt("DiemTichLuy"),
                    rs.getString("TrangThai")
                );
                dsKH.add(kh);
            }
        } catch (SQLException e) {
            System.out.println("Lỗi lấy danh sách Khách hàng: " + e.getMessage());
        }
        return dsKH;
    }

    // 2. THÊM KHÁCH HÀNG MỚI (Không cần INSERT MaID, MaKH vì SQL tự sinh)
    public boolean insert(KhachHangDTO kh) {
        String sql = "INSERT INTO KhachHang (HoTen, SoDienThoai, Email, DiaChi) VALUES (?, ?, ?, ?)";
        try (Connection con = JDBCConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            
            pst.setString(1, kh.getHoTen());
            pst.setString(2, kh.getSoDienThoai());
            pst.setString(3, kh.getEmail());
            pst.setString(4, kh.getDiaChi());
            
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Lỗi thêm Khách hàng: " + e.getMessage());
            return false;
        }
    }

    // 3. SỬA THÔNG TIN KHÁCH HÀNG (Dựa theo MaID)
    public boolean update(KhachHangDTO kh) {
        String sql = "UPDATE KhachHang SET HoTen=?, SoDienThoai=?, Email=?, DiaChi=? WHERE MaID=?";
        try (Connection con = JDBCConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            
            pst.setString(1, kh.getHoTen());
            pst.setString(2, kh.getSoDienThoai());
            pst.setString(3, kh.getEmail());
            pst.setString(4, kh.getDiaChi());
            pst.setInt(5, kh.getMaID());
            
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Lỗi sửa Khách hàng: " + e.getMessage());
            return false;
        }
    }

    // 4. XÓA KHÁCH HÀNG (Xóa mềm - Cập nhật trạng thái thành 'NgungHoatDong')
    public boolean delete(int maID) {
        String sql = "UPDATE KhachHang SET TrangThai = 'NgungHoatDong' WHERE MaID=?";
        try (Connection con = JDBCConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            
            pst.setInt(1, maID);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Lỗi xóa Khách hàng: " + e.getMessage());
            return false;
        }
    }
    // ==========================================================
    // NGHIỆP VỤ ĐĂNG NHẬP (JOIN 2 bảng để kiểm tra mật khẩu)
    // ==========================================================
    public DTO.KhachHangDTO dangNhap(String user, String pass) {
        DTO.KhachHangDTO kh = null;
        // Kiểm tra user (có thể là Email hoặc SĐT) với Mật khẩu trong bảng TaiKhoanKhachHang
        String sql = "SELECT kh.* FROM KhachHang kh "
                   + "INNER JOIN TaiKhoanKhachHang tk ON kh.MaTaiKhoanKH = tk.MaID "
                   + "WHERE (kh.Email = ? OR kh.SoDienThoai = ?) "
                   + "AND tk.MatKhau = ? AND tk.TrangThai = 'HoatDong'";
        
        try (java.sql.Connection con = Utils.JDBCConnection.getConnection();
             java.sql.PreparedStatement pst = con.prepareStatement(sql)) {
            
            pst.setString(1, user);
            pst.setString(2, user);
            pst.setString(3, pass);
            java.sql.ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {
                kh = new DTO.KhachHangDTO();
                kh.setMaID(rs.getInt("MaID"));
                kh.setMaKH(rs.getString("MaKH"));
                kh.setMaTaiKhoanKH(rs.getInt("MaTaiKhoanKH"));
                kh.setHoTen(rs.getString("HoTen"));
                kh.setSoDienThoai(rs.getString("SoDienThoai"));
                kh.setEmail(rs.getString("Email"));
                kh.setDiaChi(rs.getString("DiaChi"));
                kh.setDiemTichLuy(rs.getInt("DiemTichLuy"));
                kh.setTrangThai(rs.getString("TrangThai"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return kh;
    }

    // ==========================================================
    // NGHIỆP VỤ ĐĂNG KÝ (Transaction: Tạo Tài Khoản -> Tạo Khách Hàng)
    // ==========================================================
    // ==========================================================
    // NGHIỆP VỤ ĐĂNG KÝ (Bản chống lỗi thiếu cột CSDL)
    // ==========================================================
    public boolean dangKy(String hoTen, String email, String sdt, String pass) {
        java.sql.Connection con = null;
        try {
            con = Utils.JDBCConnection.getConnection();
            con.setAutoCommit(false); // Bật chế độ an toàn

            // Bước 1: Tạo Tài Khoản Khách Hàng (Thêm CURRENT_TIMESTAMP cho NgayTao)
            // Sửa dòng này:
            String sqlTK = "INSERT INTO TaiKhoanKhachHang (TenDangNhap, MatKhau, TrangThai, NgayTao) VALUES (?, ?, 'HoatDong', CURRENT_TIMESTAMP)";
            java.sql.PreparedStatement pstTK = con.prepareStatement(sqlTK, java.sql.Statement.RETURN_GENERATED_KEYS);
            pstTK.setString(1, sdt); 
            pstTK.setString(2, pass);
            pstTK.executeUpdate();

            // Lấy ID tài khoản vừa được tạo
            java.sql.ResultSet rsTK = pstTK.getGeneratedKeys();
            int maTaiKhoanMoi = -1;
            if (rsTK.next()) {
                maTaiKhoanMoi = rsTK.getInt(1);
            } else {
                con.rollback();
                return false;
            }

            // Bước 2: Tạo Khách Hàng (Bổ sung thêm cột DiaChi = N'Chưa cập nhật')
            // Sửa dòng này:
            String sqlKH = "INSERT INTO KhachHang (MaTaiKhoanKH, HoTen, Email, SoDienThoai, DiemTichLuy, TrangThai, DiaChi) "
                         + "VALUES (?, ?, ?, ?, 0, 'HoatDong', N'Chưa cập nhật')";
            java.sql.PreparedStatement pstKH = con.prepareStatement(sqlKH);
            pstKH.setInt(1, maTaiKhoanMoi);
            pstKH.setString(2, hoTen);
            pstKH.setString(3, email);
            pstKH.setString(4, sdt);
            pstKH.executeUpdate();

            con.commit();
            return true;

        } catch (Exception e) {
            try { if (con != null) con.rollback(); } catch (Exception ex) {} 
            // IN LỖI RA MÀN HÌNH ĐỂ BẮT ĐÚNG BỆNH
            System.err.println("LỖI SQL KHI ĐĂNG KÝ: " + e.getMessage());
            return false;
        } finally {
            try { if (con != null) con.setAutoCommit(true); } catch (Exception ex) {}
        }
    }
}