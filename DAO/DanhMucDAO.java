package DAO;

import DTO.DanhMucDTO;
import Utils.JDBCConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DanhMucDAO {

    public List<DanhMucDTO> getAll() {
        List<DanhMucDTO> list = new ArrayList<>();
        // Chỉ lấy những danh mục chưa bị xóa mềm
        String sql = "SELECT * FROM DanhMuc WHERE TrangThai = 1";

        try(Connection con = JDBCConnection.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql)){

            while(rs.next()){
                DanhMucDTO dm = new DanhMucDTO();
                dm.setMaID(rs.getInt("MaID"));
                dm.setMaDanhMuc(rs.getString("MaDanhMuc"));
                dm.setTenDanhMuc(rs.getString("TenDanhMuc"));
                list.add(dm);
            }
        } catch(Exception e) { e.printStackTrace(); }
        return list;
    }

    public boolean addDanhMuc(DanhMucDTO dm) {
        String sql = "INSERT INTO DanhMuc(TenDanhMuc) VALUES (?)";
        try(Connection con = JDBCConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)){
            ps.setString(1, dm.getTenDanhMuc());
            return ps.executeUpdate() > 0;
        } catch(Exception e) { e.printStackTrace(); }
        return false;
    }

    public boolean updateDanhMuc(DanhMucDTO dm) {
        String sql = "UPDATE DanhMuc SET TenDanhMuc=? WHERE MaDanhMuc=?";
        try(Connection con = JDBCConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)){
            ps.setString(1, dm.getTenDanhMuc());
            ps.setString(2, dm.getMaDanhMuc());
            return ps.executeUpdate() > 0;
        } catch(Exception e) { e.printStackTrace(); }
        return false;
    }

    public boolean deleteDanhMuc(String maDM) {
        // Xóa mềm: UPDATE trạng thái về 0
        String sql = "UPDATE DanhMuc SET TrangThai = 0 WHERE MaDanhMuc = ?";
        try(Connection con = JDBCConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)){
            ps.setString(1, maDM);
            return ps.executeUpdate() > 0;
        } catch(Exception e) { e.printStackTrace(); }
        return false;
    }
}