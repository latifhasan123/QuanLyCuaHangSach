package BUS;

import DTO.SachDTO;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import DAO.SachDAO;
import DAO.GioHangDAO;

public class ClientGioHangBUS {
    public static ArrayList<SachDTO> danhSachGioHang = new ArrayList<>();
    private SachDAO sachDAO = new SachDAO();
    private GioHangDAO ghDAO = new GioHangDAO();

    public ArrayList<SachDTO> getDanhSach() { return danhSachGioHang; }

    public void loadGioHangFromDB() {
        danhSachGioHang.clear();
        if (KhachHangBUS.currentCustomer != null) {
            int maGH = ghDAO.getMaGH(KhachHangBUS.currentCustomer.getMaID());
            if (maGH != -1) {
                danhSachGioHang = ghDAO.getChiTietGioHang(maGH);
            }
        }
    }

    public void clearRamOnly() {
        danhSachGioHang.clear();
    }

    public void themVaoGio(SachDTO s, int soLuongThem) {
        if (KhachHangBUS.currentCustomer == null) {
            JOptionPane.showMessageDialog(null, "Bạn phải đăng nhập trước khi thêm vào giỏ hàng!");
            return;
        }

        int tonKho = getTonKho(s.getMaID());
        SachDTO found = null;
        for (SachDTO item : danhSachGioHang) {
            if (item.getMaID() == s.getMaID()) {
                found = item;
                break;
            }
        }

        if (found != null) {
            if (found.getSoLuongTon() + soLuongThem > tonKho) {
                JOptionPane.showMessageDialog(null, "Không thể thêm! Tổng số lượng vượt quá tồn kho (Kho còn: " + tonKho + ")");
                return;
            }
            found.setSoLuongTon(found.getSoLuongTon() + soLuongThem);
        } else {
            if (soLuongThem > tonKho) {
                JOptionPane.showMessageDialog(null, "Số lượng yêu cầu vượt quá tồn kho (Kho còn: " + tonKho + ")");
                return;
            }
            s.setSoLuongTon(soLuongThem);
            danhSachGioHang.add(s);
        }
        
        int maGH = ghDAO.taoGioHangNeuChuaCo(KhachHangBUS.currentCustomer.getMaID());
        ghDAO.capNhatSanPhamTrongGio(maGH, s.getMaID(), soLuongThem);
    }

    public void themSoLuong(String maSach) {
        for (SachDTO s : danhSachGioHang) {
            if (s.getMaSach().equals(maSach)) {
                int tonKho = getTonKho(s.getMaID());
                if (s.getSoLuongTon() < tonKho) {
                    s.setSoLuongTon(s.getSoLuongTon() + 1);
                    if (KhachHangBUS.currentCustomer != null) {
                        int maGH = ghDAO.taoGioHangNeuChuaCo(KhachHangBUS.currentCustomer.getMaID());
                        ghDAO.capNhatSanPhamTrongGio(maGH, s.getMaID(), 1);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Đã đạt giới hạn tồn kho!");
                }
                return;
            }
        }
    }

    public void giamSoLuong(String maSach) {
        for (int i = 0; i < danhSachGioHang.size(); i++) {
            SachDTO s = danhSachGioHang.get(i);
            if (s.getMaSach().equals(maSach)) {
                int slMua = s.getSoLuongTon();
                int maID = s.getMaID();
                if (slMua > 1) {
                    s.setSoLuongTon(slMua - 1);
                    if (KhachHangBUS.currentCustomer != null) {
                        int maGH = ghDAO.taoGioHangNeuChuaCo(KhachHangBUS.currentCustomer.getMaID());
                        ghDAO.capNhatSanPhamTrongGio(maGH, maID, -1);
                    }
                } else {
                    danhSachGioHang.remove(i);
                    if (KhachHangBUS.currentCustomer != null) {
                        int maGH = ghDAO.taoGioHangNeuChuaCo(KhachHangBUS.currentCustomer.getMaID());
                        ghDAO.xoaSanPhamKhoiGio(maGH, maID);
                    }
                }
                return;
            }
        }
    }

    public double tinhTongTien() {
        double tong = 0;
        for (SachDTO s : danhSachGioHang) {
            tong += s.getGiaBan() * s.getSoLuongTon();
        }
        return tong;
    }

    private int getTonKho(int maID) {
        for(SachDTO s : sachDAO.getAll()){
            if(s.getMaID() == maID) return s.getSoLuongTon();
        }
        return 0;
    }

    public ArrayList<SachDTO> getDanhSachUnique() {
        ArrayList<SachDTO> uniqueList = new ArrayList<>();
        for (SachDTO s : danhSachGioHang) {
            boolean exists = false;
            for (SachDTO u : uniqueList) {
                if (u.getMaSach().equals(s.getMaSach())) {
                    exists = true;
                    break;
                }
            }
            if (!exists) uniqueList.add(s);
        }
        return uniqueList;
    }

    public int demSoLuong(String maSach) {
        int count = 0;
        for (SachDTO s : danhSachGioHang) {
            if (s.getMaSach().equals(maSach)) count++;
        }
        return count;
    }
    
    public void xoaSanPham(String maSach){
        int maIDToRemove = -1;
        for (SachDTO s : danhSachGioHang) {
            if (s.getMaSach().equals(maSach)) {
                maIDToRemove = s.getMaID();
                break;
            }
        }
        if (maIDToRemove != -1 && KhachHangBUS.currentCustomer != null) {
            int maGH = ghDAO.taoGioHangNeuChuaCo(KhachHangBUS.currentCustomer.getMaID());
            ghDAO.xoaSanPhamKhoiGio(maGH, maIDToRemove);
        }
        danhSachGioHang.removeIf(s->s.getMaSach().equals(maSach));
    }
    
    public void clear(){
        if (KhachHangBUS.currentCustomer != null) {
            int maGH = ghDAO.getMaGH(KhachHangBUS.currentCustomer.getMaID());
            if (maGH != -1) {
                ghDAO.clearGioHang(maGH);
            }
        }
        danhSachGioHang.clear();
    }
}