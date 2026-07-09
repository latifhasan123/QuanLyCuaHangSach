package bus; 

import dao.ChiTietHoaDonDAO;
import dao.HoaDonDAO;
import DAO.LichSuKhoDAO;
import DAO.SachDAO; 
import dto.ChiTietHoaDonDTO;
import dto.HoaDonDTO;
import DTO.LichSuKhoDTO;

import java.util.List;

public class HoaDonBUS {

    private HoaDonDAO hoaDonDAO = new HoaDonDAO();
    private ChiTietHoaDonDAO chiTietHoaDonDAO = new ChiTietHoaDonDAO();
    private SachDAO sachDAO = new SachDAO();
    private LichSuKhoDAO lichSuKhoDAO = new LichSuKhoDAO();

    public List<HoaDonDTO> getAll() {
        return hoaDonDAO.getAll();
    }

    public String addHoaDon(HoaDonDTO hoaDon, List<ChiTietHoaDonDTO> danhSachCTHD) {
        if (hoaDon.getMaNV() == null || hoaDon.getMaNV() <= 0) {
            return "Lỗi: Nhân viên không hợp lệ!";
        }
        if (danhSachCTHD == null || danhSachCTHD.isEmpty()) {
            return "Lỗi: Hóa đơn phải có ít nhất 1 sản phẩm!";
        }
        
        int maHoaDonMoi = hoaDonDAO.insert(hoaDon);

        if (maHoaDonMoi > 0) {
            for (ChiTietHoaDonDTO chiTietHoaDon : danhSachCTHD) {
                chiTietHoaDon.setMaHD(maHoaDonMoi);
                chiTietHoaDonDAO.insert(chiTietHoaDon);

                sachDAO.truTonKho(chiTietHoaDon.getMaSach(), chiTietHoaDon.getSoLuong());

                LichSuKhoDTO lichSu = new LichSuKhoDTO();
                // --- ĐÃ SỬA LẠI THÀNH setMaSachID VÀ TRUYỀN CHUỖI STRING ---
                lichSu.setMaSachID(chiTietHoaDon.getMaSach());
                lichSu.setLoaiGiaoDich("BAN_HANG"); 
                
                lichSu.setMaChungTu(maHoaDonMoi);
                lichSu.setSoLuongThayDoi(-chiTietHoaDon.getSoLuong());
                lichSu.setGhiChu("Bán hàng theo hóa đơn " + String.format("HD%03d", maHoaDonMoi));

                lichSuKhoDAO.insert(lichSu);
            }
            return "Thành công: Đã tạo hóa đơn và xuất kho sách!";
        }
        return "Lỗi CSDL: Không thể tạo hóa đơn (Kiểm tra lại Khóa ngoại Nhân Viên/Khách hàng)!";
    }

    public String deleteHoaDon(int maHoaDon) {
        if (hoaDonDAO.delete(maHoaDon)) {
            List<ChiTietHoaDonDTO> dsChiTiet = chiTietHoaDonDAO.getByMaHD(maHoaDon);
            for (ChiTietHoaDonDTO ds : dsChiTiet) {
                sachDAO.congTonKho(ds.getMaSach(), ds.getSoLuong());

                LichSuKhoDTO lichSu = new LichSuKhoDTO();
                // --- ĐÃ SỬA LẠI THÀNH setMaSachID VÀ TRUYỀN CHUỖI STRING ---
                lichSu.setMaSachID(ds.getMaSach());
                lichSu.setLoaiGiaoDich("KHACH_TRA"); 
                
                lichSu.setMaChungTu(maHoaDon);
                lichSu.setSoLuongThayDoi(ds.getSoLuong());
                lichSu.setGhiChu("Hoàn kho do hủy đơn " + String.format("HD%03d", maHoaDon));

                lichSuKhoDAO.insert(lichSu);
            }
            return "Thành công: Đã hủy đơn hàng và hoàn trả sách về kho!";
        }
        return "Lỗi: Không thể hủy đơn hàng";
    }

    public List<ChiTietHoaDonDTO> getChiTietByMaHD(int maHD) {
        if (maHD <= 0) return null;
        return chiTietHoaDonDAO.getByMaHD(maHD);
    }

    public double getDoanhThuHomNay() {
        java.time.LocalDate today = java.time.LocalDate.now();
        double doanhThu = 0;

        List<HoaDonDTO> dsHoaDon = hoaDonDAO.getAll();
        if (dsHoaDon != null) {
            for (HoaDonDTO hd : dsHoaDon) {
                if (hd.getTrangThai() == enums.TrangThaiGiaoDich.HoanThanh
                        && hd.getNgayTao() != null
                        && hd.getNgayTao().toLocalDate().equals(today)) {
                    if(hd.getThanhTien() != null) {
                        doanhThu += hd.getThanhTien().doubleValue();
                    } else {
                        double tong = hd.getTongTien() != null ? hd.getTongTien().doubleValue() : 0;
                        double giam = hd.getTienGiam() != null ? hd.getTienGiam().doubleValue() : 0;
                        doanhThu += (tong - giam);
                    }
                }
            }
        }
        return doanhThu;
    }

    public int getSoDonHomNay() {
        java.time.LocalDate today = java.time.LocalDate.now();
        int soDon = 0;

        List<HoaDonDTO> dsHoaDon = hoaDonDAO.getAll();
        if (dsHoaDon != null) {
            for (HoaDonDTO hd : dsHoaDon) {
                if (hd.getNgayTao() != null && hd.getNgayTao().toLocalDate().equals(today)) {
                    soDon++;
                }
            }
        }
        return soDon;
    }

    public List<HoaDonDTO> getRecentOrders(int limit) {
        List<HoaDonDTO> dsHoaDon = hoaDonDAO.getAll();
        if (dsHoaDon == null) return null;

        dsHoaDon.sort((hd1, hd2) -> Integer.compare(hd2.getMaHD(), hd1.getMaHD()));

        if (dsHoaDon.size() > limit) {
            return dsHoaDon.subList(0, limit);
        }
        return dsHoaDon;
    }
}