package com.sheffieldtrains.domain.product;

public class Locomotive extends ProductDuringEra {
    private DCCCode dccCode;

    public Locomotive(String productCode,
                      String brand,
                      String productName,
                      float price,
                      Gauge gauge,
                      String eraCode) {
        super(productCode, brand, productName, price, gauge, eraCode);
    }
    public DCCCode getDccCode() {
        return dccCode;
    }
}
