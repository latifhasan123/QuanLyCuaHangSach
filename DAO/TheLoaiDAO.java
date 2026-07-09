package DAO; 

import DTO.TheLoaiDTO; 
import Utils.JDBCConnection; 
import java.sql.*;
import java.util.*;

public class TheLoaiDAO {

    public List<TheLoaiDTO> getAll(){
        List<TheLoaiDTO> list = new ArrayList<>();
        // ĐÃ SỬA: Lấy tên danh mục và chỉ load những dòng chưa bị xóa
        String sql = "SELECT tl.*, dm.TenDanhMuc FROM TheLoai tl LEFT JOIN DanhMuc dm ON tl.MaDanhMuc = dm.MaID WHERE tl.TrangThai = 1";

        try(Connection con = JDBCConnection.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql)){

            while(rs.next()){
                TheLoaiDTO tl = new TheLoaiDTO();
                tl.setMaID(rs.getInt("MaID"));
                tl.setMaTL(rs.getString("MaLoai"));
                tl.setTenLoai(rs.getString("TenLoai"));
                tl.setMaDanhMuc(rs.getInt("MaDanhMuc"));
                tl.setTenDanhMuc(rs.getString("TenDanhMuc")); // Kéo tên hiển thị lên bảng
                list.add(tl);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return list;
    }

    // ĐÃ THÊM: Hàm xóa mềm Thể Loại (ẩn khỏi phần mềm)
    public boolean deleteTheLoai(String maTL) {
        String sql = "UPDATE TheLoai SET TrangThai = 0 WHERE MaLoai = ?";
        try (Connection conn = JDBCConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, maTL);
            return pst.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    // ĐÃ FIX: Bỏ cột MaLoai đi, để SQL Server tự động sinh mã
    public boolean insertTheLoai(TheLoaiDTO tl){
        boolean result = false;

        String sql = "INSERT INTO TheLoai(TenLoai, MaDanhMuc) VALUES (?, ?)";

        try(Connection conn = JDBCConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){

            // Chỉ truyền vào Tên và Mã DM (2 dấu chấm hỏi)
            ps.setString(1, tl.getTenLoai());
            ps.setInt(2, tl.getMaDanhMuc());

            result = ps.executeUpdate() > 0;

        }catch(Exception e){
            e.printStackTrace();
        }

        return result;
    }

    // ĐÃ FIX: Sửa lại mệnh đề WHERE cho đúng tên cột trong CSDL là MaLoai
    public boolean updateTheLoai(TheLoaiDTO tl) {
        boolean ketQua = false;
        
        String sql = "UPDATE TheLoai SET TenLoai = ?, MaDanhMuc = ? WHERE MaLoai = ?";
        
        try (Connection conn = JDBCConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            
            // Nhồi dữ liệu vào các dấu chấm hỏi (?)
            pst.setString(1, tl.getTenLoai());
            pst.setInt(2, tl.getMaDanhMuc());
            pst.setString(3, tl.getMaTL()); // Lấy MaTL trong Java truyền vào WHERE MaLoai trong SQL
            
            // Thực thi câu lệnh
            if (pst.executeUpdate() > 0) {
                ketQua = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ketQua;
    }
}