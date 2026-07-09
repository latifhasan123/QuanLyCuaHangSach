package BUS;

import DAO.DonHangDAO;
import DTO.DonHangDTO;
import DTO.SachDTO;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class DonHangBUS {
    private DonHangDAO donHangDAO = new DonHangDAO();

    public int luuDonHang(DonHangDTO dh, int maKH, ArrayList<SachDTO> dsMua) {
        if (KhachHangBUS.currentCustomer == null) {
            return -1; 
        }
        return donHangDAO.insertDonHang(dh, maKH, dsMua);
    }
    
    public ArrayList<DonHangDTO> getLichSuDonHang(int maKH) {
        return donHangDAO.getLichSuDonHang(maKH);
    }

    public boolean huyDonHang(int maDH) {
        return donHangDAO.huyDonHang(maDH);
    }

    public ArrayList<DonHangDTO> getDonHangOnline() {
        return donHangDAO.getDonHangOnline();
    }

    public ArrayList<DonHangDTO> getDonHangOnlineByStatus(String status) {
        ArrayList<DonHangDTO> all = donHangDAO.getDonHangOnline();
        if (status.equals("Tất cả")) return all;
        
        ArrayList<DonHangDTO> filtered = new ArrayList<>();
        for (DonHangDTO dh : all) {
            if (dh.getTrangThaiDon() != null && dh.getTrangThaiDon().equals(status)) {
                filtered.add(dh);
            }
        }
        return filtered;
    }

    public boolean xacNhanDon(int maID) {
        return donHangDAO.updateTrangThaiOnline(maID, "DaXacNhan", "Đang giao hàng");
    }

    public boolean hoanThanhDon(int maID) {
        return donHangDAO.hoanThanhDonOnline(maID);
    }

    public boolean huyDonAdmin(int maID) {
        return donHangDAO.updateTrangThaiOnline(maID, "DaHuy", "Giao thất bại");
    }

    public ArrayList<DonHangDTO> searchDonHangOnlineUnified(String basicKw, String maDH, String tenKH, String sdt, String status) {
        ArrayList<DonHangDTO> listByStatus = getDonHangOnlineByStatus(status);
        ArrayList<DonHangDTO> result = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        String kw = basicKw.toLowerCase().trim();
        String kwMa = maDH.toLowerCase().trim();
        String kwTen = tenKH.toLowerCase().trim();
        String kwSdt = sdt.toLowerCase().trim();

        for (DonHangDTO dh : listByStatus) {
            String dateStr = dh.getNgayTao() != null ? sdf.format(dh.getNgayTao()) : "";
            
            boolean matchBasic = kw.isEmpty() || 
                                (dh.getMaDH() != null && dh.getMaDH().toLowerCase().contains(kw)) ||
                                (dh.getTenNguoiNhan() != null && dh.getTenNguoiNhan().toLowerCase().contains(kw)) ||
                                (dh.getSdtNhan() != null && dh.getSdtNhan().contains(kw)) ||
                                dateStr.contains(kw); 
            
            boolean matchAdv = true;
            if (!kwMa.isEmpty() && (dh.getMaDH() == null || !dh.getMaDH().toLowerCase().contains(kwMa))) matchAdv = false;
            if (!kwTen.isEmpty() && (dh.getTenNguoiNhan() == null || !dh.getTenNguoiNhan().toLowerCase().contains(kwTen))) matchAdv = false;
            if (!kwSdt.isEmpty() && (dh.getSdtNhan() == null || !dh.getSdtNhan().contains(kwSdt))) matchAdv = false;

            if (matchBasic && matchAdv) {
                result.add(dh);
            }
        }
        return result;
    }
}