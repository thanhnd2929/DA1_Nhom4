package fpoly.md18402.duan1_nhom4.Model;

public class CTHD {
    private int maCTHD;
    private int maHD;
    private int maGiay;
    private int soLuong;
    private int giaMua;

    public CTHD() {
    }

    public CTHD(int maGiay, int soLuong, int giaMua) {
        this.maGiay = maGiay;
        this.soLuong = soLuong;
        this.giaMua = giaMua;
    }

    public CTHD(int maHD, int maGiay, int soLuong, int giaMua) {
        this.maHD = maHD;
        this.maGiay = maGiay;
        this.soLuong = soLuong;
        this.giaMua = giaMua;
    }

    public int getMaCTHD() {
        return maCTHD;
    }

    public void setMaCTHD(int maCTHD) {
        this.maCTHD = maCTHD;
    }

    public int getMaHD() {
        return maHD;
    }

    public void setMaHD(int maHD) {
        this.maHD = maHD;
    }

    public int getMaGiay() {
        return maGiay;
    }

    public void setMaGiay(int maGiay) {
        this.maGiay = maGiay;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getGiaMua() {
        return giaMua;
    }

    public void setGiaMua(int giaMua) {
        this.giaMua = giaMua;
    }
}
