package com.sheffieldtrains.domain.product;

public class ProductDuringEra extends Product {
    String eraCode;

    public ProductDuringEra(String productCode,
                            String brand,
                            String productName,
                            float price,
                            Gauge gauge,
                            String eraCode) {
        super(productCode, brand, productName, price, gauge);
        this.eraCode = eraCode;
    }

    public String getEraCode() {
        return eraCode;
    }

}
