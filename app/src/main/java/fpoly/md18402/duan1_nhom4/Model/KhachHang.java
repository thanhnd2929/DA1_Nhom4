package fpoly.md18402.duan1_nhom4.Model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import fpoly.md18402.duan1_nhom4.Database.DbHelper;

public class KhachHang {
    private int maKH;
    private String hoTen;
    private String sdt;

    public KhachHang() {
    }

    public KhachHang(int maKH, String hoTen, String sdt) {
        this.maKH = maKH;
        this.hoTen = hoTen;
        this.sdt = sdt;
    }

    public KhachHang(String hoTen, String sdt) {
        this.hoTen = hoTen;
        this.sdt = sdt;
    }

    public int getMaKH() {
        return maKH;
    }

    public void setMaKH(int maKH) {
        this.maKH = maKH;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }
}
