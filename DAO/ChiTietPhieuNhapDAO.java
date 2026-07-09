package DAO;

import DTO.ChiTietPhieuNhapDTO;
import Utils.JDBCConnection; // Đã chuẩn hóa file kết nối
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ChiTietPhieuNhapDAO {
    
    public ArrayList<ChiTietPhieuNhapDTO> selectAll(String maIDString) {
        ArrayList<ChiTietPhieuNhapDTO> ketQua = new ArrayList<>();
        String sql = "SELECT * FROM ChiTietPhieuNhap WHERE MaPN = ?";
        
        try (Connection con = JDBCConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
             
            pst.setInt(1, Integer.parseInt(maIDString)); 
            
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    ChiTietPhieuNhapDTO ctpn = new ChiTietPhieuNhapDTO();
                    ctpn.setMaCTPN(rs.getInt("MaCTPN"));
                    ctpn.setMaPN(String.valueOf(rs.getInt("MaPN")));
                    ctpn.setMaSach(String.valueOf(rs.getInt("MaSach")));
                    ctpn.setSoLuong(rs.getInt("SoLuong"));
                    
                    // ĐÃ SỬA: Dùng đúng tên GiaNhap của CSDL
                    ctpn.setGiaNhap(rs.getDouble("GiaNhap")); 
                    ctpn.setThanhTien(rs.getDouble("ThanhTien"));
                    ketQua.add(ctpn);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    public boolean insert(ChiTietPhieuNhapDTO ctpn) {
        String sql = "INSERT INTO ChiTietPhieuNhap (MaPN, MaSach, SoLuong, GiaNhap) VALUES (?, ?, ?, ?)";
        try (Connection con = JDBCConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
             
            pst.setInt(1, Integer.parseInt(ctpn.getMaPN()));
            pst.setInt(2, Integer.parseInt(ctpn.getMaSach()));
            pst.setInt(3, ctpn.getSoLuong());
            
            // ĐÃ SỬA: Gọi đúng getGiaNhap()
            pst.setDouble(4, ctpn.getGiaNhap()); 
            
            return pst.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}