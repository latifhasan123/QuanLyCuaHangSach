package BUS; // ĐÃ SỬA: Viết hoa

import DAO.TacGiaDAO; // ĐÃ SỬA: Viết hoa
import DTO.TacGiaDTO; // ĐÃ SỬA: Viết hoa
import java.util.List;

public class TacGiaBUS {

    private TacGiaDAO dao = new TacGiaDAO();

    public List<TacGiaDTO> getAllTacGia(){
        return dao.getAll();
    }
    
    public boolean addTacGia(TacGiaDTO tg){
        return dao.insertTacGia(tg);
    }
    public boolean updateTacGia(TacGiaDTO tg){
        return dao.updateTacGia(tg);
    }
    // ================= HÀM XÓA TÁC GIẢ =================
    public boolean deleteTacGia(String maTG) {
        return dao.deleteTacGia(maTG);
    }

}