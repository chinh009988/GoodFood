package vn.poly.goodfood.model;

public class Bill {
    private String hoTen;
    private int phone;
    private String address;
    private double giaBill;

    public Bill() {
    }

    public Bill(String hoTen, int phone, String address, double giaBill) {
        this.hoTen = hoTen;
        this.phone = phone;
        this.address = address;
        this.giaBill = giaBill;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getGiaBill() {
        return giaBill;
    }

    public void setGiaBill(double giaBill) {
        this.giaBill = giaBill;
    }
}
