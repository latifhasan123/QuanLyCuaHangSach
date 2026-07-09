package BUS; 

import DAO.TheLoaiDAO; 
import DTO.TheLoaiDTO; 
import java.util.List;

public class TheLoaiBUS {

    private TheLoaiDAO dao = new TheLoaiDAO();

    public List<TheLoaiDTO> getAllTheLoai(){
        return dao.getAll();
    }
    
    public boolean addTheLoai(TheLoaiDTO tl){
        return dao.insertTheLoai(tl);
    }

    // ĐÃ BỔ SUNG HÀM CẬP NHẬT (SỬA) THỂ LOẠI
    public boolean updateTheLoai(TheLoaiDTO tl){
        return dao.updateTheLoai(tl); 
    }
    public boolean deleteTheLoai(String maTL){
        return dao.deleteTheLoai(maTL);
    }
}