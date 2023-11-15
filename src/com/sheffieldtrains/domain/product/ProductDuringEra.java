package com.sheffieldtrains.domain.product;

public class ProductDuringEra extends Product {
    protected String eraCode;

    public ProductDuringEra(String productCode,
                            String brand,
                            String productName,
                            float price,
                            Gauge gauge,
                            String eraCode) {
        super(productCode, brand, productName, price, gauge, resultSet.getString("productType"));
        this.eraCode = eraCode;
    }

    public String getEraCode() {
        return eraCode;
    }

}
