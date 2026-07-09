/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DTO.DonHangDTO;
import DTO.SachDTO;
import java.sql.*;
import java.util.ArrayList;
import Utils.JDBCConnection;

public class DonHangDAO {

    // 🔥 ĐÃ SỬA: Nhận thêm tham số dsChiTiet trực tiếp từ Java
    // 🔥 ĐÃ SỬA: Đổi chữ boolean thành int
    public int insertDonHang(DonHangDTO donHang, int maKH, ArrayList<SachDTO> dsChiTiet) {
        Connection con = null;
        try {
            con = Utils.JDBCConnection.getConnection();
            con.setAutoCommit(false); 

            // 1. Chèn vào bảng DonHang
            String sqlDon = "INSERT INTO DonHang (MaKH, TongTien, TienGiam, ThanhTien, PhiGiaoHang, DiaChiGiao, tenNguoiNhan, sdtNhan, TrangThaiDon, LoaiDonHang, TrangThaiGiaoHang) "
                    + "VALUES (?, ?, 0, ?, 0, ?, ?, ?, 'ChoXuLy', 'Online', N'Chờ chuẩn bị')";
            PreparedStatement psDon = con.prepareStatement(sqlDon, Statement.RETURN_GENERATED_KEYS);
            psDon.setInt(1, maKH);
            psDon.setDouble(2, donHang.getTongTien());
            psDon.setDouble(3, donHang.getTongTien());
            psDon.setString(4, donHang.getDiaChiGiao());
            psDon.setString(5, donHang.getTenNguoiNhan());
            psDon.setString(6, donHang.getSdtNhan());
            psDon.executeUpdate();

            // Lấy ID Đơn hàng vừa tạo
            ResultSet rsGen = psDon.getGeneratedKeys();
            int maDH = -1;
            if (rsGen.next()) maDH = rsGen.getInt(1);

            // 2. Chèn Chi tiết đơn hàng và trừ kho
            String sqlCT = "INSERT INTO ChiTietDonHang (MaDH, MaSach, SoLuong, DonGia) VALUES (?, ?, ?, ?)";
            String sqlUpdateKho = "UPDATE Sach SET SoLuongTon = SoLuongTon - ? WHERE MaID = ?";
            String sqlLichSu = "INSERT INTO LichSuKho (MaSach, LoaiGiaoDich, MaChungTu, SoLuongThayDoi, GhiChu) VALUES (?, 'BAN_HANG', ?, ?, N'Bán hàng online')";

            PreparedStatement psCT = con.prepareStatement(sqlCT);
            PreparedStatement psUpKho = con.prepareStatement(sqlUpdateKho);
            PreparedStatement psLS = con.prepareStatement(sqlLichSu);

            for (SachDTO s : dsChiTiet) {
                psCT.setInt(1, maDH);
                psCT.setInt(2, s.getMaID());
                psCT.setInt(3, s.getSoLuongTon()); 
                psCT.setDouble(4, s.getGiaBan());
                psCT.addBatch();

                psUpKho.setInt(1, s.getSoLuongTon());
                psUpKho.setInt(2, s.getMaID());
                psUpKho.addBatch();

                psLS.setInt(1, s.getMaID());
                psLS.setInt(2, maDH);
                psLS.setInt(3, -s.getSoLuongTon()); 
                psLS.addBatch();
            }
            psCT.executeBatch();
            psUpKho.executeBatch();
            psLS.executeBatch();

            con.commit();
            return maDH; // 🔥 ĐÃ SỬA: Trả về chính cái mã đơn hàng vừa sinh ra
        } catch (Exception e) {
            try { if (con != null) con.rollback(); } catch (SQLException ex) {}
            e.printStackTrace();
            return -1; // 🔥 ĐÃ SỬA: Lỗi thì trả về -1
        }
    }
    public ArrayList<DonHangDTO> getLichSuDonHang(int maKH) {
        ArrayList<DonHangDTO> list = new ArrayList<>();
        try (Connection con = Utils.JDBCConnection.getConnection()) {
            // Lấy mã đơn, ngày tạo, trạng thái và tổng tiền, sắp xếp đơn mới nhất lên đầu
            String sql = "SELECT MaDH, NgayTao, TrangThaiDon, ThanhTien FROM DonHang WHERE MaKH = ? ORDER BY NgayTao DESC";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, maKH);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                DonHangDTO dh = new DonHangDTO();
                dh.setMaDH(rs.getString("MaDH"));
                dh.setNgayTao(rs.getTimestamp("NgayTao")); 
                dh.setTrangThaiDon(rs.getString("TrangThaiDon"));
                dh.setThanhTien(rs.getDouble("ThanhTien"));
                list.add(dh);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    // ================= KHÁCH HÀNG HỦY ĐƠN =================
    public boolean huyDonHang(int maDH) {
        try (Connection con = Utils.JDBCConnection.getConnection()) {
            // Đổi trạng thái đơn thành 'DaHuy'
            String sql = "UPDATE DonHang SET TrangThaiDon = 'DaHuy' WHERE MaID = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, maDH);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public ArrayList<DonHangDTO> getDonHangOnline() {
        ArrayList<DonHangDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM DonHang WHERE LoaiDonHang = 'Online' ORDER BY NgayTao DESC";
        try (Connection con = Utils.JDBCConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                DonHangDTO dh = new DonHangDTO();
                dh.setMaID(rs.getInt("MaID"));
                dh.setMaDH(rs.getString("MaDH"));
                dh.setTenNguoiNhan(rs.getString("TenNguoiNhan"));
                dh.setSdtNhan(rs.getString("SDTNhan"));
                dh.setDiaChiGiao(rs.getString("DiaChiGiao"));
                dh.setThanhTien(rs.getDouble("ThanhTien"));
                dh.setNgayTao(rs.getTimestamp("NgayTao"));
                dh.setTrangThaiDon(rs.getString("TrangThaiDon"));
                dh.setTrangThaiGiaoHang(rs.getString("TrangThaiGiaoHang"));
                list.add(dh);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean updateTrangThaiOnline(int maID, String trangThaiDon, String trangThaiGiao) {
        String sql = "UPDATE DonHang SET TrangThaiDon = ?, TrangThaiGiaoHang = ? WHERE MaID = ?";
        try (Connection con = Utils.JDBCConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, trangThaiDon);
            ps.setString(2, trangThaiGiao);
            ps.setInt(3, maID);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean hoanThanhDonOnline(int maID) {
        Connection con = null;
        try {
            con = Utils.JDBCConnection.getConnection();
            con.setAutoCommit(false); 

            String sqlUpdate = "UPDATE DonHang SET TrangThaiDon = 'HoanThanh', TrangThaiGiaoHang = N'Giao thành công' WHERE MaID = ?";
            PreparedStatement psUpdate = con.prepareStatement(sqlUpdate);
            psUpdate.setInt(1, maID);
            psUpdate.executeUpdate();

            String sqlGet = "SELECT ThanhTien FROM DonHang WHERE MaID = ?";
            PreparedStatement psGet = con.prepareStatement(sqlGet);
            psGet.setInt(1, maID);
            ResultSet rs = psGet.executeQuery();
            double thanhTien = 0;
            if (rs.next()) {
                thanhTien = rs.getDouble("ThanhTien");
            }

            String sqlCheck = "SELECT MaID FROM HoaDon WHERE MaDH = ?";
            PreparedStatement psCheck = con.prepareStatement(sqlCheck);
            psCheck.setInt(1, maID);
            ResultSet rsCheck = psCheck.executeQuery();

            if (!rsCheck.next()) {
                String sqlInsert = "INSERT INTO HoaDon (MaDH, HinhThucThanhToan, SoTienKhachTra, TienThua) VALUES (?, N'Thanh toán khi nhận hàng', ?, 0)";
                PreparedStatement psInsert = con.prepareStatement(sqlInsert);
                psInsert.setInt(1, maID);
                psInsert.setDouble(2, thanhTien);
                psInsert.executeUpdate();
            }

            con.commit();
            return true;
        } catch (Exception e) {
            try { if (con != null) con.rollback(); } catch (Exception ex) {}
            e.printStackTrace();
            return false;
        } finally {
            try { if (con != null) con.setAutoCommit(true); } catch (Exception ex) {}
        }
    }
}