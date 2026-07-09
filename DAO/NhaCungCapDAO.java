package DAO;

import DTO.NhaCungCapDTO;
import Utils.JDBCConnection;
import java.sql.*;
import java.util.ArrayList;

public class NhaCungCapDAO {
    
    // ĐÃ ĐỔI TÊN THÀNH selectAll() CHO KHỚP VỚI BUS
    public ArrayList<NhaCungCapDTO> selectAll() {
        ArrayList<NhaCungCapDTO> list = new ArrayList<>();
        try (Connection conn = JDBCConnection.getConnection()) {
            String sql = "SELECT * FROM NhaCungCap WHERE TrangThai = 1";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                NhaCungCapDTO ncc = new NhaCungCapDTO(
                    rs.getInt("MaNCC"), // Trong DB cột ID tên là MaNCC
                    rs.getNString("TenNCC"),
                    rs.getNString("DiaChi"),
                    rs.getString("SoDienThoai"),
                    rs.getString("Email")
                );
                
                // TẠO MÃ CHUỖI ẢO (VD: NCC001) ĐỂ HIỂN THỊ LÊN BẢNG VÀ TÌM KIẾM KHÔNG BỊ LỖI NULL
                ncc.setMaNCC("NCC" + String.format("%03d", rs.getInt("MaNCC")));
                
                list.add(ncc);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    // ĐÃ ĐỔI TÊN THÀNH insert() CHO KHỚP VỚI BUS
    public boolean insert(NhaCungCapDTO ncc) {
        String sql = "INSERT INTO NhaCungCap (TenNCC, DiaChi, SoDienThoai, Email, TrangThai) VALUES (?, ?, ?, ?, 1)";
        try (Connection conn = JDBCConnection.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setNString(1, ncc.getTenNCC());
            ps.setNString(2, ncc.getDiaChi());
            ps.setString(3, ncc.getSoDienThoai());
            ps.setString(4, ncc.getEmail());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    // ĐÃ ĐỔI TÊN THÀNH update() CHO KHỚP VỚI BUS
    public boolean update(NhaCungCapDTO ncc) {
        String sql = "UPDATE NhaCungCap SET TenNCC=?, DiaChi=?, SoDienThoai=?, Email=? WHERE MaNCC=?";
        try (Connection conn = JDBCConnection.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setNString(1, ncc.getTenNCC());
            ps.setNString(2, ncc.getDiaChi());
            ps.setString(3, ncc.getSoDienThoai());
            ps.setString(4, ncc.getEmail());
            ps.setInt(5, ncc.getMaID()); // Lấy ID thực tế để cập nhật
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    // ĐÃ BỔ SUNG HÀM delete() ĐỂ BUS GỌI ĐƯỢC
    public boolean delete(int maID) {
        // Thực hiện "Xóa mềm" (Chuyển trạng thái = 0) để không làm hỏng lịch sử các Phiếu Nhập cũ
        String sql = "UPDATE NhaCungCap SET TrangThai = 0 WHERE MaNCC = ?";
        try (Connection conn = JDBCConnection.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, maID);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }
}