package BUS;

import DAO.LoiNhuanDAO;
import DTO.LoiNhuanDTO;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class LoiNhuanBUS {
    private LoiNhuanDAO dao = new LoiNhuanDAO();
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public ArrayList<LoiNhuanDTO> getLoiNhuan(Date tuNgay, Date denNgay) {
        String strTuNgay = (tuNgay != null) ? sdf.format(tuNgay) : null;
        String strDenNgay = (denNgay != null) ? sdf.format(denNgay) : null;
        
        return dao.getLoiNhuanTheoDonHang(strTuNgay, strDenNgay);
    }
}