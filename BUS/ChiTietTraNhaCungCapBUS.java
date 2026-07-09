package BUS;

import DAO.ChiTietTraNhaCungCapDAO;
import DTO.ChiTietTraNhaCungCapDTO;
import java.util.ArrayList;

public class ChiTietTraNhaCungCapBUS {
    private ChiTietTraNhaCungCapDAO dao = new ChiTietTraNhaCungCapDAO();

    public boolean themCTPTN(ChiTietTraNhaCungCapDTO ct) {
        return dao.insert(ct);
    }

    public ArrayList<ChiTietTraNhaCungCapDTO> getListCTPTN(int maPTN) {
        return dao.selectAll(maPTN);
    }
}