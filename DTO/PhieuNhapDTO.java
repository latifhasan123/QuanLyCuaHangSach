package DTO;

public class PhieuNhapDTO {
    private int maID;
    private String maPN;
    private int maNV;
    private int maNCC;
    private double tongTien;
    private String trangThai;
    private String ngayTao;

    public PhieuNhapDTO() {}

    public PhieuNhapDTO(int maID, int maNCC, int maNV, String ngayTao, double tongTien) {
        this.maID = maID;
        this.maNCC = maNCC;
        this.maNV = maNV;
        this.ngayTao = ngayTao;
        this.tongTien = tongTien;
        this.trangThai = "HoanThanh";
    }

    public int getMaID() { return maID; }
    public void setMaID(int maID) { this.maID = maID; }

    public String getMaPN() { return maPN; }
    public void setMaPN(String maPN) { this.maPN = maPN; }

    public int getMaNV() { return maNV; }
    public void setMaNV(int maNV) { this.maNV = maNV; }

    public int getMaNCC() { return maNCC; }
    public void setMaNCC(int maNCC) { this.maNCC = maNCC; }

    public double getTongTien() { return tongTien; }
    public void setTongTien(double tongTien) { this.tongTien = tongTien; }

    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }

    public String getNgayTao() { return ngayTao; }
    public void setNgayTao(String ngayTao) { this.ngayTao = ngayTao; }
}