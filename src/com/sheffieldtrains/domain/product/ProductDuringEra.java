package com.sheffieldtrains.domain.product;

public class ProductDuringEra extends Product {
    protected String eraCode;

    public ProductDuringEra(String productCode,
                            String brand,
                            String productName,
                            float price,
                            Gauge gauge,
                            int quantity,
                            String eraCode) {
        super(productCode, brand, productName, price, gauge, quantity);
        this.eraCode = eraCode;
    }

    public ProductDuringEra() {
        super();
    }

    public String getEraCode() {
        return eraCode;
    }

    public void setEraCode(String eraCode) {
        this.eraCode=eraCode;
    }
}
