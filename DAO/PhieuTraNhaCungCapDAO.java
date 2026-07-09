package DAO;

import DTO.PhieuTraNhaCungCapDTO;
import Utils.JDBCConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class PhieuTraNhaCungCapDAO {

    public ArrayList<PhieuTraNhaCungCapDTO> selectAll() {
        ArrayList<PhieuTraNhaCungCapDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM PhieuTraNhaCungCap"; 
        
        try (Connection con = JDBCConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
             
            while (rs.next()) {
                PhieuTraNhaCungCapDTO ptn = new PhieuTraNhaCungCapDTO();
                ptn.setMaID(rs.getInt("MaID"));
                ptn.setMaPTN(rs.getString("MaPTN"));
                ptn.setMaNV(rs.getInt("MaNV"));
                ptn.setMaNCC(rs.getInt("MaNCC"));
                ptn.setLyDo(rs.getString("LyDo"));
                ptn.setTongTienHoan(rs.getDouble("TongTienHoan"));
                ptn.setNgayTao(rs.getTimestamp("NgayTao"));
                list.add(ptn);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public int insertAndReturnId(PhieuTraNhaCungCapDTO pt) {
        int maIDPhieuMoi = -1;
        String sql = "INSERT INTO PhieuTraNhaCungCap (MaNV, MaNCC, LyDo, TongTienHoan, NgayTao) VALUES (?, ?, ?, ?, GETDATE())";
        
        try (Connection con = JDBCConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            
            // Phiếu trả bắt buộc phải có NV thực hiện nên truyền thẳng luôn
            pst.setInt(1, pt.getMaNV());
            pst.setInt(2, pt.getMaNCC());
            pst.setString(3, pt.getLyDo());
            pst.setDouble(4, pt.getTongTienHoan());
            
            pst.executeUpdate();
            
            try (ResultSet rs = pst.getGeneratedKeys()) {
                if (rs.next()) { 
                    maIDPhieuMoi = rs.getInt(1); 
                }
            }
        } catch (Exception e) { 
            e.printStackTrace(); 
        }
        return maIDPhieuMoi;
    }

    public boolean delete(int maID) {
        String sql = "DELETE FROM PhieuTraNhaCungCap WHERE MaID = ?";
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