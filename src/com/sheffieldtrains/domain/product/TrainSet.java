package com.sheffieldtrains.domain.product;

public class TrainSet extends ProductDuringEra{
    private String contents;

    public TrainSet(String productCode,
                    String brand,
                    String productName,
                    float price,
                    Gauge gauge,
                    String eraCode,
                    String contents) {
        super(productCode, brand, productName, price, gauge, eraCode);
        this.contents = contents;
    }

    public String getContents() {
        return contents;
    }
}
