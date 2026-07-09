package BUS;

import DAO.PhieuTraNhaCungCapDAO;
import DAO.SachDAO; 
import DAO.LichSuKhoDAO; 
import DTO.PhieuTraNhaCungCapDTO;
import DTO.ChiTietTraNhaCungCapDTO;
import DTO.LichSuKhoDTO; // ĐÃ FIX LỖI DƯ DẤU CÁCH Ở ĐÂY
import java.util.ArrayList;

public class PhieuTraNhaCungCapBUS {
    private PhieuTraNhaCungCapDAO ptnDAO = new PhieuTraNhaCungCapDAO();
    private ChiTietTraNhaCungCapBUS ctBUS = new ChiTietTraNhaCungCapBUS();
    private SachDAO sachDAO = new SachDAO(); 
    private LichSuKhoDAO lskDAO = new LichSuKhoDAO(); 
    private ArrayList<PhieuTraNhaCungCapDTO> listPTN = null;

    public PhieuTraNhaCungCapBUS() { docDanhSach(); }
    public void docDanhSach() { this.listPTN = ptnDAO.selectAll(); }
    public ArrayList<PhieuTraNhaCungCapDTO> getListPTN() {
        if (listPTN == null) docDanhSach();
        return listPTN;
    }

    public boolean taoPhieuTraHoanChinh(PhieuTraNhaCungCapDTO pt, ArrayList<ChiTietTraNhaCungCapDTO> dsCT) {
        int newId = ptnDAO.insertAndReturnId(pt); 
        if (newId > 0) {
            for (ChiTietTraNhaCungCapDTO ct : dsCT) {
                ct.setMaPTN(newId);
                ctBUS.themCTPTN(ct);
                
                sachDAO.truTonKho(ct.getMaSach(), ct.getSoLuong()); 
                
                LichSuKhoDTO lsk = new LichSuKhoDTO();
                lsk.setMaSachID(ct.getMaSach()); // ĐÃ SỬA THÀNH setMaSachID
                lsk.setLoaiGiaoDich("TRA_NCC"); // ĐÃ SỬA THÀNH STRING
                lsk.setMaChungTu(newId);
                lsk.setSoLuongThayDoi(-ct.getSoLuong());
                lsk.setGhiChu(pt.getLyDo());
                
                lskDAO.insert(lsk);
            }
            docDanhSach(); 
            return true;
        }
        return false;
    }

    public boolean xoaPhieuTra(int maID) {
        ArrayList<ChiTietTraNhaCungCapDTO> listCT = ctBUS.getListCTPTN(maID);
        for(ChiTietTraNhaCungCapDTO ct : listCT) {
            sachDAO.congTonKho(ct.getMaSach(), ct.getSoLuong()); 
            
            LichSuKhoDTO lsk = new LichSuKhoDTO();
            lsk.setMaSachID(ct.getMaSach()); // ĐÃ SỬA THÀNH setMaSachID
            lsk.setLoaiGiaoDich("TRA_NCC"); // ĐÃ SỬA THÀNH STRING
            lsk.setMaChungTu(maID);
            lsk.setSoLuongThayDoi(ct.getSoLuong()); 
            lsk.setGhiChu("Hủy yêu cầu trả hàng NCC");
            
            lskDAO.insert(lsk);
        }
        boolean kq = ptnDAO.delete(maID);
        if(kq) docDanhSach();
        return kq;
    }
}