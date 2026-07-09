package BUS;

import DAO.PhieuNhapDAO;
import DAO.ChiTietPhieuNhapDAO;
import DAO.SachDAO; 
import DAO.LichSuKhoDAO;
import DTO.PhieuNhapDTO;
import DTO.ChiTietPhieuNhapDTO;
import DTO.LichSuKhoDTO;
import java.util.ArrayList;

public class PhieuNhapBUS {
    private PhieuNhapDAO pnDAO = new PhieuNhapDAO();
    private ChiTietPhieuNhapDAO ctpnDAO = new ChiTietPhieuNhapDAO();
    private SachDAO sachDAO = new SachDAO(); 
    private LichSuKhoDAO lskDAO = new LichSuKhoDAO(); 
    private ArrayList<PhieuNhapDTO> listPN = null;

    public PhieuNhapBUS() { docDanhSach(); }
    public void docDanhSach() { this.listPN = pnDAO.selectAll(); }
    public ArrayList<PhieuNhapDTO> getListPN() {
        if (listPN == null) docDanhSach();
        return listPN;
    }

    public int themPN(PhieuNhapDTO pn) {
        int newId = pnDAO.insertAndReturnId(pn);
        if (newId > 0) {
            pn.setMaID(newId);
            if (listPN != null) listPN.add(pn);
        }
        return newId;
    }

    public boolean taoPhieuNhapHoanChinh(PhieuNhapDTO pn, ArrayList<ChiTietPhieuNhapDTO> dsCT) {
        int newId = themPN(pn); 
        if (newId > 0) {
            for (ChiTietPhieuNhapDTO ct : dsCT) {
                ct.setMaPN(String.valueOf(newId));
                ctpnDAO.insert(ct);
                
                int maSach = Integer.parseInt(ct.getMaSach());
                sachDAO.congTonKho(maSach, ct.getSoLuong()); 
                
                LichSuKhoDTO lsk = new LichSuKhoDTO();
                lsk.setMaSachID(maSach); // ĐÃ SỬA THÀNH setMaSachID
                lsk.setLoaiGiaoDich("NHAP_HANG"); // ĐÃ SỬA THÀNH STRING
                lsk.setMaChungTu(newId);
                lsk.setSoLuongThayDoi(ct.getSoLuong());
                lsk.setGhiChu("Nhập hàng từ NCC");
                
                lskDAO.insert(lsk);
            }
            docDanhSach(); 
            return true;
        }
        return false;
    }

    public boolean xoaPhieuNhap(int maID) {
        ArrayList<ChiTietPhieuNhapDTO> listCT = ctpnDAO.selectAll(String.valueOf(maID));
        for(ChiTietPhieuNhapDTO ct : listCT) {
            int maSach = Integer.parseInt(ct.getMaSach());
            sachDAO.truTonKho(maSach, ct.getSoLuong()); 
            
            LichSuKhoDTO lsk = new LichSuKhoDTO();
            lsk.setMaSachID(maSach); // ĐÃ SỬA THÀNH setMaSachID
            lsk.setLoaiGiaoDich("NHAP_HANG"); // ĐÃ SỬA THÀNH STRING
            lsk.setMaChungTu(maID);
            lsk.setSoLuongThayDoi(-ct.getSoLuong());
            lsk.setGhiChu("Hủy phiếu nhập hàng");
            
            lskDAO.insert(lsk);
        }
        boolean kq = pnDAO.delete(maID);
        if(kq) docDanhSach(); 
        return kq;
    }
}