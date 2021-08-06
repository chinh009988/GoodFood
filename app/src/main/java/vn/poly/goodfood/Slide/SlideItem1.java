package vn.poly.goodfood.Slide;

public class SlideItem1 {
    private int image;
    public String ten;
    public double gia;

    public SlideItem1() {

    }

    public SlideItem1(int image, String ten, double gia) {
        this.image = image;
        this.ten = ten;
        this.gia = gia;
    }

    public int getImage() {
        return image;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public double getGia() {
        return gia;
    }

    public void setGia(double gia) {
        this.gia = gia;
    }
}
