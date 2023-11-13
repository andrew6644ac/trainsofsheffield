package com.sheffieldtrains.domain.product;

public class RollingStock extends ProductDuringEra{
    private RollingStockType rollingStockType;

    public RollingStock(String productCode,
                        String brand,
                        String productName,
                        float price,
                        Gauge gauge,
                        String eraCode,
                        RollingStockType rollingStockType) {
        super(productCode, brand, productName, price, gauge, eraCode);
        this.rollingStockType = rollingStockType;
    }

    public RollingStockType getRollingStockType() {
        return rollingStockType;
    }
}
