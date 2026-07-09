package DAO; // ĐÃ SỬA: Viết hoa

import DTO.TacGiaDTO; // ĐÃ SỬA: Viết hoa
import Utils.JDBCConnection; // ĐÃ THÊM: Import file kết nối chuẩn của hệ thống
import java.sql.*;
import java.util.*;

public class TacGiaDAO {

    public List<TacGiaDTO> getAll(){
        List<TacGiaDTO> list = new ArrayList<>();
        // ĐÃ SỬA: Chỉ lấy những tác giả chưa bị xóa mềm (TrangThai = 1)
        String sql = "SELECT * FROM TacGia WHERE TrangThai = 1";

        try(Connection con = Utils.JDBCConnection.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql)){

            while(rs.next()){
                TacGiaDTO tg = new TacGiaDTO();
                tg.setMaID(rs.getInt("MaID"));
                tg.setMaTG(rs.getString("MaTG"));
                tg.setTenTacGia(rs.getString("TenTacGia"));
                tg.setNgaySinh(rs.getDate("NgaySinh"));
                tg.setNgayMat(rs.getDate("NgayMat"));
                tg.setQuocTich(rs.getString("QuocTich"));
                list.add(tg);
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        return list;
    }

    public boolean deleteTacGia(String maTG) {
        // Lệnh chuẩn Xóa Mềm: UPDATE chuyển trạng thái thành 0
        String sql = "UPDATE TacGia SET TrangThai = 0 WHERE MaTG = ?";
        
        try (Connection conn = Utils.JDBCConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
             
            pst.setString(1, maTG);
            return pst.executeUpdate() > 0;
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean insertTacGia(TacGiaDTO tg){
        boolean result = false;
        
        // ĐÃ SỬA: Đưa NgayMat vào câu lệnh SQL
        String sql = "INSERT INTO TacGia(TenTacGia, NgaySinh, NgayMat, QuocTich) VALUES (?, ?, ?, ?)";

        try(Connection conn = Utils.JDBCConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){

            ps.setString(1, tg.getTenTacGia());
            
            if (tg.getNgaySinh() != null) {
                ps.setDate(2, new java.sql.Date(tg.getNgaySinh().getTime()));
            } else {
                ps.setNull(2, java.sql.Types.DATE);
            }

            // ĐÃ THÊM: Xử lý lưu Ngày Mất an toàn
            if (tg.getNgayMat() != null) {
                ps.setDate(3, new java.sql.Date(tg.getNgayMat().getTime()));
            } else {
                ps.setNull(3, java.sql.Types.DATE);
            }
            
            ps.setString(4, tg.getQuocTich());

            result = ps.executeUpdate() > 0;

        }catch(Exception e){
            e.printStackTrace(); // In lỗi đỏ ra console để dễ kiểm tra
        }
        return result;
    }
    
    public boolean updateTacGia(TacGiaDTO tg){
        // ĐÃ SỬA: Thêm cột NgayMat và đổi WHERE MaTG thành WHERE MaID cho an toàn tuyệt đối
        String sql = "UPDATE TacGia SET TenTacGia=?, NgaySinh=?, NgayMat=?, QuocTich=? WHERE MaID=?";

        try(Connection conn = JDBCConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){

            ps.setString(1, tg.getTenTacGia());
            
            // Xử lý an toàn cho Ngày Sinh
            if (tg.getNgaySinh() != null) {
                ps.setDate(2, new java.sql.Date(tg.getNgaySinh().getTime()));
            } else {
                ps.setNull(2, java.sql.Types.DATE);
            }

            // Xử lý an toàn cho Ngày Mất
            if (tg.getNgayMat() != null) {
                ps.setDate(3, new java.sql.Date(tg.getNgayMat().getTime()));
            } else {
                ps.setNull(3, java.sql.Types.DATE);
            }
            
            ps.setString(4, tg.getQuocTich());
            ps.setInt(5, tg.getMaID()); // Truyền MaID kiểu Int vào

            return ps.executeUpdate() > 0;

        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
    // ================= HÀM XÓA TÁC GIẢ =================
    
}