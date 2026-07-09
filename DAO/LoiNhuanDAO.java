package DAO;

import DTO.LoiNhuanDTO;
import Utils.JDBCConnection;
import java.sql.*;
import java.util.ArrayList;

public class LoiNhuanDAO {

    // Lấy lợi nhuận theo từng đơn hàng (Có thể lọc theo khoảng thời gian)
    public ArrayList<LoiNhuanDTO> getLoiNhuanTheoDonHang(String tuNgay, String denNgay) {
        ArrayList<LoiNhuanDTO> list = new ArrayList<>();
        
        // Câu lệnh SQL "thần thánh": Tính tổng tiền vốn của tất cả sách trong 1 đơn hàng
        String sql = "SELECT dh.MaDH, dh.NgayTao, dh.ThanhTien AS DoanhThu, " +
                     "ISNULL(SUM(ct.SoLuong * s.GiaNhap), 0) AS ChiPhi " +
                     "FROM DonHang dh " +
                     "JOIN ChiTietDonHang ct ON dh.MaID = ct.MaDH " +
                     "JOIN Sach s ON ct.MaSach = s.MaID " +
                     "WHERE dh.TrangThaiDon = 'HoanThanh' ";

        if (tuNgay != null && !tuNgay.isEmpty()) {
            sql += " AND dh.NgayTao >= '" + tuNgay + " 00:00:00' ";
        }
        if (denNgay != null && !denNgay.isEmpty()) {
            sql += " AND dh.NgayTao <= '" + denNgay + " 23:59:59' ";
        }
        
        sql += " GROUP BY dh.MaID, dh.MaDH, dh.NgayTao, dh.ThanhTien " +
               " ORDER BY dh.NgayTao DESC";

        try (Connection conn = JDBCConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            
            while (rs.next()) {
                LoiNhuanDTO ln = new LoiNhuanDTO();
                ln.setMaDonHang(rs.getString("MaDH"));
                if (rs.getTimestamp("NgayTao") != null) {
                    ln.setNgayTao(rs.getTimestamp("NgayTao").toLocalDateTime());
                }
                ln.setDoanhThu(rs.getDouble("DoanhThu"));
                ln.setChiPhi(rs.getDouble("ChiPhi"));
                // Lợi nhuận sẽ được tự tính trong hàm setChiPhi của DTO
                list.add(ln);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}