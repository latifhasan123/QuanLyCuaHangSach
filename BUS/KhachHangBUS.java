package BUS;

import DAO.KhachHangDAO;
import DTO.KhachHangDTO;
import java.util.ArrayList;

public class KhachHangBUS {
    private KhachHangDAO khDAO = new KhachHangDAO();
    private ArrayList<KhachHangDTO> dsKhachHang = new ArrayList<>();
    // Biến toàn cục lưu thông tin khách hàng đang đăng nhập trên Web/App
    public static DTO.KhachHangDTO currentCustomer = null;
    // Lấy danh sách để đẩy lên GUI
    public ArrayList<KhachHangDTO> getAllKhachHang() {
        dsKhachHang = khDAO.selectAll();
        return dsKhachHang;
    }
    public KhachHangDTO getKhachHangByPhone(String sdt) {
        getAllKhachHang(); // Load lại list mới nhất
        for (KhachHangDTO kh : dsKhachHang) {
            if (kh.getSoDienThoai().equals(sdt)) {
                return kh;
            }
        }
        return null;
    }

    // 1. TÌM KIẾM CƠ BẢN (Có chọn tiêu chí từ ComboBox)
    public ArrayList<KhachHangDTO> searchBasic(String tieuChi, String keyword) {
        ArrayList<KhachHangDTO> result = new ArrayList<>();
        String lowerKw = keyword.toLowerCase().trim();
        
        for (KhachHangDTO kh : dsKhachHang) {
            boolean match = false;
            switch (tieuChi) {
                case "Tất cả":
                    match = kh.getMaKH().toLowerCase().contains(lowerKw) || 
                            kh.getHoTen().toLowerCase().contains(lowerKw) || 
                            kh.getSoDienThoai().contains(lowerKw);
                    break;
                case "Mã KH":
                    match = kh.getMaKH().toLowerCase().contains(lowerKw);
                    break;
                case "Tên KH":
                    match = kh.getHoTen().toLowerCase().contains(lowerKw);
                    break;
                case "Số Điện Thoại":
                    match = kh.getSoDienThoai().contains(lowerKw);
                    break;
            }
            if (match) result.add(kh);
        }
        return result;
    }

    // 2. LỌC NÂNG CAO (Theo khoảng Điểm tích lũy)
    public ArrayList<KhachHangDTO> searchAdvanced(String tuDiemStr, String denDiemStr) {
        ArrayList<KhachHangDTO> result = new ArrayList<>();
        int tuDiem = 0;
        int denDiem = Integer.MAX_VALUE;

        try {
            if (!tuDiemStr.trim().isEmpty()) tuDiem = Integer.parseInt(tuDiemStr.trim());
            if (!denDiemStr.trim().isEmpty()) denDiem = Integer.parseInt(denDiemStr.trim());
        } catch (NumberFormatException e) {
            return result; // Nếu nhập bậy chữ cái vào ô số thì trả về rỗng
        }

        for (KhachHangDTO kh : dsKhachHang) {
            if (kh.getDiemTichLuy() >= tuDiem && kh.getDiemTichLuy() <= denDiem) {
                result.add(kh);
            }
        }
        return result;
    }

    // =======================================================
    // NGHIỆP VỤ THÊM KHÁCH HÀNG (ĐÃ FIX KIỂM TRA TOÀN DIỆN)
    // =======================================================
    public String addKhachHang(KhachHangDTO kh) {
        // 1. Kiểm tra rỗng (Họ tên và SĐT là bắt buộc)
        if (kh.getHoTen().trim().isEmpty() || kh.getSoDienThoai().trim().isEmpty()) {
            return "Họ Tên và Số điện thoại không được để trống!";
        }
        
        // 2. Kiểm tra Số Điện Thoại
        if (!kh.getSoDienThoai().matches("\\d+")) {
            return "Số điện thoại chỉ được chứa các chữ số!";
        }
        if (!kh.getSoDienThoai().matches("0\\d{9}")) {
            return "Số điện thoại phải bắt đầu bằng số 0 và có đúng 10 chữ số!";
        }
        
        // 3. Kiểm tra Email (Khách có thể không nhập, nhưng nếu nhập thì phải chuẩn)
        if (!kh.getEmail().trim().isEmpty()) {
            if (!kh.getEmail().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")) {
                return "Email không hợp lệ! Vui lòng nhập đúng định dạng (VD: example@gmail.com).";
            }
        }
        
        // 4. Kiểm tra trùng lặp trong hệ thống
        for (KhachHangDTO k : dsKhachHang) {
            if (k.getSoDienThoai().equals(kh.getSoDienThoai())) {
                return "Số điện thoại này đã tồn tại trong hệ thống!";
            }
            if (!kh.getEmail().trim().isEmpty() && k.getEmail() != null && k.getEmail().equals(kh.getEmail())) {
                return "Email này đã được sử dụng bởi khách hàng khác!";
            }
        }

        // 5. Thêm vào DB
        if (khDAO.insert(kh)) {
            return "Thêm khách hàng thành công!";
        }
        return "Thêm thất bại. Vui lòng kiểm tra lại CSDL!";
    }

    // =======================================================
    // NGHIỆP VỤ SỬA KHÁCH HÀNG (ĐÃ FIX KIỂM TRA TRÙNG CHÉO)
    // =======================================================
    public String updateKhachHang(KhachHangDTO kh) {
        // 1. Kiểm tra rỗng
        if (kh.getHoTen().trim().isEmpty() || kh.getSoDienThoai().trim().isEmpty()) {
            return "Họ Tên và Số điện thoại không được để trống!";
        }
        
        // 2. Kiểm tra Số Điện Thoại
        if (!kh.getSoDienThoai().matches("\\d+")) {
            return "Số điện thoại chỉ được chứa các chữ số!";
        }
        if (!kh.getSoDienThoai().matches("0\\d{9}")) {
            return "Số điện thoại phải bắt đầu bằng số 0 và có đúng 10 chữ số!";
        }
        
        // 3. Kiểm tra Email
        if (!kh.getEmail().trim().isEmpty()) {
            if (!kh.getEmail().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")) {
                return "Email không hợp lệ! Vui lòng nhập đúng định dạng (VD: example@gmail.com).";
            }
        }
        
        // 4. Kiểm tra trùng lặp (Bỏ qua chính người đang được sửa)
        for (KhachHangDTO k : dsKhachHang) {
            if (k.getMaID() != kh.getMaID()) { // Nếu không phải là chính nó
                if (k.getSoDienThoai().equals(kh.getSoDienThoai())) {
                    return "Số điện thoại này đang thuộc về một khách hàng khác!";
                }
                if (!kh.getEmail().trim().isEmpty() && k.getEmail() != null && k.getEmail().equals(kh.getEmail())) {
                    return "Email này đã được sử dụng bởi một khách hàng khác!";
                }
            }
        }
        
        // 5. Update DB
        if (khDAO.update(kh)) {
            return "Cập nhật thông tin thành công!";
        }
        return "Cập nhật thất bại!";
    }

    // Nghiệp vụ Xóa
    public String deleteKhachHang(int maID) {
        if (khDAO.delete(maID)) {
            return "Đã chuyển khách hàng sang trạng thái Ngừng Hoạt Động!";
        }
        return "Xóa thất bại!";
    }
    // =======================================================
    // NGHIỆP VỤ ĐĂNG NHẬP / ĐĂNG KÝ (ONLINE)
    // =======================================================
    
    // 1. Kiểm tra tồn tại Email/SĐT
    public boolean isExists(String email, String sdt) {
        for (KhachHangDTO kh : getAllKhachHang()) {
            if (kh.getSoDienThoai().equals(sdt)) return true;
            if (!email.isEmpty() && kh.getEmail() != null && kh.getEmail().equals(email)) return true;
        }
        return false;
    }

    // 2. Xử lý Đăng Nhập
    public KhachHangDTO dangNhapTraVeDTO(String user, String pass) {
        // Lưu ý: Trong file KhachHangDAO Sếp phải viết sẵn một hàm tên là dangNhap(user, pass) 
        // để chọc xuống Database kiểm tra mật khẩu nhé!
        return khDAO.dangNhap(user, pass); 
    }

    // 3. Xử lý Đăng Ký
    public boolean dangKy(String hoTen, String email, String sdt, String pass) {
        if (isExists(email, sdt)) return false;
        
        // Lưu ý: Tương tự, trong KhachHangDAO cũng cần hàm dangKy() để INSERT dữ liệu và mật khẩu
        return khDAO.dangKy(hoTen, email, sdt, pass); 
    }
    // =======================================================
    // CẬP NHẬT ĐIỂM VÀ ĐỊA CHỈ SAU KHI ĐẶT HÀNG ONLINE
    // =======================================================
    public void capNhatThongTinSauDatHang(int maID, String diaChiMoi, int diemCongThem) {
        String sql = "UPDATE KhachHang SET DiaChi = ?, DiemTichLuy = DiemTichLuy + ? WHERE MaID = ?";
        try (java.sql.Connection con = Utils.JDBCConnection.getConnection();
             java.sql.PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, diaChiMoi);
            pst.setInt(2, diemCongThem);
            pst.setInt(3, maID);
            pst.executeUpdate();
            
            // Cập nhật luôn điểm và địa chỉ cho biến toàn cục đang lưu trên RAM
            if (currentCustomer != null && currentCustomer.getMaID() == maID) {
                currentCustomer.setDiaChi(diaChiMoi);
                currentCustomer.setDiemTichLuy(currentCustomer.getDiemTichLuy() + diemCongThem);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}