package BUS;

import DAO.TonKhoDAO;
import DTO.TonKhoDTO;
import java.util.ArrayList;

public class TonKhoBUS {
    private TonKhoDAO dao = new TonKhoDAO();
    
    public ArrayList<TonKhoDTO> getDanhSach() {
        return dao.selectAll();
    }
    
    public String kiemKe(int maSachID, int slHeThong, String slThucTeStr, String lyDo) {
        if (lyDo == null || lyDo.trim().isEmpty()) {
            return "Vui lòng nhập lý do kiểm kê (VD: Hư hỏng, thất lạc, đếm sai...)!";
        }
        
        int slThucTe;
        try {
            slThucTe = Integer.parseInt(slThucTeStr);
            if(slThucTe < 0) return "Số lượng thực tế không được âm!";
        } catch (Exception e) {
            return "Số lượng thực tế phải là một con số!";
        }
        
        if (slHeThong == slThucTe) {
            return "Số lượng thực tế đang KHỚP với hệ thống. Không cần kiểm kê!";
        }
        
        // Cố gắng lấy ID nhân viên đang đăng nhập (nếu Sếp có làm class SharedData)
        int maNV = 0; 
        try {
            // maNV = Utils.SharedData.currentUser.getMaID(); // Bỏ comment nếu Sếp có class này
        } catch (Exception e) {}
        
        boolean ok = dao.capNhatKiemKe(maSachID, slHeThong, slThucTe, lyDo, maNV);
        return ok ? "Kiểm kê và cập nhật tồn kho thành công!" : "Lỗi kết nối CSDL khi kiểm kê!";
    }
}