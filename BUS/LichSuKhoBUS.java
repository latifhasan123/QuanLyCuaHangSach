package BUS;

import DAO.LichSuKhoDAO;
import DTO.LichSuKhoDTO;
import java.util.ArrayList;

public class LichSuKhoBUS {
    private LichSuKhoDAO dao = new LichSuKhoDAO();

    public ArrayList<LichSuKhoDTO> getAll() {
        return dao.selectAll();
    }
}