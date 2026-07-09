package BUS;

import DAO.TaiKhoanKhachHangDAO;
import DTO.TaiKhoanKhachHangDTO;
import java.util.ArrayList;

public class TaiKhoanKhachHangBUS {
    private TaiKhoanKhachHangDAO dao = new TaiKhoanKhachHangDAO();
    private ArrayList<TaiKhoanKhachHangDTO> dsTaiKhoan = new ArrayList<>();

    public ArrayList<TaiKhoanKhachHangDTO> getAll() {
        dsTaiKhoan = dao.selectAll();
        return dsTaiKhoan;
    }

    // Lọc theo Username (Tìm kiếm)
    public ArrayList<TaiKhoanKhachHangDTO> search(String keyword) {
        ArrayList<TaiKhoanKhachHangDTO> result = new ArrayList<>();
        String lowerKw = keyword.toLowerCase().trim();
        for (TaiKhoanKhachHangDTO tk : dsTaiKhoan) {
            if (tk.getTenDangNhap().toLowerCase().contains(lowerKw)) {
                result.add(tk);
            }
        }
        return result;
    }

    // Đảo trạng thái (Đang Hoạt Động -> Khóa, và ngược lại)
    public String toggleStatus(TaiKhoanKhachHangDTO tk) {
        String newStatus = tk.getTrangThai().equals("HoatDong") ? "Khoa" : "HoatDong";
        if (dao.updateStatus(tk.getMaID(), newStatus)) {
            return "Đã chuyển tài khoản sang trạng thái: " + newStatus;
        }
        return "Cập nhật trạng thái thất bại!";
    }

    // Reset mật khẩu về "123456"
    public String resetPass(int maID) {
        // Trong thực tế nên mã hóa MD5/SHA-256 đoạn "123456" này trước khi đưa xuống DAO
        if (dao.resetPassword(maID, "123456")) { 
            return "Mật khẩu đã được đặt lại thành công (Mặc định: 123456)";
        }
        return "Reset mật khẩu thất bại!";
    }
}