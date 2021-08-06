package vn.poly.goodfood.model;

public class HealThy {
    private int donGia;
    private String linkAnh;
    private String nameAnh;

    public HealThy() {

    }

    public HealThy(int donGia, String linkAnh, String nameAnh) {
        this.donGia = donGia;
        this.linkAnh = linkAnh;
        this.nameAnh = nameAnh;
    }

    public int getDonGia() {
        return donGia;
    }

    public void setDonGia(int donGia) {
        this.donGia = donGia;
    }

    public String getLinkAnh() {
        return linkAnh;
    }

    public void setLinkAnh(String linkAnh) {
        this.linkAnh = linkAnh;
    }

    public String getNameAnh() {
        return nameAnh;
    }

    public void setNameAnh(String nameAnh) {
        this.nameAnh = nameAnh;
    }
}
