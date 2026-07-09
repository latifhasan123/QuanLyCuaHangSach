package BUS;

import DAO.ChiTietPhieuNhapDAO;
import DTO.ChiTietPhieuNhapDTO;
import java.util.ArrayList;

public class ChiTietPhieuNhapBUS {
    private ChiTietPhieuNhapDAO ctpnDAO = new ChiTietPhieuNhapDAO();

    public ChiTietPhieuNhapBUS() {
    }

    public ArrayList<ChiTietPhieuNhapDTO> getListCTPN(String maPN) {
        return ctpnDAO.selectAll(maPN);
    }

    public boolean themCTPN(ChiTietPhieuNhapDTO ctpn) {
        return ctpnDAO.insert(ctpn);
    }
}