package fpoly.md18402.duan1_nhom4.Model;

public class Giay {
    private int maGiay;
    private String tenGiay;
    private String moTa;
    private int giaMua;
    private int maLoai;

    public Giay() {
    }

    public Giay(String tenGiay, String moTa, int giaMua, int maLoai) {
        this.tenGiay = tenGiay;
        this.moTa = moTa;
        this.giaMua = giaMua;
        this.maLoai = maLoai;
    }

    public Giay(int maGiay, String tenGiay, String moTa, int giaMua, int maLoai) {
        this.maGiay = maGiay;
        this.tenGiay = tenGiay;
        this.moTa = moTa;
        this.giaMua = giaMua;
        this.maLoai = maLoai;
    }

    public int getMaGiay() {
        return maGiay;
    }

    public void setMaGiay(int maGiay) {
        this.maGiay = maGiay;
    }

    public String getTenGiay() {
        return tenGiay;
    }

    public void setTenGiay(String tenGiay) {
        this.tenGiay = tenGiay;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public int getGiaMua() {
        return giaMua;
    }

    public void setGiaMua(int giaMua) {
        this.giaMua = giaMua;
    }

    public int getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(int maLoai) {
        this.maLoai = maLoai;
    }
}
