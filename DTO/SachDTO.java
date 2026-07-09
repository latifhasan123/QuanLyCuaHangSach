package DTO;

import java.util.ArrayList;
import java.util.List;

public class SachDTO {

    private int maID;
    private String maSach;
    private String tenSach;
    private String isbn;
    private int maLoai;
    private int maTacGia;
    private int maNXB;
    private String moTa;
    private String hinhAnh;
    private double giaNhap;
    private double giaBan;
    private int soLuongTon;
    private int soLuongLoi; 
    private int soNgayDoiTra;
    private String trangThai;
    private String tenLoai;
    private String tenTacGia;
    private String tenNXB;

    private List<Integer> listMaLoai = new ArrayList<>();
    private List<Integer> listMaTacGia = new ArrayList<>();

    public SachDTO(){}

    public int getMaID() { return maID; }
    public void setMaID(int maID) { this.maID = maID; }
    public String getMaSach() { return maSach; }
    public void setMaSach(String maSach) { this.maSach = maSach; }
    public String getTenSach() { return tenSach; }
    public void setTenSach(String tenSach) { this.tenSach = tenSach; }
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    public int getMaLoai() { return maLoai; }
    public void setMaLoai(int maLoai) { this.maLoai = maLoai; }
    public int getMaTacGia() { return maTacGia; }
    public void setMaTacGia(int maTacGia) { this.maTacGia = maTacGia; }
    public int getMaNXB() { return maNXB; }
    public void setMaNXB(int maNXB) { this.maNXB = maNXB; }
    public String getMoTa() { return moTa; }
    public void setMoTa(String moTa) { this.moTa = moTa; }
    public String getHinhAnh() { return hinhAnh; }
    public void setHinhAnh(String hinhAnh) { this.hinhAnh = hinhAnh; }
    public double getGiaNhap() { return giaNhap; }
    public void setGiaNhap(double giaNhap) { this.giaNhap = giaNhap; }
    public double getGiaBan() { return giaBan; }
    public void setGiaBan(double giaBan) { this.giaBan = giaBan; }
    public int getSoLuongTon() { return soLuongTon; }
    public void setSoLuongTon(int soLuongTon) { this.soLuongTon = soLuongTon; }
    public int getSoLuongLoi() { return soLuongLoi; }
    public void setSoLuongLoi(int soLuongLoi) { this.soLuongLoi = soLuongLoi; }
    public int getSoNgayDoiTra() { return soNgayDoiTra; }
    public void setSoNgayDoiTra(int soNgayDoiTra) { this.soNgayDoiTra = soNgayDoiTra; }
    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }
    public String getTenLoai() { return tenLoai; }
    public void setTenLoai(String tenLoai) { this.tenLoai = tenLoai; }
    public String getTenTacGia() { return tenTacGia; }
    public void setTenTacGia(String tenTacGia) { this.tenTacGia = tenTacGia; }
    public String getTenNXB() { return tenNXB; }
    public void setTenNXB(String tenNXB) { this.tenNXB = tenNXB; }
    

    public List<Integer> getListMaLoai() { return listMaLoai; }
    public void setListMaLoai(List<Integer> listMaLoai) { this.listMaLoai = listMaLoai; }
    public List<Integer> getListMaTacGia() { return listMaTacGia; }
    public void setListMaTacGia(List<Integer> listMaTacGia) { this.listMaTacGia = listMaTacGia; }
}