package com.sheffieldtrains.domain.product;

public class Controller extends Product{
    private boolean isDigital;

    public Controller(String productCode,
                      String brand,
                      String productName,
                      float price,
                      Gauge gauge,
                      boolean isDigital) {
        super(productCode, brand, productName, price, gauge, resultSet.getString("productType"));
        this.isDigital = isDigital;
    }
    public boolean isDigital() {
        return isDigital;
    }
}
