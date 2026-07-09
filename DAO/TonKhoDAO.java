package DAO;

import DTO.TonKhoDTO;
import Utils.JDBCConnection;
import java.sql.*;
import java.util.ArrayList;

public class TonKhoDAO {

    public ArrayList<TonKhoDTO> selectAll() {
        ArrayList<TonKhoDTO> list = new ArrayList<>();
        // Chỉ lấy những sách đang bán hoặc ngưng bán (Không lấy sách đã xóa)
        String sql = "SELECT MaID, MaSach, TenSach, GiaNhap, SoLuongTon FROM Sach WHERE TrangThai != 'DaXoa' OR TrangThai IS NULL";
        
        try (Connection conn = JDBCConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            
            while (rs.next()) {
                TonKhoDTO tk = new TonKhoDTO();
                tk.setMaID(rs.getInt("MaID"));
                tk.setMaSach(rs.getString("MaSach"));
                tk.setTenSach(rs.getString("TenSach"));
                tk.setGiaNhap(rs.getDouble("GiaNhap"));
                tk.setSoLuongTon(rs.getInt("SoLuongTon"));
                list.add(tk);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // HÀM KIỂM KÊ (SỬ DỤNG TRANSACTION ĐỂ ĐẢM BẢO TOÀN VẸN DỮ LIỆU)
    public boolean capNhatKiemKe(int maSachID, int soLuongHeThong, int soLuongThucTe, String lyDo, int maNV) {
        String updateSach = "UPDATE Sach SET SoLuongTon = ? WHERE MaID = ?";
        String insertLSK = "INSERT INTO LichSuKho (MaSach, LoaiGiaoDich, MaChungTu, SoLuongThayDoi, GhiChu) VALUES (?, 'KIEM_KE', 0, ?, ?)";
        Connection conn = null;
        
        try {
            conn = JDBCConnection.getConnection();
            conn.setAutoCommit(false); // Khóa AutoCommit để chạy Transaction
            
            // Bước 1: Cập nhật lại tồn kho trong bảng Sách
            try (PreparedStatement pstSach = conn.prepareStatement(updateSach)) {
                pstSach.setInt(1, soLuongThucTe);
                pstSach.setInt(2, maSachID);
                pstSach.executeUpdate();
            }
            
            // Bước 2: Lưu vết vào bảng LichSuKho
            try (PreparedStatement pstLSK = conn.prepareStatement(insertLSK)) {
                int chenhLech = soLuongThucTe - soLuongHeThong; 
                
                // ==================================================
                // ĐÃ SỬA: Nối chữ "Kiểm kê" với cái lý do Sếp nhập trên GUI
                // Kết quả hiển thị: "Kiểm kê. Lý do: Chuột cắn nát sách"
                // ==================================================
                String ghiChuChiTiet = "Kiểm kê. Lý do: " + lyDo; 
                
                pstLSK.setInt(1, maSachID);
                pstLSK.setInt(2, chenhLech); 
                pstLSK.setString(3, ghiChuChiTiet);
                pstLSK.executeUpdate();
            }
            
            conn.commit(); // Thành công cả 2 bước thì mới Lưu DB
            return true;
            
        } catch (Exception e) {
            if (conn != null) try { conn.rollback(); } catch (Exception ex) {} // Lỗi thì hoàn tác
            e.printStackTrace();
            return false;
        } finally {
            if (conn != null) try { conn.setAutoCommit(true); conn.close(); } catch (Exception e) {}
        }
    }
}