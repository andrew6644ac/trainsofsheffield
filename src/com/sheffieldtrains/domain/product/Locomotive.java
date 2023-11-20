package com.sheffieldtrains.domain.product;

public class Locomotive extends ProductDuringEra {
    private DCCCode dccCode;

    public Locomotive(String productCode,
                      String brand,
                      String productName,
                      float price,
                      Gauge gauge,
                      int quantity,
                      String eraCode,
                      DCCCode dccCode) {
        super(productCode, brand, productName, price, gauge, quantity, eraCode);
        this.dccCode = dccCode;
    }

    public Locomotive() {
        super();
    }

    public DCCCode getDccCode() {
        return dccCode;
    }

    public void setDccCode(DCCCode dccCode) {
        this.dccCode=dccCode;
    }
}
