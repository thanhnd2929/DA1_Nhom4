package fpoly.md18402.duan1_nhom4.Model;

import java.util.Date;

public class HoaDon {
    private int maHD;
    private Date ngayMua;
    private int thanhToan;
    private int maKH;
    private String maNV;

    public HoaDon() {
    }

    public HoaDon(int maHD, Date ngayMua, int thanhToan, int maKH, String maNV) {
        this.maHD = maHD;
        this.ngayMua = ngayMua;
        this.thanhToan = thanhToan;
        this.maKH = maKH;
        this.maNV = maNV;
    }

    public HoaDon(Date ngayMua, int thanhToan, int maKH, String maNV) {
        this.ngayMua = ngayMua;
        this.thanhToan = thanhToan;
        this.maKH = maKH;
        this.maNV = maNV;
    }

    public int getMaHD() {
        return maHD;
    }

    public void setMaHD(int maHD) {
        this.maHD = maHD;
    }

    public Date getNgayMua() {
        return ngayMua;
    }

    public void setNgayMua(Date ngayMua) {
        this.ngayMua = ngayMua;
    }

    public int getThanhToan() {
        return thanhToan;
    }

    public void setThanhToan(int thanhToan) {
        this.thanhToan = thanhToan;
    }

    public int getMaKH() {
        return maKH;
    }

    public void setMaKH(int maKH) {
        this.maKH = maKH;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }
}
