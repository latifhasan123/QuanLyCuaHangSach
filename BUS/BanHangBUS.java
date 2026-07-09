package BUS; 

import DAO.SachDAO;
import dto.ChiTietHoaDonDTO;
import dto.HoaDonDTO;
import DTO.SachDTO;
import enums.LoaiHoaDon;
import enums.TrangThaiGiaoDich;

import java.math.BigDecimal;
import java.util.List;

public class BanHangBUS {

    private SachDAO sachDAO = new SachDAO();
    
    public boolean themVaoGioHang(String maSachHienThi, int soLuongThem, List<ChiTietHoaDonDTO> dsGioHang) {
        List<SachDTO> dsSach = sachDAO.getAll();
        SachDTO sachChon = null;

        for (SachDTO s : dsSach) {
            if (s.getMaSach() != null && s.getMaSach().equalsIgnoreCase(maSachHienThi) 
                && s.getTrangThai() != null && s.getTrangThai().equals("DangBan")) {
                sachChon = s;
                break;
            }
        }

        if (sachChon == null || sachChon.getSoLuongTon() < soLuongThem) return false;

        for (ChiTietHoaDonDTO ct : dsGioHang) {
            if (ct.getMaSach() == sachChon.getMaID()) {
                int tongSLMoi = ct.getSoLuong() + soLuongThem;
                if (tongSLMoi > sachChon.getSoLuongTon()) return false;
                ct.setSoLuong(tongSLMoi);
                BigDecimal giaBan = new BigDecimal(String.valueOf(sachChon.getGiaBan()));
                ct.setThanhTien(giaBan.multiply(new BigDecimal(tongSLMoi)));
                return true;
            }
        }

        ChiTietHoaDonDTO ctMoi = new ChiTietHoaDonDTO();
        ctMoi.setMaSach(sachChon.getMaID()); 
        ctMoi.setTenSach(sachChon.getTenSach());
        ctMoi.setSoLuong(soLuongThem);
        BigDecimal giaBan = new BigDecimal(String.valueOf(sachChon.getGiaBan()));
        ctMoi.setDonGia(giaBan);
        ctMoi.setThanhTien(giaBan.multiply(new BigDecimal(soLuongThem)));

        dsGioHang.add(ctMoi);
        return true;
    }

    // ĐÃ SỬA: Trả về chuỗi String để báo lỗi rõ ràng cho GUI
    public String thanhToanHoaDon(Integer maNV, Integer maKH, Integer maKM, List<ChiTietHoaDonDTO> dsGioHang, double tongTien, double giamGia, double thucThu) {
        try {
            HoaDonDTO hd = new HoaDonDTO();
            hd.setMaNV(maNV != null && maNV > 0 ? maNV : 1);
            
            // QUAN TRỌNG NHẤT: Bắt buộc dùng null nếu không có ID khách hàng, để SQL không bị lỗi khóa ngoại
            hd.setMaKH(maKH != null && maKH > 0 ? maKH : null); 
            hd.setMaKM(maKM != null && maKM > 0 ? maKM : null); 

            hd.setTongTien(new BigDecimal(String.valueOf(tongTien)));
            hd.setTienGiam(new BigDecimal(String.valueOf(giamGia)));
            hd.setThanhTien(new BigDecimal(String.valueOf(thucThu)));
            hd.setLoaiHoaDon(LoaiHoaDon.TaiQuay);
            hd.setTrangThai(TrangThaiGiaoDich.HoanThanh);

            // GỌI THẲNG QUA HOADONBUS CỦA SẾP ĐỂ ĐỒNG BỘ MỌI THỨ
            bus.HoaDonBUS hdBUS = new bus.HoaDonBUS();
            return hdBUS.addHoaDon(hd, dsGioHang); // Trả về câu thông báo của Sếp luôn

        } catch (Exception e) {
            e.printStackTrace();
            return "Lỗi CSDL: " + e.getMessage();
        }
    }
}