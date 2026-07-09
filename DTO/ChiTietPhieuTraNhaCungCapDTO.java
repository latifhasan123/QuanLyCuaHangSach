package DTO;

public class ChiTietPhieuTraNhaCungCapDTO {
    private int maPT;
    private int maSach;
    private int soLuongTra;
    private long donGiaTra; 
    private String lyDo;   

    public ChiTietPhieuTraNhaCungCapDTO() {}

    public ChiTietPhieuTraNhaCungCapDTO(int maPT, int maSach, int soLuongTra, long donGiaTra, String lyDo) {
        this.maPT = maPT;
        this.maSach = maSach;
        this.soLuongTra = soLuongTra;
        this.donGiaTra = donGiaTra;
        this.lyDo = lyDo;
    }

    public int getMaPT() { return maPT; }
    public void setMaPT(int maPT) { this.maPT = maPT; }
    public int getMaSach() { return maSach; }
    public void setMaSach(int maSach) { this.maSach = maSach; }
    public int getSoLuongTra() { return soLuongTra; }
    public void setSoLuongTra(int soLuongTra) { this.soLuongTra = soLuongTra; }
    public long getDonGiaTra() { return donGiaTra; }
    public void setDonGiaTra(long donGiaTra) { this.donGiaTra = donGiaTra; }
    public String getLyDo() { return lyDo; }
    public void setLyDo(String lyDo) { this.lyDo = lyDo; }
}