package Utils;

import DTO.PhanQuyenDTO;
import DTO.TaiKhoanNhanVienDTO;

public class SharedData {
    // Lưu tài khoản đang đăng nhập
    public static TaiKhoanNhanVienDTO currentUser;
    // Lưu chi tiết 14 quyền của tài khoản đó
    public static PhanQuyenDTO userPermission;
}