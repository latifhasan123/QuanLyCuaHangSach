package DTO;

public class TonKhoDTO {
    private int maID;
    private String maSach;
    private String tenSach;
    private double giaNhap;
    private int soLuongTon;

    public TonKhoDTO() {}

    public TonKhoDTO(int maID, String maSach, String tenSach, double giaNhap, int soLuongTon) {
        this.maID = maID;
        this.maSach = maSach;
        this.tenSach = tenSach;
        this.giaNhap = giaNhap;
        this.soLuongTon = soLuongTon;
    }

    public int getMaID() { return maID; }
    public void setMaID(int maID) { this.maID = maID; }
    public String getMaSach() { return maSach; }
    public void setMaSach(String maSach) { this.maSach = maSach; }
    public String getTenSach() { return tenSach; }
    public void setTenSach(String tenSach) { this.tenSach = tenSach; }
    public double getGiaNhap() { return giaNhap; }
    public void setGiaNhap(double giaNhap) { this.giaNhap = giaNhap; }
    public int getSoLuongTon() { return soLuongTon; }
    public void setSoLuongTon(int soLuongTon) { this.soLuongTon = soLuongTon; }
}   