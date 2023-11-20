package com.sheffieldtrains.domain.product;

public class Controller extends Product{
    private boolean isDigital;

    public Controller(String productCode,
                      String brand,
                      String productName,
                      float price,
                      Gauge gauge,
                      int quantity,
                      boolean isDigital) {
        super(productCode, brand, productName, price, gauge, quantity);
        this.isDigital = isDigital;
    }

    public Controller() {

    }

    public boolean isDigital() {
        return isDigital;
    }

    public void setDigital(boolean digital) {
        isDigital = digital;
    }
}
