package vn.poly.goodfood.model;

public class Combo {
    private int donGia;
    private String tenAnh;
    private String urlAnh;

    public Combo() {

    }

    public Combo(int donGia, String tenAnh, String urlAnh) {
        this.donGia = donGia;
        this.tenAnh = tenAnh;
        this.urlAnh = urlAnh;
    }

    public int getDonGia() {
        return donGia;
    }

    public void setDonGia(int donGia) {
        this.donGia = donGia;
    }

    public String getTenAnh() {
        return tenAnh;
    }

    public void setTenAnh(String tenAnh) {
        this.tenAnh = tenAnh;
    }

    public String getUrlAnh() {
        return urlAnh;
    }

    public void setUrlAnh(String urlAnh) {
        this.urlAnh = urlAnh;
    }
}
