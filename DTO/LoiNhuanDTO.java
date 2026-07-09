package DTO;

import java.time.LocalDateTime;

public class LoiNhuanDTO {
    private String maDonHang;
    private LocalDateTime ngayTao;
    private double doanhThu; // Tiền khách trả (Đã trừ khuyến mãi)
    private double chiPhi;   // Tiền vốn nhập sách
    private double loiNhuan; // Lợi nhuận = Doanh Thu - Chi Phí

    public LoiNhuanDTO() {}

    public LoiNhuanDTO(String maDonHang, LocalDateTime ngayTao, double doanhThu, double chiPhi) {
        this.maDonHang = maDonHang;
        this.ngayTao = ngayTao;
        this.doanhThu = doanhThu;
        this.chiPhi = chiPhi;
        this.loiNhuan = doanhThu - chiPhi; // Tự động tính
    }

    public String getMaDonHang() { return maDonHang; }
    public void setMaDonHang(String maDonHang) { this.maDonHang = maDonHang; }

    public LocalDateTime getNgayTao() { return ngayTao; }
    public void setNgayTao(LocalDateTime ngayTao) { this.ngayTao = ngayTao; }

    public double getDoanhThu() { return doanhThu; }
    public void setDoanhThu(double doanhThu) { this.doanhThu = doanhThu; this.loiNhuan = this.doanhThu - this.chiPhi; }

    public double getChiPhi() { return chiPhi; }
    public void setChiPhi(double chiPhi) { this.chiPhi = chiPhi; this.loiNhuan = this.doanhThu - this.chiPhi; }

    public double getLoiNhuan() { return loiNhuan; }
}