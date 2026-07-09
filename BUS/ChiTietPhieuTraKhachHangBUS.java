package BUS;

import DAO.ChiTietPhieuTraKhachHangDAO;
import DTO.ChiTietPhieuTraKhachHangDTO;
import java.util.ArrayList;

public class ChiTietPhieuTraKhachHangBUS {
    private ChiTietPhieuTraKhachHangDAO ctkhDAO = new ChiTietPhieuTraKhachHangDAO();

    public long tinhThanhTien(int sl, long gia) {
        return (long) sl * gia;
    }

    public String validateDetail(int sl, long gia) {
        if (sl <= 0) return "SỐ lượng phải lớn hơn 0";
        if (gia < 0) return "GIá hoàn không được âm";
        return "OK";
    }

    public ArrayList<ChiTietPhieuTraKhachHangDTO> getByMaPTKH(int maPTKH) {
        return ctkhDAO.getByMaPTKH(maPTKH);
    }

    public boolean addDetail(ChiTietPhieuTraKhachHangDTO ct) {
        return ctkhDAO.insert(ct);
    }
}