package DTO;

import java.sql.Timestamp;

public class PhieuTraNhaCungCapDTO {
    private int maID;
    private String maPTN;
    private int maNV;
    private int maNCC;
    private String lyDo;
    private double tongTienHoan;
    private Timestamp ngayTao;

    public PhieuTraNhaCungCapDTO() {}

    public int getMaID() { return maID; }
    public void setMaID(int maID) { this.maID = maID; }

    public String getMaPTN() { return maPTN; }
    public void setMaPTN(String maPTN) { this.maPTN = maPTN; }

    public int getMaNV() { return maNV; }
    public void setMaNV(int maNV) { this.maNV = maNV; }

    public int getMaNCC() { return maNCC; }
    public void setMaNCC(int maNCC) { this.maNCC = maNCC; }

    public String getLyDo() { return lyDo; }
    public void setLyDo(String lyDo) { this.lyDo = lyDo; }

    public double getTongTienHoan() { return tongTienHoan; }
    public void setTongTienHoan(double tongTienHoan) { this.tongTienHoan = tongTienHoan; }

    public Timestamp getNgayTao() { return ngayTao; }
    public void setNgayTao(Timestamp ngayTao) { this.ngayTao = ngayTao; }
}