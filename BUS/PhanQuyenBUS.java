package BUS;

import DAO.PhanQuyenDAO;
import DTO.PhanQuyenDTO;
import java.util.ArrayList;

public class PhanQuyenBUS {
    private PhanQuyenDAO dao = new PhanQuyenDAO();

    public ArrayList<PhanQuyenDTO> getAll() {
        return dao.selectAll();
    }

    public boolean luuQuyen(PhanQuyenDTO pq) {
        return dao.updateQuyen(pq);
    }
}