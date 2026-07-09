package DTO;

import java.sql.Timestamp;

public class DonHangDTO {
    private int maID;
    private String maKH;
    private double tongTien;
    private String diaChiGiao;
    private String tenNguoiNhan;
    private String sdtNhan;
    private String maDH; 
    private java.sql.Timestamp ngayTao; 
    private String trangThaiDon;
    private String trangThaiGiaoHang;
    private double thanhTien;

    public int getMaID() { return maID; }
    public void setMaID(int maID) { this.maID = maID; }
    public String getMaKH() { return maKH; }
    public void setMaKH(String maKH) { this.maKH = maKH; }
    public double getTongTien() { return tongTien; }
    public void setTongTien(double tongTien) { this.tongTien = tongTien; }
    public String getDiaChiGiao() { return diaChiGiao; }
    public void setDiaChiGiao(String diaChiGiao) { this.diaChiGiao = diaChiGiao; }
    public String getTenNguoiNhan() { return tenNguoiNhan; }
    public void setTenNguoiNhan(String tenNguoiNhan) { this.tenNguoiNhan = tenNguoiNhan; }
    public String getSdtNhan() { return sdtNhan; }
    public void setSdtNhan(String sdtNhan) { this.sdtNhan = sdtNhan; }
    public String getMaDH() { return maDH; }
    public void setMaDH(String maDH) { this.maDH = maDH; }
    public java.sql.Timestamp getNgayTao() { return ngayTao; }
    public void setNgayTao(java.sql.Timestamp ngayTao) { this.ngayTao = ngayTao; }
    public String getTrangThaiDon() { return trangThaiDon; }
    public void setTrangThaiDon(String trangThaiDon) { this.trangThaiDon = trangThaiDon; }
    public String getTrangThaiGiaoHang() { return trangThaiGiaoHang; }
    public void setTrangThaiGiaoHang(String trangThaiGiaoHang) { this.trangThaiGiaoHang = trangThaiGiaoHang; }
    public double getThanhTien() { return thanhTien; }
    public void setThanhTien(double thanhTien) { this.thanhTien = thanhTien; }
}