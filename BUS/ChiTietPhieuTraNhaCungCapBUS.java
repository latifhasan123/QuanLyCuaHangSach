package BUS;

import DAO.ChiTietPhieuTraNhaCungCapDAO;
import DTO.ChiTietPhieuTraNhaCungCapDTO;
import java.util.ArrayList;

public class ChiTietPhieuTraNhaCungCapBUS {
    private ChiTietPhieuTraNhaCungCapDAO ctptDAO = new ChiTietPhieuTraNhaCungCapDAO();

    public ArrayList<ChiTietPhieuTraNhaCungCapDTO> getListByMaPT(int maPT) {
        return ctptDAO.getByMaPT(maPT);
    }

    public String validateReturnDetail(int soLuongTra, String lyDo) {
        if (soLuongTra <= 0) {
            return "Số lượng trả phải lớn hơn 0";
        }
        if (lyDo == null || lyDo.trim().isEmpty()) {
            return "Vui lòng nhập lý do trả hàng";
        }
        return "OK";
    }

    public long tinhTienHoan(int soLuong, long donGia) {
        return (long) soLuong * donGia;
    }
}