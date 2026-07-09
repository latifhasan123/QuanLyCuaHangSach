package BUS;

import DAO.ThongKeDAO;
import DTO.ThongKeDTO;
import java.time.LocalDate;
import java.util.ArrayList;
import DTO.TopSachDTO;

public class ThongKeBUS {
    private ThongKeDAO tkDAO = new ThongKeDAO();

    // Hàm gọi lấy dữ liệu từ DAO đưa lên GUI
    public ArrayList<ThongKeDTO> getDoanhThuTheoNgay(String tuNgay, String denNgay) {
        
        // 1. KIỂM TRA LOGIC NGÀY THÁNG TRƯỚC KHI XUỐNG DB
        try {
            LocalDate dateTu = LocalDate.parse(tuNgay);
            LocalDate dateDen = LocalDate.parse(denNgay);
            
            if (dateTu.isAfter(dateDen)) {
                System.out.println("Lỗi logic: Ngày bắt đầu không được lớn hơn ngày kết thúc!");
                return new ArrayList<>(); // Trả về mảng rỗng để GUI không bị sập
            }
        } catch (Exception e) {
            System.out.println("Định dạng ngày không hợp lệ (Cần yyyy-MM-dd)!");
            return new ArrayList<>();
        }
        
        // 2. NẾU HỢP LỆ, GỌI DAO ĐỂ XUỐNG DB LẤY TIỀN VỀ
        return tkDAO.getDoanhThuTheoNgay(tuNgay, denNgay);
    }
    public ArrayList<TopSachDTO> getTopSachBanChay(String tuNgay, String denNgay) {
        try {
            LocalDate dateTu = LocalDate.parse(tuNgay);
            LocalDate dateDen = LocalDate.parse(denNgay);
            if (dateTu.isAfter(dateDen)) return new ArrayList<>();
        } catch (Exception e) {
            return new ArrayList<>();
        }
        return tkDAO.getTopSachBanChay(tuNgay, denNgay);
    }
}