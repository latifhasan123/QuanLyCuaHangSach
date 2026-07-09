package BUS;

import DAO.PhieuTraKhachHangDAO;
import DAO.ChiTietPhieuTraKhachHangDAO;
import DAO.SachDAO;
import DTO.PhieuTraKhachHangDTO;
import DTO.ChiTietPhieuTraKhachHangDTO;
import java.util.ArrayList;

public class PhieuTraKhachHangBUS {
    private PhieuTraKhachHangDAO ptkhDAO = new PhieuTraKhachHangDAO();
    private ChiTietPhieuTraKhachHangDAO ctkhDAO = new ChiTietPhieuTraKhachHangDAO();
    private SachDAO sachDAO = new SachDAO();

    public String tiepNhanTraHang(PhieuTraKhachHangDTO pt, ArrayList<ChiTietPhieuTraKhachHangDTO> dsct) {
        if (dsct.isEmpty()) return "Chưa có thông tin hàng trả";

        if (ptkhDAO.insert(pt)) {
            for (ChiTietPhieuTraKhachHangDTO ct : dsct) {
                ct.setMaPTKH(pt.getMaPTKH()); 
                ctkhDAO.insert(ct);
                sachDAO.updateSoLuong(ct.getMaSach(), ct.getSoLuongTra());
            }
            return "Thành công: Đã tiếp nhận và cập nhật kho";
        }
        return "Lỗi hệ thống khi tạo phiếu";
    }
}