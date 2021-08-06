package vn.poly.goodfood.model;

public class DoAnYeuThich {
    private int donGia;
    private String linkAnh;
    private int luotThich;
    private String tenAnh;

    public DoAnYeuThich() {

    }

    public DoAnYeuThich(int donGia, String linkAnh, int luotThich, String tenAnh) {
        this.donGia = donGia;
        this.linkAnh = linkAnh;
        this.luotThich = luotThich;
        this.tenAnh = tenAnh;
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

    public int getLuotThich() {
        return luotThich;
    }

    public void setLuotThich(int luotThich) {
        this.luotThich = luotThich;
    }

    public String getTenAnh() {
        return tenAnh;
    }

    public void setTenAnh(String tenAnh) {
        this.tenAnh = tenAnh;
    }
}
