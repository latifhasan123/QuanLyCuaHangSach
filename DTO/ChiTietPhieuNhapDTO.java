package DTO;

public class ChiTietPhieuNhapDTO {
    private int maCTPN;
    private String maPN;
    private String maSach;
    private int soLuong;
    private double giaNhap;
    private double thanhTien;

    public ChiTietPhieuNhapDTO() {}

    // Hỗ trợ truyền int từ GUI (sẽ tự ép sang String)
    public ChiTietPhieuNhapDTO(int maCTPN, int maSach, int soLuong, double giaNhap) {
        this.maCTPN = maCTPN;
        this.maSach = String.valueOf(maSach);
        this.soLuong = soLuong;
        this.giaNhap = giaNhap;
        this.thanhTien = soLuong * giaNhap;
    }

    public int getMaCTPN() { return maCTPN; }
    public void setMaCTPN(int maCTPN) { this.maCTPN = maCTPN; }

    public String getMaPN() { return maPN; }
    public void setMaPN(String maPN) { this.maPN = maPN; }

    public String getMaSach() { return maSach; }
    public void setMaSach(String maSach) { this.maSach = maSach; }

    public int getSoLuong() { return soLuong; }
    public void setSoLuong(int soLuong) { this.soLuong = soLuong; }

    public double getGiaNhap() { return giaNhap; }
    public void setGiaNhap(double giaNhap) { this.giaNhap = giaNhap; }

    public double getThanhTien() { return thanhTien; }
    public void setThanhTien(double thanhTien) { this.thanhTien = thanhTien; }
}