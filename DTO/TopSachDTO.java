package DTO;

public class TopSachDTO {
    private String tenSach;
    private int soLuongBan;
    private double doanhThu;

    public TopSachDTO(String tenSach, int soLuongBan, double doanhThu) {
        this.tenSach = tenSach;
        this.soLuongBan = soLuongBan;
        this.doanhThu = doanhThu;
    }

    public String getTenSach() { return tenSach; }
    public void setTenSach(String tenSach) { this.tenSach = tenSach; }
    public int getSoLuongBan() { return soLuongBan; }
    public void setSoLuongBan(int soLuongBan) { this.soLuongBan = soLuongBan; }
    public double getDoanhThu() { return doanhThu; }
    public void setDoanhThu(double doanhThu) { this.doanhThu = doanhThu; }
}