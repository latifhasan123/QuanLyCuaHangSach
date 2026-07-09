package DAO;

import DTO.SachDTO;
import Utils.JDBCConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SachDAO {

    // 1. LẤY DANH SÁCH: Lấy cả Đang Bán và Ngừng Bán, BỎ QUA sách Đã Xóa
    public List<SachDTO> getAll() {
        List<SachDTO> list = new ArrayList<>();
        // ĐÃ SỬA: Chỉ giấu những cuốn bị Xóa Mềm ('DaXoa')
        String sql = "SELECT * FROM Sach WHERE TrangThai != 'DaXoa'";
        
        try (Connection con = Utils.JDBCConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            
            while (rs.next()) {
                SachDTO s = new SachDTO();
                s.setMaID(rs.getInt("MaID"));
                s.setMaSach(rs.getString("MaSach"));
                s.setTenSach(rs.getString("TenSach"));
                s.setIsbn(rs.getString("ISBN"));
                s.setMaNXB(rs.getInt("MaNXB"));
                s.setMoTa(rs.getString("MoTa"));
                s.setHinhAnh(rs.getString("HinhAnh"));
                s.setGiaNhap(rs.getDouble("GiaNhap"));
                s.setGiaBan(rs.getDouble("GiaBan"));
                s.setSoLuongTon(rs.getInt("SoLuongTon"));
                s.setSoNgayDoiTra(rs.getInt("SoNgayDoiTra"));
                s.setTrangThai(rs.getString("TrangThai")); // Lấy trạng thái lên để hiển thị lên bảng
                
                list.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // 2. CHỨC NĂNG XÓA MỀM (Dọn rác, gõ sai) -> Biến mất khỏi màn hình
    public boolean deleteSach(int maID) {
        String sql = "UPDATE Sach SET TrangThai = 'DaXoa' WHERE MaID = ?";
        try (Connection conn = Utils.JDBCConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
             
            pst.setInt(1, maID);
            return pst.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 3. CHỨC NĂNG NGỪNG BÁN (Hết hàng, ngưng kinh doanh) -> Vẫn hiện trên bảng với nhãn "NgungBan"
    public boolean ngungBanSach(int maID) {
        String sql = "UPDATE Sach SET TrangThai = 'NgungBan' WHERE MaID = ?";
        try (Connection conn = Utils.JDBCConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
             
            pst.setInt(1, maID);
            return pst.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean insert(SachDTO s) {
        java.sql.Connection con = null;
        try {
            con = Utils.JDBCConnection.getConnection();
            con.setAutoCommit(false);
            
            String sql = "INSERT INTO Sach (TenSach, ISBN, MaNXB, MoTa, HinhAnh, GiaNhap, GiaBan, SoLuongTon, SoNgayDoiTra, TrangThai) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            
            try(java.sql.PreparedStatement ps = con.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, s.getTenSach());
                ps.setString(2, s.getIsbn());
                ps.setInt(3, s.getMaNXB());
                ps.setString(4, s.getMoTa());
                ps.setString(5, s.getHinhAnh());
                ps.setDouble(6, s.getGiaNhap());
                ps.setDouble(7, s.getGiaBan());
                ps.setInt(8, s.getSoLuongTon());
                ps.setInt(9, s.getSoNgayDoiTra());
                ps.setString(10, "DangBan");
                
                int affectedRows = ps.executeUpdate();
                if(affectedRows > 0) {
                    try(java.sql.ResultSet rs = ps.getGeneratedKeys()) {
                        if(rs.next()) {
                            int newID = rs.getInt(1);
                            
                            if(s.getListMaLoai() != null && !s.getListMaLoai().isEmpty()){
                                try(java.sql.PreparedStatement psTL = con.prepareStatement("INSERT INTO Sach_TheLoai(MaSach,MaLoai) VALUES(?,?)")){
                                    for(int ml : s.getListMaLoai()){
                                        psTL.setInt(1, newID); 
                                        psTL.setInt(2, ml); 
                                        psTL.executeUpdate();
                                    }
                                }
                            }
                            
                            if(s.getListMaTacGia() != null && !s.getListMaTacGia().isEmpty()){
                                try(java.sql.PreparedStatement psTG = con.prepareStatement("INSERT INTO Sach_TacGia(MaSach,MaTacGia) VALUES(?,?)")){
                                    for(int mtg : s.getListMaTacGia()){
                                        psTG.setInt(1, newID); 
                                        psTG.setInt(2, mtg); 
                                        psTG.executeUpdate();
                                    }
                                }
                            }
                        }
                    }
                }
            }
            con.commit();
            return true;
        } catch(Exception e) {
            try { if(con != null) con.rollback(); } catch(Exception ex) {}
            e.printStackTrace();
        } finally {
            try { if(con != null) con.setAutoCommit(true); } catch(Exception ex) {}
        }
        return false;
    }

    public boolean update(SachDTO s) {
        java.sql.Connection con = null;
        try {
            con = Utils.JDBCConnection.getConnection();
            con.setAutoCommit(false);

            String sqlSach = "UPDATE Sach SET TenSach=?, ISBN=?, MaNXB=?, GiaBan=?, TrangThai=?, MoTa=?, HinhAnh=? WHERE MaID=?";
            try(java.sql.PreparedStatement ps = con.prepareStatement(sqlSach)){
                ps.setString(1, s.getTenSach());
                ps.setString(2, s.getIsbn());
                ps.setInt(3, s.getMaNXB());
                ps.setDouble(4, s.getGiaBan());
                ps.setString(5, s.getTrangThai());
                ps.setString(6, s.getMoTa());
                ps.setString(7, s.getHinhAnh());
                ps.setInt(8, s.getMaID());
                ps.executeUpdate();
            }

            try(java.sql.PreparedStatement psDel = con.prepareStatement("DELETE FROM Sach_TheLoai WHERE MaSach=?")){
                psDel.setInt(1, s.getMaID()); 
                psDel.executeUpdate();
            }
            if(s.getListMaLoai() != null && !s.getListMaLoai().isEmpty()){
                try(java.sql.PreparedStatement psIns = con.prepareStatement("INSERT INTO Sach_TheLoai(MaSach, MaLoai) VALUES (?, ?)")){
                    for(int maLoai : s.getListMaLoai()){
                        psIns.setInt(1, s.getMaID());
                        psIns.setInt(2, maLoai);
                        psIns.executeUpdate();
                    }
                }
            }

            try(java.sql.PreparedStatement psDel = con.prepareStatement("DELETE FROM Sach_TacGia WHERE MaSach=?")){
                psDel.setInt(1, s.getMaID()); 
                psDel.executeUpdate();
            }
            if(s.getListMaTacGia() != null && !s.getListMaTacGia().isEmpty()){
                try(java.sql.PreparedStatement psIns = con.prepareStatement("INSERT INTO Sach_TacGia(MaSach, MaTacGia) VALUES (?, ?)")){
                    for(int maTG : s.getListMaTacGia()){
                        psIns.setInt(1, s.getMaID());
                        psIns.setInt(2, maTG);
                        psIns.executeUpdate();
                    }
                }
            }

            con.commit();
            return true;
        } catch(Exception e) {
            try { if (con != null) con.rollback(); } catch(Exception ex) {}
            e.printStackTrace();
        } finally {
            try { if (con != null) con.setAutoCommit(true); } catch(Exception ex) {}
        }
        return false;
    }

    public SachDTO findByID(int id) {
        SachDTO s = null;
        String sql = "SELECT s.*, n.TenNXB FROM Sach s LEFT JOIN NhaXuatBan n ON s.MaNXB = n.MaID WHERE s.MaID = ?";
        try (Connection con = JDBCConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
             
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    s = new SachDTO();
                    s.setMaID(rs.getInt("MaID"));
                    s.setMaSach(rs.getString("MaSach"));
                    s.setTenSach(rs.getString("TenSach"));
                    s.setIsbn(rs.getString("ISBN"));
                    s.setMaNXB(rs.getInt("MaNXB"));
                    s.setTenNXB(rs.getString("TenNXB")); 
                    s.setMoTa(rs.getString("MoTa"));
                    s.setHinhAnh(rs.getString("HinhAnh"));
                    s.setGiaNhap(rs.getDouble("GiaNhap"));
                    s.setGiaBan(rs.getDouble("GiaBan"));
                    s.setSoLuongTon(rs.getInt("SoLuongTon"));
                    s.setSoNgayDoiTra(rs.getInt("SoNgayDoiTra"));
                    s.setTrangThai(rs.getString("TrangThai"));
                }
            }

            if (s != null) {
                List<String> dsTenLoai = new ArrayList<>();
                try (PreparedStatement psLoai = con.prepareStatement("SELECT tl.MaID, tl.TenLoai FROM Sach_TheLoai st JOIN TheLoai tl ON st.MaLoai = tl.MaID WHERE st.MaSach = ?")) {
                    psLoai.setInt(1, id);
                    ResultSet rsLoai = psLoai.executeQuery();
                    while(rsLoai.next()) {
                        s.getListMaLoai().add(rsLoai.getInt("MaID"));
                        dsTenLoai.add(rsLoai.getString("TenLoai"));
                    }
                }
                s.setTenLoai(String.join(", ", dsTenLoai));

                List<String> dsTenTG = new ArrayList<>();
                try (PreparedStatement psTG = con.prepareStatement("SELECT tg.MaID, tg.TenTacGia FROM Sach_TacGia st JOIN TacGia tg ON st.MaTacGia = tg.MaID WHERE st.MaSach = ?")) {
                    psTG.setInt(1, id);
                    ResultSet rsTG = psTG.executeQuery();
                    while(rsTG.next()) {
                        s.getListMaTacGia().add(rsTG.getInt("MaID"));
                        dsTenTG.add(rsTG.getString("TenTacGia"));
                    }
                }
                s.setTenTacGia(String.join(", ", dsTenTG));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }

    public boolean updateSoLuong(int maID, int soLuongThayDoi) {
        String sql = "UPDATE Sach SET SoLuongTon = SoLuongTon + ? WHERE MaID = ?";
        try (Connection con = JDBCConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, soLuongThayDoi);
            ps.setInt(2, maID);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean truTonKho(int maSach, int soLuong) {
        String sql = "UPDATE Sach SET SoLuongTon = SoLuongTon - ? WHERE MaID = ?";
        try (Connection conn = JDBCConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, soLuong);
            ps.setInt(2, maSach);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean congTonKho(int maSach, int soLuong) {
        String sql = "UPDATE Sach SET SoLuongTon = SoLuongTon + ? WHERE MaID = ?";
        try (Connection conn = JDBCConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, soLuong);
            ps.setInt(2, maSach);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean checkTrungISBN(String isbn, int maIDHienTai) {
        String sql = "SELECT MaID FROM Sach WHERE ISBN = ? AND MaID != ?";
        try (Connection con = JDBCConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, isbn);
            ps.setInt(2, maIDHienTai);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}