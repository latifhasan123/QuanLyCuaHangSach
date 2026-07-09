package DTO;

public class PhanQuyenDTO {
    private int maID;
    private String maPQ;
    private String tenQuyen;
    private String moTa;
    
    // ========================================================
    // QUY ƯỚC 5 CẤP ĐỘ QUYỀN (Thay vì chỉ 0 và 1 như lúc trước):
    // 0 = Không có quyền
    // 1 = Chỉ xem / Xem và In
    // 2 = Xem và Thêm
    // 3 = Xem, Thêm và Sửa
    // 4 = Toàn quyền (Bao gồm cả Xóa, Hủy, Duyệt)
    // ========================================================
    private int qlSach, qlThuocTinh, qlBanHang, qlKhuyenMai, qlHoaDon, qlPhieuDoiTra;
    private int qlNhapHang, qlPhieuNhap, qlKhachHang, qlNCC, qlNhanVien, qlTaiKhoan;
    private int qlPhanQuyen, qlThongKe;

    public PhanQuyenDTO() {}

    // --- GETTER & SETTER CƠ BẢN ---
    public int getMaID() { return maID; }
    public void setMaID(int maID) { this.maID = maID; }
    public String getMaPQ() { return maPQ; }
    public void setMaPQ(String maPQ) { this.maPQ = maPQ; }
    public String getTenQuyen() { return tenQuyen; }
    public void setTenQuyen(String tenQuyen) { this.tenQuyen = tenQuyen; }
    public String getMoTa() { return moTa; }
    public void setMoTa(String moTa) { this.moTa = moTa; }

    // --- GETTER & SETTER 14 QUYỀN ---
    public int getQlSach() { return qlSach; }
    public void setQlSach(int qlSach) { this.qlSach = qlSach; }
    public int getQlThuocTinh() { return qlThuocTinh; }
    public void setQlThuocTinh(int qlThuocTinh) { this.qlThuocTinh = qlThuocTinh; }
    public int getQlBanHang() { return qlBanHang; }
    public void setQlBanHang(int qlBanHang) { this.qlBanHang = qlBanHang; }
    public int getQlKhuyenMai() { return qlKhuyenMai; }
    public void setQlKhuyenMai(int qlKhuyenMai) { this.qlKhuyenMai = qlKhuyenMai; }
    public int getQlHoaDon() { return qlHoaDon; }
    public void setQlHoaDon(int qlHoaDon) { this.qlHoaDon = qlHoaDon; }
    public int getQlPhieuDoiTra() { return qlPhieuDoiTra; }
    public void setQlPhieuDoiTra(int qlPhieuDoiTra) { this.qlPhieuDoiTra = qlPhieuDoiTra; }
    public int getQlNhapHang() { return qlNhapHang; }
    public void setQlNhapHang(int qlNhapHang) { this.qlNhapHang = qlNhapHang; }
    public int getQlPhieuNhap() { return qlPhieuNhap; }
    public void setQlPhieuNhap(int qlPhieuNhap) { this.qlPhieuNhap = qlPhieuNhap; }
    public int getQlKhachHang() { return qlKhachHang; }
    public void setQlKhachHang(int qlKhachHang) { this.qlKhachHang = qlKhachHang; }
    public int getQlNCC() { return qlNCC; }
    public void setQlNCC(int qlNCC) { this.qlNCC = qlNCC; }
    public int getQlNhanVien() { return qlNhanVien; }
    public void setQlNhanVien(int qlNhanVien) { this.qlNhanVien = qlNhanVien; }
    public int getQlTaiKhoan() { return qlTaiKhoan; }
    public void setQlTaiKhoan(int qlTaiKhoan) { this.qlTaiKhoan = qlTaiKhoan; }
    public int getQlPhanQuyen() { return qlPhanQuyen; }
    public void setQlPhanQuyen(int qlPhanQuyen) { this.qlPhanQuyen = qlPhanQuyen; }
    public int getQlThongKe() { return qlThongKe; }
    public void setQlThongKe(int qlThongKe) { this.qlThongKe = qlThongKe; }
}