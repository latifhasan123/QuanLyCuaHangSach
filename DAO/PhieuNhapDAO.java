package DAO;

import DTO.PhieuNhapDTO;
import Utils.JDBCConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class PhieuNhapDAO {
    
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    public ArrayList<PhieuNhapDTO> selectAll() {
        ArrayList<PhieuNhapDTO> ketQua = new ArrayList<>();
        String sql = "SELECT * FROM PhieuNhap"; 
        
        try (Connection con = JDBCConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
             
            while (rs.next()) {
                PhieuNhapDTO pn = new PhieuNhapDTO();
                pn.setMaID(rs.getInt("MaID"));
                pn.setMaPN(rs.getString("MaPN"));
                pn.setMaNV(rs.getInt("MaNV"));
                pn.setMaNCC(rs.getInt("MaNCC"));
                pn.setTongTien(rs.getDouble("TongTien"));
                pn.setTrangThai(rs.getString("TrangThai"));

                Timestamp ngayTaoTS = rs.getTimestamp("NgayTao");
                pn.setNgayTao(ngayTaoTS != null ? sdf.format(ngayTaoTS) : "");

                ketQua.add(pn);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    public int insertAndReturnId(PhieuNhapDTO pn) {
        int maIDPhieuMoi = -1;
        String sql = "INSERT INTO PhieuNhap (MaNV, MaNCC, TongTien, TrangThai, NgayTao) VALUES (?, ?, ?, ?, GETDATE())";
        
        try (Connection con = JDBCConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            
            // ĐÃ SỬA: Chống lỗi khóa ngoại nếu MaNV rỗng
            if (pn.getMaNV() <= 0) {
                pst.setNull(1, java.sql.Types.INTEGER);
            } else {
                pst.setInt(1, pn.getMaNV());
            }
            
            pst.setInt(2, pn.getMaNCC());
            pst.setDouble(3, pn.getTongTien());
            pst.setString(4, pn.getTrangThai());
            
            pst.executeUpdate();
            
            try (ResultSet rs = pst.getGeneratedKeys()) {
                if (rs.next()) { maIDPhieuMoi = rs.getInt(1); }
            }
        } catch (Exception e) { 
            e.printStackTrace(); 
        }
        return maIDPhieuMoi;
    }
    
    public boolean delete(int maID) {
        String sql = "DELETE FROM PhieuNhap WHERE MaID = ?";
        try (Connection con = JDBCConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
             
            pst.setInt(1, maID);
            return pst.executeUpdate() > 0;
        } catch (Exception e) { 
            e.printStackTrace(); 
            return false; 
        }
    }
}