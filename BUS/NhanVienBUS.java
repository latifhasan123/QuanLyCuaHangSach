package BUS;

import DAO.NhanVienDAO;
import DTO.NhanVienDTO;
import DAO.TaiKhoanNhanVienDAO;
import DTO.TaiKhoanNhanVienDTO; 
import java.util.ArrayList;

public class NhanVienBUS {
    private NhanVienDAO nvDAO = new NhanVienDAO();

    public ArrayList<NhanVienDTO> getDanhSach() {
        return nvDAO.selectAll();
    }

    public String themNhanVien(NhanVienDTO nv) {
        if (nv.getHoTen().trim().isEmpty() || nv.getSoDienThoai().trim().isEmpty() || nv.getEmail().trim().isEmpty()) {
            return "Vui lòng nhập đầy đủ Họ tên, Số điện thoại và Email!";
        }
        if (!nv.getSoDienThoai().matches("\\d{10}")) {
            return "Số điện thoại không hợp lệ (Phải là 10 chữ số)!";
        }
        if (!nv.getEmail().matches("^[a-zA-Z0-9._%+-]+@gmail\\.com$")) {
            return "Email không hợp lệ! Vui lòng nhập đúng định dạng (VD: tenban@gmail.com).";
        }
        
        if (nv.getMaTaiKhoanNV() == -1) {
            return "TỪ CHỐI: Vui lòng chọn tài khoản (Hoặc hệ thống đã hết tài khoản trống)!";
        }


        for (NhanVienDTO nvCu : nvDAO.selectAll()) {
            if (nvCu.getMaTaiKhoanNV() == nv.getMaTaiKhoanNV()) {
                return "TỪ CHỐI: Tài khoản này đã được cấp cho một nhân viên khác!";
            }
        }

        TaiKhoanNhanVienDAO tkDAO = new TaiKhoanNhanVienDAO();
        for (TaiKhoanNhanVienDTO tk : tkDAO.selectAll()) {
            if (tk.getMaID() == nv.getMaTaiKhoanNV() && tk.getMaQuyen() == 1) {
                return "TỪ CHỐI BẢO MẬT: Không được phép gán tài khoản Quản Trị Viên cho nhân viên mới!";
            }
        }

        if (nvDAO.insert(nv)) {
            return "Thêm nhân viên thành công!";
        } else {
            return "Lỗi CSDL! (Trùng Email/SĐT)";
        }
    }

    public String suaNhanVien(NhanVienDTO nv) {
        if (nv.getHoTen().trim().isEmpty() || nv.getEmail().trim().isEmpty()) {
            return "Họ tên và Email không được để trống!";
        }
        if (!nv.getEmail().matches("^[a-zA-Z0-9._%+-]+@gmail\\.com$")) {
            return "Email không hợp lệ! Vui lòng nhập đúng định dạng (VD: tenban@gmail.com).";
        }
        

        if (nv.getMaTaiKhoanNV() == -1) {
            return "TỪ CHỐI: Vui lòng chọn tài khoản hợp lệ!";
        }


        for (NhanVienDTO nvCu : nvDAO.selectAll()) {
            if (nvCu.getMaTaiKhoanNV() == nv.getMaTaiKhoanNV() && nvCu.getMaID() != nv.getMaID()) {
                return "TỪ CHỐI: Tài khoản này đã thuộc sở hữu của nhân viên khác!";
            }
        }

        if (nvDAO.update(nv)) {
            return "Cập nhật thông tin thành công!";
        } else {
            return "Lỗi cập nhật CSDL!";
        }
    }

    public String xoaNhanVien(int maID) {
        // --- ĐÃ THÊM: CHẶN XÓA ADMIN ---
        for (NhanVienDTO nv : nvDAO.selectAll()) {
            if (nv.getMaID() == maID) {
                // Giả sử 1 là mã Chức vụ của Quản trị viên
                if (nv.getMaChucVu() == 1) {
                    return "TỪ CHỐI BẢO MẬT: Không được phép xóa Quản Trị Viên hệ thống!";
                }
            }
        }
        // -------------------------------

        if (nvDAO.delete(maID)) {
            return "Đã xóa nhân viên khỏi hệ thống!";
        } else {
            return "Lỗi: Không thể xóa (Ràng buộc dữ liệu)!";
        }
    }

    public ArrayList<TaiKhoanNhanVienDTO> getDanhSachTaiKhoanRanh(String maTKNV_NgoaiLe) {
        TaiKhoanNhanVienDAO tkDAO = new TaiKhoanNhanVienDAO();
        ArrayList<TaiKhoanNhanVienDTO> tatCaTaiKhoan = tkDAO.selectAll();
        ArrayList<TaiKhoanNhanVienDTO> dsTaiKhoanRanh = new ArrayList<>();
        
        ArrayList<Integer> dsDaCoChu = new ArrayList<>();
        for (NhanVienDTO nv : nvDAO.selectAll()) {
            dsDaCoChu.add(nv.getMaTaiKhoanNV());
        }
        
        for (TaiKhoanNhanVienDTO tk : tatCaTaiKhoan) {
            if (tk.getMaQuyen() == 1 && !tk.getMaTKNV().equals(maTKNV_NgoaiLe)) {
                continue;
            }
            if (!dsDaCoChu.contains(tk.getMaID()) || tk.getMaTKNV().equals(maTKNV_NgoaiLe)) {
                dsTaiKhoanRanh.add(tk);
            }
        }
        return dsTaiKhoanRanh;
    }

    public int dichChucVuSangSo(String tenCV) {
        if(tenCV.equalsIgnoreCase("Quản trị")) return 1;
        if(tenCV.equalsIgnoreCase("Nhân viên bán hàng")) return 2;
        if(tenCV.equalsIgnoreCase("Thủ kho")) return 3;
        if(tenCV.equalsIgnoreCase("Nhân viên nhập hàng")) return 4;
        return 0; 
    }

    public int dichTaiKhoanSangSo(String chuoiHienThi) {
        if (chuoiHienThi == null || !chuoiHienThi.contains(" - ")) {
            return -1;
        }
        
        String tenDangNhap = chuoiHienThi.split(" - ")[1]; 
        
        TaiKhoanNhanVienDAO tkDAO = new TaiKhoanNhanVienDAO();
        for (TaiKhoanNhanVienDTO tk : tkDAO.selectAll()) {
            if (tk.getTenDangNhap().equals(tenDangNhap)) {
                return tk.getMaID(); 
            }
        }
        return -1; 
    }
    public ArrayList<NhanVienDTO> timKiemCoBan(String tuKhoa) {
        ArrayList<NhanVienDTO> ketQua = new ArrayList<>();
        tuKhoa = tuKhoa.toLowerCase().trim();
        
        for (NhanVienDTO nv : getDanhSach()) {
            String maCV = nv.getMaChucVu_Chu() != null ? nv.getMaChucVu_Chu().toLowerCase() : "";
            String maTK = nv.getMaTaiKhoan_Chu() != null ? nv.getMaTaiKhoan_Chu().toLowerCase() : "";

            if (nv.getMaNV().toLowerCase().contains(tuKhoa) ||
                nv.getHoTen().toLowerCase().contains(tuKhoa) ||
                nv.getSoDienThoai().contains(tuKhoa) ||
                nv.getEmail().toLowerCase().contains(tuKhoa) ||
                maCV.contains(tuKhoa) ||
                maTK.contains(tuKhoa)) {
                ketQua.add(nv);
            }
        }
        return ketQua;
    }

    public ArrayList<NhanVienDTO> timKiemNangCao(String maNV, String tenNV, String sdt, String email, String chucVu, String taiKhoan) {
        ArrayList<NhanVienDTO> ketQua = new ArrayList<>();
        maNV = maNV.toLowerCase().trim();
        tenNV = tenNV.toLowerCase().trim();
        sdt = sdt.trim();
        email = email.toLowerCase().trim();
        chucVu = chucVu.toLowerCase().trim();
        taiKhoan = taiKhoan.toLowerCase().trim();

        for (NhanVienDTO nv : getDanhSach()) {
            String cvChu = nv.getMaChucVu_Chu() != null ? nv.getMaChucVu_Chu().toLowerCase() : "";
            String tkChu = nv.getMaTaiKhoan_Chu() != null ? nv.getMaTaiKhoan_Chu().toLowerCase() : "";
            boolean matchMa = nv.getMaNV().toLowerCase().contains(maNV);
            boolean matchTen = nv.getHoTen().toLowerCase().contains(tenNV);
            boolean matchSdt = nv.getSoDienThoai().contains(sdt);
            boolean matchEmail = nv.getEmail().toLowerCase().contains(email);
            boolean matchCV = cvChu.contains(chucVu);
            boolean matchTK = tkChu.contains(taiKhoan);
            if (matchMa && matchTen && matchSdt && matchEmail && matchCV && matchTK) {
                ketQua.add(nv);
            }
        }
        return ketQua;
    }
}