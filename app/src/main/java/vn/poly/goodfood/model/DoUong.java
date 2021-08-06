package vn.poly.goodfood.model;

public class DoUong {
    private int donGia;
    private String urlAnh;
    private String tenAnh;

    public DoUong() {
    }
    public DoUong(int donGia,  String tenAnh, String urlAnh) {
        this.donGia = donGia;
        this.urlAnh = urlAnh;
        this.tenAnh = tenAnh;
    }

    public int getDonGia() {
        return donGia;
    }

    public void setDonGia(int donGia) {
        this.donGia = donGia;
    }

    public String getUrlAnh() {
        return urlAnh;
    }

    public void setUrlAnh(String urlAnh) {
        this.urlAnh = urlAnh;
    }

    public String getTenAnh() {
        return tenAnh;
    }

    public void setTenAnh(String tenAnh) {
        this.tenAnh = tenAnh;
    }
}
