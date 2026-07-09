package BUS; // ĐÃ SỬA: Viết hoa

import DAO.NhaXuatBanDAO; // ĐÃ SỬA: Viết hoa
import DTO.NhaXuatBanDTO; // ĐÃ SỬA: Viết hoa
import java.util.List;

public class NhaXuatBanBUS {

    private NhaXuatBanDAO dao = new NhaXuatBanDAO();

    public List<NhaXuatBanDTO> getAllNXB(){
        return dao.getAll();
    }
    public boolean addNXB(NhaXuatBanDTO nxb){
        return dao.addNXB(nxb);
    }
    public boolean updateNXB(NhaXuatBanDTO nxb){
        return dao.updateNXB(nxb);
    }
    // Thêm hàm này vào trước dấu ngoặc đóng } cuối cùng của class NhaXuatBanBUS
    public boolean deleteNXB(String maNXB) {
        return dao.deleteNXB(maNXB);
    }
}