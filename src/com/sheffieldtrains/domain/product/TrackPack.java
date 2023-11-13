package com.sheffieldtrains.domain.product;

public class TrackPack extends Product{
    private String contents;

    public TrackPack(String productCode,
                     String brand,
                     String productName,
                     float price,
                     Gauge gauge,
                     String contents) {
        super(productCode, brand, productName, price, gauge);
        this.contents = contents;
    }

    public String getContents() {
        return contents;
    }
}
