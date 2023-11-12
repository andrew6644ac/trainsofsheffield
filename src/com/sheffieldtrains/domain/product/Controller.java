package com.sheffieldtrains.domain.product;

public class Controller extends Product{
    private boolean isDigital;

    public Controller(String productCode,
                      String brand,
                      String productName,
                      float price,
                      Gauge gauge) {
        super(productCode, brand, productName, price, gauge);
    }
    public boolean isDigital() {
        return isDigital;
    }
}
