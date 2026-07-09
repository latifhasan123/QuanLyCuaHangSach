package BUS;

import DAO.SachDAO;
import DTO.SachDTO;
import java.util.List;

public class SachBUS {

    private SachDAO sachDAO = new SachDAO();

    public List<SachDTO> getAllSach() {
        return sachDAO.getAll();
    }

    public boolean addSach(SachDTO s){
        if(s.getTenSach() == null || s.getTenSach().trim().isEmpty()){
            return false;
        }
        if(s.getIsbn() == null || s.getIsbn().trim().isEmpty()){
            return false;
        }
        if(s.getGiaBan() < 0){
            return false;
        }
        return sachDAO.insert(s);
    }

    public boolean updateSach(SachDTO s){
        if(s.getMaID() <= 0){
            return false;
        }
        return sachDAO.update(s);
    }

    // ĐÃ SỬA: Gọi đúng hàm deleteSach (Xóa mềm - tàng hình) bên DAO
    public boolean deleteSach(int id){
        return sachDAO.deleteSach(id);
    }
    
    // ĐÃ THÊM: Hàm ngừng bán để giao diện gọi (Đổi trạng thái thành NgungBan)
    public boolean ngungBanSach(int id){
        return sachDAO.ngungBanSach(id);
    }
    
    public SachDTO getSachByID(int id){
        return sachDAO.findByID(id);
    }

    public boolean checkTrungISBN(String isbn, int maIDHienTai) {
        return sachDAO.checkTrungISBN(isbn, maIDHienTai);
    }
}