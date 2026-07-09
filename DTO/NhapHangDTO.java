package DTO;
import java.sql.Timestamp;

public class NhapHangDTO {
    private int maPN;
    private int maNCC;
    private int maNV;
    private Timestamp ngayNhap;
    private long tongTien;

    public NhapHangDTO() {}
    public NhapHangDTO(int maPN, int maNCC, int maNV, Timestamp ngayNhap, long tongTien) {
        this.maPN = maPN;
        this.maNCC = maNCC;
        this.maNV = maNV;
        this.ngayNhap = ngayNhap;
        this.tongTien = tongTien;
    }

    public int getMaPN() { return maPN; }
    public void setMaPN(int maPN) { this.maPN = maPN; }
    public int getMaNCC() { return maNCC; }
    public void setMaNCC(int maNCC) { this.maNCC = maNCC; }
    public int getMaNV() { return maNV; }
    public void setMaNV(int maNV) { this.maNV = maNV; }
    public Timestamp getNgayNhap() { return ngayNhap; }
    public void setNgayNhap(Timestamp ngayNhap) { this.ngayNhap = ngayNhap; }
    public long getTongTien() { return tongTien; }
    public void setTongTien(long tongTien) { this.tongTien = tongTien; }
}