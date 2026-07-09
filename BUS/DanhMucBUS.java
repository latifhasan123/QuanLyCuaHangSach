package BUS;

import DAO.DanhMucDAO;
import DTO.DanhMucDTO;
import java.util.List;

public class DanhMucBUS {

    private DanhMucDAO dao = new DanhMucDAO();

    public List<DanhMucDTO> getAllDanhMuc() { return dao.getAll(); }
    
    public boolean addDanhMuc(DanhMucDTO dm) { return dao.addDanhMuc(dm); }
    
    public boolean updateDanhMuc(DanhMucDTO dm) { return dao.updateDanhMuc(dm); }
    
    public boolean deleteDanhMuc(String maDM) { return dao.deleteDanhMuc(maDM); }
}