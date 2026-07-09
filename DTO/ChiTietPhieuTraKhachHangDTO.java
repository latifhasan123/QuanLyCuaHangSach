package DTO;

public class ChiTietPhieuTraKhachHangDTO {
    private int maPTKH;
    private int maSach;
    private int soLuongTra;
    private long donGiaTra;

    public ChiTietPhieuTraKhachHangDTO() {}

    public ChiTietPhieuTraKhachHangDTO(int maPTKH, int maSach, int soLuongTra, long donGiaTra) {
        this.maPTKH = maPTKH;
        this.maSach = maSach;
        this.soLuongTra = soLuongTra;
        this.donGiaTra = donGiaTra;
    }

    public int getMaPTKH() { return maPTKH; }
    public void setMaPTKH(int maPTKH) { this.maPTKH = maPTKH; }
    public int getMaSach() { return maSach; }
    public void setMaSach(int maSach) { this.maSach = maSach; }
    public int getSoLuongTra() { return soLuongTra; }
    public void setSoLuongTra(int soLuongTra) { this.soLuongTra = soLuongTra; }
    public long getDonGiaTra() { return donGiaTra; }
    public void setDonGiaTra(long donGiaTra) { this.donGiaTra = donGiaTra; }
}