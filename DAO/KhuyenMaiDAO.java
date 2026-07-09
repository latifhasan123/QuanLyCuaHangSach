package DAO;

import dto.KhuyenMaiDTO;
import enums.TrangThaiKhuyenMai;
import Utils.JDBCConnection; 

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KhuyenMaiDAO {

    public List<KhuyenMaiDTO> getAll() {
        List<KhuyenMaiDTO> list = new ArrayList<>();
        // ĐÃ SỬA: Lấy MaID và MaKM đúng với thiết kế DB của Sếp
        String sql = "SELECT MaID, MaKM, TenKM, PhanTramGiam, SoTienGiam, DonHangToiThieu, NgayBatDau, NgayKetThuc, TrangThai FROM KhuyenMai";

        try (Connection conn = JDBCConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                KhuyenMaiDTO khuyenMaiDTO = new KhuyenMaiDTO();
                khuyenMaiDTO.setMaKM(rs.getInt("MaID")); // Ánh xạ MaID (DB) -> maKM (DTO)
                khuyenMaiDTO.setMaCode(rs.getString("MaKM")); // Ánh xạ MaKM (DB) -> maCode (DTO)
                khuyenMaiDTO.setTenKM(rs.getString("TenKM"));
                khuyenMaiDTO.setPhanTramGiam(rs.getBigDecimal("PhanTramGiam"));
                khuyenMaiDTO.setSoTienGiam(rs.getBigDecimal("SoTienGiam"));
                khuyenMaiDTO.setDonHangToiThieu(rs.getBigDecimal("DonHangToiThieu"));
                
                Date bd = rs.getDate("NgayBatDau");
                if(bd != null) khuyenMaiDTO.setNgayBatDau(bd.toLocalDate());
                
                Date kt = rs.getDate("NgayKetThuc");
                if(kt != null) khuyenMaiDTO.setNgayKetThuc(kt.toLocalDate());
                
                khuyenMaiDTO.setTrangThai(
                        enums.TrangThaiKhuyenMai.valueOf(rs.getString("TrangThai"))
                );

                list.add(khuyenMaiDTO);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insert(KhuyenMaiDTO khuyenMaiDTO) {
        // ĐÃ SỬA: Không được INSERT cột MaKM vì DB đã tự động tạo (KM0001...)
        String sql = "INSERT INTO KhuyenMai (TenKM, PhanTramGiam, SoTienGiam, DonHangToiThieu, NgayBatDau, NgayKetThuc, TrangThai) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = JDBCConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, khuyenMaiDTO.getTenKM());
            ps.setBigDecimal(2, khuyenMaiDTO.getPhanTramGiam());
            ps.setBigDecimal(3, khuyenMaiDTO.getSoTienGiam());
            ps.setBigDecimal(4, khuyenMaiDTO.getDonHangToiThieu());
            ps.setDate(5, Date.valueOf(khuyenMaiDTO.getNgayBatDau()));
            ps.setDate(6, Date.valueOf(khuyenMaiDTO.getNgayKetThuc()));
            ps.setString(7, khuyenMaiDTO.getTrangThai().name());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean update(KhuyenMaiDTO khuyenMaiDTO) {
        // ĐÃ SỬA: Không update MaKM, dùng MaID làm điều kiện WHERE
        String sql = "UPDATE KhuyenMai SET TenKM = ?, PhanTramGiam = ?, SoTienGiam = ?, DonHangToiThieu = ?, NgayBatDau = ?, NgayKetThuc = ?, TrangThai = ? WHERE MaID = ?";

        try (Connection conn = JDBCConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, khuyenMaiDTO.getTenKM());
            ps.setBigDecimal(2, khuyenMaiDTO.getPhanTramGiam());
            ps.setBigDecimal(3, khuyenMaiDTO.getSoTienGiam());
            ps.setBigDecimal(4, khuyenMaiDTO.getDonHangToiThieu());
            ps.setDate(5, Date.valueOf(khuyenMaiDTO.getNgayBatDau()));
            ps.setDate(6, Date.valueOf(khuyenMaiDTO.getNgayKetThuc()));
            ps.setString(7, khuyenMaiDTO.getTrangThai().name());
            ps.setInt(8, khuyenMaiDTO.getMaKM()); // getMaKM() ở đây đang chứa giá trị MaID
            
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(int maID) {
        String sql = "UPDATE KhuyenMai SET TrangThai = ? WHERE MaID = ?";
        
        try (Connection conn = JDBCConnection.getConnection();
             // ĐÃ THÊM conn. VÀO TRƯỚC prepareStatement
             PreparedStatement ps = conn.prepareStatement(sql)) { 
             
            ps.setString(1, TrangThaiKhuyenMai.HetHan.name());
            ps.setInt(2, maID);
            
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}