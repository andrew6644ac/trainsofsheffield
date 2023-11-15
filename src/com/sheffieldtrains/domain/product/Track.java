package com.sheffieldtrains.domain.product;

public class Track extends Product{
    public Track(String productCode,
                 String brand,
                 String productName,
                 float price,
                 Gauge gauge) {
        super(productCode, brand, productName, price, gauge, resultSet.getString("productType"));
    }
}
