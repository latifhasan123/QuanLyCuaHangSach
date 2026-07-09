package DAO;

import DTO.ChiTietTraNhaCungCapDTO;
import Utils.JDBCConnection;
import java.sql.*;
import java.util.ArrayList;

public class ChiTietTraNhaCungCapDAO {
    
    public boolean insert(ChiTietTraNhaCungCapDTO ct) {
        String sql = "INSERT INTO ChiTietTraNhaCungCap (MaPTN, MaSach, SoLuong) VALUES (?, ?, ?)";
        try (Connection con = JDBCConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
             
            pst.setInt(1, ct.getMaPTN());
            pst.setInt(2, ct.getMaSach());
            pst.setInt(3, ct.getSoLuong());
            
            return pst.executeUpdate() > 0;
        } catch (Exception e) { 
            e.printStackTrace(); 
            return false; 
        }
    }

    public ArrayList<ChiTietTraNhaCungCapDTO> selectAll(int maPTN) {
        ArrayList<ChiTietTraNhaCungCapDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM ChiTietTraNhaCungCap WHERE MaPTN = ?";
        
        try (Connection con = JDBCConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
             
            pst.setInt(1, maPTN);
            try (ResultSet rs = pst.executeQuery()) {
                while(rs.next()){
                    ChiTietTraNhaCungCapDTO ct = new ChiTietTraNhaCungCapDTO();
                    ct.setMaCTPTN(rs.getInt("MaCTPTN"));
                    ct.setMaPTN(rs.getInt("MaPTN"));
                    ct.setMaSach(rs.getInt("MaSach"));
                    ct.setSoLuong(rs.getInt("SoLuong"));
                    list.add(ct);
                }
            }
        } catch (Exception e) { 
            e.printStackTrace(); 
        }
        return list;
    }
}