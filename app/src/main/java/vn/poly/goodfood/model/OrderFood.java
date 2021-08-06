package vn.poly.goodfood.model;

public class OrderFood {

    private String nameFood;
    private int numberFood;
    private double priceFood;

    public OrderFood() {
    }

    public OrderFood(String nameFood, int numberFood, double priceFood) {
        this.nameFood = nameFood;
        this.numberFood = numberFood;
        this.priceFood = priceFood;
    }

    public String getNameFood() {
        return nameFood;
    }

    public void setNameFood(String nameFood) {
        this.nameFood = nameFood;
    }

    public int getNumberFood() {
        return numberFood;
    }

    public void setNumberFood(int numberFood) {
        this.numberFood = numberFood;
    }

    public double getPriceFood() {
        return priceFood;
    }

    public void setPriceFood(double priceFood) {
        this.priceFood = priceFood;
    }
}
