package DTO;

public class ThongKeDTO {
    private String thoiGian;   // Lưu ngày, tháng hoặc năm (VD: "2026-03-07")
    private double doanhThu;   // Tổng tiền thu được
    private int soDonHang;     // Tổng số lượng hóa đơn bán ra
    private int soLuongSach;   // Tổng số cuốn sách đã bán

    public ThongKeDTO() {
    }

    public ThongKeDTO(String thoiGian, double doanhThu, int soDonHang, int soLuongSach) {
        this.thoiGian = thoiGian;
        this.doanhThu = doanhThu;
        this.soDonHang = soDonHang;
        this.soLuongSach = soLuongSach;
    }

    // --- GETTER & SETTER ---
    public String getThoiGian() { return thoiGian; }
    public void setThoiGian(String thoiGian) { this.thoiGian = thoiGian; }

    public double getDoanhThu() { return doanhThu; }
    public void setDoanhThu(double doanhThu) { this.doanhThu = doanhThu; }

    public int getSoDonHang() { return soDonHang; }
    public void setSoDonHang(int soDonHang) { this.soDonHang = soDonHang; }

    public int getSoLuongSach() { return soLuongSach; }
    public void setSoLuongSach(int soLuongSach) { this.soLuongSach = soLuongSach; }
}