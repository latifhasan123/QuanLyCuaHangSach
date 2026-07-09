package DTO;
import java.sql.Timestamp;

public class PhieuTraKhachHangDTO {
    private int maPTKH;
    private int maHD;
    private int maKH;
    private int maNV;
    private Timestamp ngayTra;
    private long tongTienHoan;

    public PhieuTraKhachHangDTO() {}

    public PhieuTraKhachHangDTO(int maPTKH, int maHD, int maKH, int maNV, Timestamp ngayTra, long tongTienHoan) {
        this.maPTKH = maPTKH;
        this.maHD = maHD;
        this.maKH = maKH;
        this.maNV = maNV;
        this.ngayTra = ngayTra;
        this.tongTienHoan = tongTienHoan;
    }

    public int getMaPTKH() { return maPTKH; }
    public void setMaPTKH(int maPTKH) { this.maPTKH = maPTKH; }
    public int getMaHD() { return maHD; }
    public void setMaHD(int maHD) { this.maHD = maHD; }
    public int getMaKH() { return maKH; }
    public void setMaKH(int maKH) { this.maKH = maKH; }
    public int getMaNV() { return maNV; }
    public void setMaNV(int maNV) { this.maNV = maNV; }
    public Timestamp getNgayTra() { return ngayTra; }
    public void setNgayTra(Timestamp ngayTra) { this.ngayTra = ngayTra; }
    public long getTongTienHoan() { return tongTienHoan; }
    public void setTongTienHoan(long tongTienHoan) { this.tongTienHoan = tongTienHoan; }
}