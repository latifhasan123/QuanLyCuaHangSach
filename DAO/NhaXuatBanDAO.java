package DAO; // ĐÃ SỬA: Viết hoa

import DTO.NhaXuatBanDTO; // ĐÃ SỬA: Viết hoa
import Utils.JDBCConnection; // ĐÃ THÊM: Import file kết nối DB chuẩn
import java.sql.*;
import java.util.*;

public class NhaXuatBanDAO {

    public List<NhaXuatBanDTO> getAll(){
        List<NhaXuatBanDTO> list = new ArrayList<>();
        // ĐÃ SỬA: Chỉ lấy những NXB chưa bị xóa (TrangThai = 1)
        String sql = "SELECT * FROM NhaXuatBan WHERE TrangThai = 1";

        try(Connection con = JDBCConnection.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql)){

            while(rs.next()){
                NhaXuatBanDTO nxb = new NhaXuatBanDTO();
                nxb.setMaID(rs.getInt("MaID"));
                nxb.setMaNXB(rs.getString("MaNXB"));
                nxb.setTenNXB(rs.getString("TenNXB"));
                nxb.setDiaChi(rs.getString("DiaChi"));
                list.add(nxb);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return list;
    }
    
    // ================= HÀM THÊM NXB =================
    public boolean addNXB(NhaXuatBanDTO nxb) {
        boolean result = false;
        // LƯU Ý: Tuyệt đối không Insert cột MaNXB và MaID vì SQL Server tự động sinh
        String sql = "INSERT INTO NhaXuatBan(TenNXB, DiaChi) VALUES (?, ?)";

        try (java.sql.Connection conn = Utils.JDBCConnection.getConnection(); // Chú ý đường dẫn file Connection của Sếp
             java.sql.PreparedStatement ps = conn.prepareStatement(sql)) {

            // Chỉ truyền 2 tham số là Tên và Địa chỉ
            ps.setString(1, nxb.getTenNXB());
            ps.setString(2, nxb.getDiaChi());

            result = ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
    public boolean updateNXB(NhaXuatBanDTO nxb){
        String sql = "UPDATE NhaXuatBan SET TenNXB=?, DiaChi=? WHERE MaNXB=?";

        // ĐÃ SỬA: Dùng JDBCConnection thay cho DBConnection
        try(Connection conn = JDBCConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){

            ps.setString(1,nxb.getTenNXB());
            ps.setString(2,nxb.getDiaChi());
            ps.setString(3,nxb.getMaNXB());

            return ps.executeUpdate() > 0;

        }catch(Exception e){
            e.printStackTrace();
        }

        return false;
    }
    // Thêm hàm này vào trước dấu ngoặc đóng } cuối cùng của class NhaXuatBanDAO
    public boolean deleteNXB(String maNXB) {
        // ĐÃ SỬA: Chuyển DELETE thành UPDATE để Xóa mềm
        String sql = "UPDATE NhaXuatBan SET TrangThai = 0 WHERE MaNXB = ?";
        
        try (Connection conn = Utils.JDBCConnection.getConnection(); 
             PreparedStatement pst = conn.prepareStatement(sql)) {
             
            pst.setString(1, maNXB);
            return pst.executeUpdate() > 0;
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}