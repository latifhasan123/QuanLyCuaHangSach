package BUS;

import DAO.TaiKhoanNhanVienDAO;
import DTO.TaiKhoanNhanVienDTO;
import java.util.ArrayList;

public class TaiKhoanNhanVienBUS {
    private TaiKhoanNhanVienDAO tkDAO = new TaiKhoanNhanVienDAO();

    public ArrayList<TaiKhoanNhanVienDTO> getDanhSach() {
        return tkDAO.selectAll();
    }

    public String themTaiKhoan(TaiKhoanNhanVienDTO tk) {
        if (tk.getTenDangNhap().trim().isEmpty() || tk.getMatKhau().trim().isEmpty()) {
            return "Tên đăng nhập và Mật khẩu không được để trống!";
        }
        if (tk.getMatKhau().length() < 6) {
            return "Mật khẩu phải có ít nhất 6 ký tự!";
        }
        if (!tk.getTenDangNhap().matches("^[a-zA-Z0-9_]+$")) {
            return "Tên đăng nhập chỉ được chứa chữ cái, số và dấu gạch dưới!";
        }

        // =========================================================
        // LUẬT THÉP 1: NGĂN CHẶN ĐẺ THÊM ADMIN
        // =========================================================
        if (tk.getMaQuyen() == 1) { // 1 là mã của Quản trị hệ thống
            return "TỪ CHỐI: Hệ thống chỉ được phép tồn tại 1 Quản trị viên duy nhất!";
        }

        if (tkDAO.insert(tk)) {
            return "Tạo tài khoản thành công!";
        } else {
            return "Lỗi CSDL! (Có thể Tên Đăng Nhập này đã tồn tại)";
        }
    }

    public String suaTaiKhoan(TaiKhoanNhanVienDTO tk) {
        if (tk.getMatKhau().trim().isEmpty()) {
            return "Mật khẩu không được để trống!";
        }
        if (tk.getMatKhau().length() < 6) {
            return "Mật khẩu phải có ít nhất 6 ký tự!";
        }

        // =========================================================
        // LUẬT THÉP 2: BẢO VỆ ADMIN TỐI CAO (Giả sử Admin gốc có MaID = 1)
        // =========================================================
        if (tk.getMaID() == 1) {
            if (tk.getMaQuyen() != 1) return "TỪ CHỐI: Không được phép giáng chức của Admin tối cao!";
            if (tk.getTrangThai().equals("Khoa")) return "TỪ CHỐI: Không được phép tự khóa tài khoản Admin!";
        } else {
            // Nếu không phải là Admin gốc (MaID khác 1) mà dám chọn quyền Quản trị hệ thống
            if (tk.getMaQuyen() == 1) {
                return "TỪ CHỐI: Không được phép thăng quyền nhân viên lên làm Admin!";
            }
        }
        
        if (tkDAO.update(tk)) {
            return "Cập nhật tài khoản thành công!";
        } else {
            return "Lỗi cập nhật CSDL!";
        }
    }

    public String xoaTaiKhoan(int maID) {
        // =========================================================
        // LUẬT THÉP 3: MIỄN TỬ KIM BÀI CHO ADMIN
        // =========================================================
        if (maID == 1) {
            return "TỪ CHỐI: Không thể xóa tài khoản Quản trị hệ thống gốc!";
        }

        if (tkDAO.delete(maID)) {
            return "Đã xóa tài khoản!";
        } else {
            return "Không thể xóa! (Tài khoản này đang được sử dụng bởi một Nhân Viên)";
        }
    }
    // ======================================================
    // BỘ PHIÊN DỊCH CHO GIAO DIỆN (GUI)
    // ================================
    // ======================================================
    // 1. TÌM KIẾM CƠ BẢN (Quét mọi ngóc ngách)
    // ======================================================
    public ArrayList<TaiKhoanNhanVienDTO> timKiemCoBan(String tuKhoa) {
        ArrayList<TaiKhoanNhanVienDTO> ketQua = new ArrayList<>();
        tuKhoa = tuKhoa.toLowerCase().trim();

        for (TaiKhoanNhanVienDTO tk : getDanhSach()) {
            // Lấy dữ liệu dạng chuỗi (Xử lý null để không văng app)
            String maTK = tk.getMaTKNV() != null ? tk.getMaTKNV().toLowerCase() : "";
            String tenDN = tk.getTenDangNhap() != null ? tk.getTenDangNhap().toLowerCase() : "";
            String quyen = dichSoSangQuyen(tk.getMaQuyen()).toLowerCase();
            String trangThai = tk.getTrangThai() != null ? tk.getTrangThai().toLowerCase() : "";

            if (maTK.contains(tuKhoa) || tenDN.contains(tuKhoa) || 
                quyen.contains(tuKhoa) || trangThai.contains(tuKhoa)) {
                ketQua.add(tk);
            }
        }
        return ketQua;
    }

    // ======================================================
    // 2. TÌM KIẾM NÂNG CAO (Lọc 4 tiêu chí)
    // ======================================================
    public ArrayList<TaiKhoanNhanVienDTO> timKiemNangCao(String maTK, String tenDN, String quyen, String trangThai) {
        ArrayList<TaiKhoanNhanVienDTO> ketQua = new ArrayList<>();
        maTK = maTK.toLowerCase().trim();
        tenDN = tenDN.toLowerCase().trim();
        quyen = quyen.toLowerCase().trim();
        trangThai = trangThai.toLowerCase().trim();

        for (TaiKhoanNhanVienDTO tk : getDanhSach()) {
            String m = tk.getMaTKNV() != null ? tk.getMaTKNV().toLowerCase() : "";
            String t = tk.getTenDangNhap() != null ? tk.getTenDangNhap().toLowerCase() : "";
            String q = dichSoSangQuyen(tk.getMaQuyen()).toLowerCase();
            String tt = tk.getTrangThai() != null ? tk.getTrangThai().toLowerCase() : "";

            // Phải khớp tất cả các ô mới lấy
            if (m.contains(maTK) && t.contains(tenDN) && q.contains(quyen) && tt.contains(trangThai)) {
                ketQua.add(tk);
            }
        }
        return ketQua;
    }

    // ======================================================
    // Hàm phụ trợ: Dịch số sang Quyền để tìm kiếm bằng chữ
    // ======================================================
    // ======================================================
    // BỘ PHIÊN DỊCH CHO GIAO DIỆN (CHUẨN 4 QUYỀN CỦA SẾP)
    // ======================================================
    public int dichQuyenSangSo(String tenQuyen) {
        if (tenQuyen.equalsIgnoreCase("Quản trị viên")) return 1;
        if (tenQuyen.equalsIgnoreCase("Nhân viên bán hàng")) return 2;
        if (tenQuyen.equalsIgnoreCase("Thủ kho")) return 3;
        if (tenQuyen.equalsIgnoreCase("Nhân viên nhập hàng")) return 4;
        return 2; // Mặc định cấp quyền bán hàng cho an toàn
    }

    public String dichSoSangQuyen(int maQuyen) {
        if (maQuyen == 1) return "Quản trị viên";
        if (maQuyen == 2) return "Nhân viên bán hàng";
        if (maQuyen == 3) return "Thủ kho";
        if (maQuyen == 4) return "Nhân viên nhập hàng";
        return "";
    }
}