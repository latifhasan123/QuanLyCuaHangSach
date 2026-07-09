package BUS;

import DAO.NhapHangDAO;
import DAO.SachDAO;
import DAO.LichSuKhoDAO;
import DTO.NhapHangDTO;
import DTO.ChiTietPhieuNhapDTO;
import DTO.LichSuKhoDTO;
import java.util.ArrayList;

public class NhapHangBUS {
    private NhapHangDAO pnDAO = new NhapHangDAO();
    private SachDAO sachDAO = new SachDAO();
    private LichSuKhoDAO lskDAO = new LichSuKhoDAO();

    public String processNhapHang(NhapHangDTO pn, ArrayList<ChiTietPhieuNhapDTO> dsct) {
        if (dsct.isEmpty()) return "Giỏ hàng trống";
        if (pnDAO.insert(pn)) {
            for (ChiTietPhieuNhapDTO ct : dsct) {
                pnDAO.insertChiTiet(ct);
                
                try {
                    int maSach = Integer.parseInt(ct.getMaSach()); 
                    sachDAO.updateSoLuong(maSach, ct.getSoLuong());
                    
                    LichSuKhoDTO lsk = new LichSuKhoDTO();
                    lsk.setMaSachID(maSach);  // ĐÃ SỬA THÀNH setMaSachID
                    lsk.setLoaiGiaoDich("NHAP_HANG"); // ĐÃ SỬA THÀNH STRING
                    lsk.setSoLuongThayDoi(ct.getSoLuong());
                    lsk.setGhiChu("Nhập hàng mới");
                    
                    lskDAO.insert(lsk);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return "Thành công";
        }
        return "Thất bại";
    }
}