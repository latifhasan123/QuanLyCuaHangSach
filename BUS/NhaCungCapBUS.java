package BUS;

import DAO.NhaCungCapDAO;
import DTO.NhaCungCapDTO;
import java.util.ArrayList;

public class NhaCungCapBUS {
    private NhaCungCapDAO nccDAO = new NhaCungCapDAO();
    private ArrayList<NhaCungCapDTO> listNCC = new ArrayList<>();

    public NhaCungCapBUS() {
        docDanhSach(); // Tự động lấy dữ liệu từ DB lên khi gọi lớp này
    }

    // Hàm lấy dữ liệu từ DAO bỏ vào danh sách
    public void docDanhSach() {
        this.listNCC = nccDAO.selectAll();
    }

    // Trả về danh sách hiện tại
    public ArrayList<NhaCungCapDTO> getListNCC() {
        return this.listNCC;
    }

    public boolean themNCC(NhaCungCapDTO ncc) {
        if (nccDAO.insert(ncc)) {
            docDanhSach(); // Cập nhật lại danh sách ngay sau khi thêm thành công
            return true;
        }
        return false;
    }

    public boolean suaNCC(NhaCungCapDTO ncc) {
        if (nccDAO.update(ncc)) {
            docDanhSach(); 
            return true;
        }
        return false;
    }

    public boolean xoaNCC(int maID) {
        if (nccDAO.delete(maID)) {
            docDanhSach(); 
            return true;
        }
        return false;
    }

    // ========================================================
    // TÍNH NĂNG TÌM KIẾM NÂNG CAO (GÕ TỚI ĐÂU LỌC TỚI ĐÓ)
    // ========================================================
    public ArrayList<NhaCungCapDTO> timKiem(String keyword) {
        ArrayList<NhaCungCapDTO> result = new ArrayList<>();
        String lowerKeyword = keyword.toLowerCase().trim();
        
        for (NhaCungCapDTO ncc : listNCC) {
            // Kiểm tra xem từ khóa có nằm trong Mã, Tên hoặc SĐT không
            if (ncc.getMaNCC().toLowerCase().contains(lowerKeyword) ||
                ncc.getTenNCC().toLowerCase().contains(lowerKeyword) ||
                (ncc.getSoDienThoai() != null && ncc.getSoDienThoai().toLowerCase().contains(lowerKeyword))) {
                
                // Nếu có, đưa vào danh sách kết quả
                result.add(ncc);
            }
        }
        return result;
    }
}