/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package DAO;

import DTO.SachDTO;
import java.sql.*;
import java.util.ArrayList;
import Utils.JDBCConnection;

public class GioHangDAO {

    public int getMaGH(int maKH) {
        try (Connection con = JDBCConnection.getConnection()) {
            String sql = "SELECT MaID FROM GioHang WHERE MaKH = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, maKH);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("MaID");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
    public ArrayList<SachDTO> getChiTietGioHang(int maGH) {
        ArrayList<SachDTO> ds = new ArrayList<>();
        try (Connection con = JDBCConnection.getConnection()) {
            // 🔥 THÊM CỘT s.HinhAnh VÀO CÂU LỆNH SQL
            String sql = "SELECT s.MaID, s.MaSach, s.TenSach, s.GiaBan AS Gia, s.HinhAnh, ct.SoLuong "
                    + "FROM ChiTietGioHang ct "
                    + "INNER JOIN Sach s ON ct.MaSach = s.MaID "
                    + "WHERE ct.MaGH = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, maGH);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SachDTO s = new SachDTO();
                s.setMaID(rs.getInt("MaID"));
                s.setMaSach(rs.getString("MaSach"));
                s.setTenSach(rs.getString("TenSach"));
                s.setGiaBan(rs.getDouble("Gia"));
                s.setHinhAnh(rs.getString("HinhAnh")); // 🔥 ĐỪNG QUÊN DÒNG NÀY SẾP NHÉ
                s.setSoLuongTon(rs.getInt("SoLuong"));  
                ds.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ds;
    }

    public void clearGioHang(int maGH) {
        try (Connection con = JDBCConnection.getConnection()) {
            String sqlChiTiet = "DELETE FROM ChiTietGioHang WHERE MaGH = ?";
            PreparedStatement psChiTiet = con.prepareStatement(sqlChiTiet);
            psChiTiet.setInt(1, maGH);
            psChiTiet.executeUpdate();

            String sqlGio = "DELETE FROM GioHang WHERE MaID = ?";
            PreparedStatement psGio = con.prepareStatement(sqlGio);
            psGio.setInt(1, maGH);
            psGio.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public int taoGioHangNeuChuaCo(int maKH) {
        int maGH = getMaGH(maKH);
        if (maGH != -1) {
            return maGH;
        }
        
        try (Connection con = JDBCConnection.getConnection()) {
            String sqlInsert = "INSERT INTO GioHang (MaKH) VALUES (?)";
            PreparedStatement ps = con.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, maKH);
            ps.executeUpdate();
            
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void capNhatSanPhamTrongGio(int maGH, int maSach, int soLuongThayDoi) {
        try (Connection con = JDBCConnection.getConnection()) {
            String sqlCheck = "SELECT SoLuong FROM ChiTietGioHang WHERE MaGH = ? AND MaSach = ?";
            PreparedStatement psCheck = con.prepareStatement(sqlCheck);
            psCheck.setInt(1, maGH);
            psCheck.setInt(2, maSach);
            ResultSet rs = psCheck.executeQuery();
            
            if (rs.next()) {
                int soLuongHienTai = rs.getInt("SoLuong");
                int soLuongMoi = soLuongHienTai + soLuongThayDoi;
                
                if (soLuongMoi <= 0) {
                    xoaSanPhamKhoiGio(maGH, maSach);
                } else {
                    String sqlUpdate = "UPDATE ChiTietGioHang SET SoLuong = ? WHERE MaGH = ? AND MaSach = ?";
                    PreparedStatement psUpdate = con.prepareStatement(sqlUpdate);
                    psUpdate.setInt(1, soLuongMoi);
                    psUpdate.setInt(2, maGH);
                    psUpdate.setInt(3, maSach);
                    psUpdate.executeUpdate();
                }
            } else {
                if (soLuongThayDoi > 0) {
                    String sqlInsert = "INSERT INTO ChiTietGioHang (MaGH, MaSach, SoLuong) VALUES (?, ?, ?)";
                    PreparedStatement psInsert = con.prepareStatement(sqlInsert);
                    psInsert.setInt(1, maGH);
                    psInsert.setInt(2, maSach);
                    psInsert.setInt(3, soLuongThayDoi);
                    psInsert.executeUpdate();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void xoaSanPhamKhoiGio(int maGH, int maSach) {
        try (Connection con = JDBCConnection.getConnection()) {
            String sqlDelete = "DELETE FROM ChiTietGioHang WHERE MaGH = ? AND MaSach = ?";
            PreparedStatement psDelete = con.prepareStatement(sqlDelete);
            psDelete.setInt(1, maGH);
            psDelete.setInt(2, maSach);
            psDelete.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}