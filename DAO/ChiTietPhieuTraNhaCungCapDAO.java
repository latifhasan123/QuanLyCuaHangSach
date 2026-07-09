package DAO;

import DTO.ChiTietPhieuTraNhaCungCapDTO;
import java.sql.*;
import java.util.ArrayList;

public class ChiTietPhieuTraNhaCungCapDAO {
    public boolean insert(ChiTietPhieuTraNhaCungCapDTO ct) {
        System.out.println("Đã lưu chi tiết trả hàng cho sách mã: " + ct.getMaSach());
        return true;
    }

    public ArrayList<ChiTietPhieuTraNhaCungCapDTO> getByMaPT(int maPT) {
        ArrayList<ChiTietPhieuTraNhaCungCapDTO> list = new ArrayList<>();
        return list;
    }
}