package com.sheffieldtrains.domain.product;

public class RollingStock extends ProductDuringEra{
    private RollingStockType rollingStockType;

    public RollingStock(String productCode,
                        String brand,
                        String productName,
                        float price,
                        Gauge gauge,
                        int quantity,
                        String eraCode,
                        RollingStockType rollingStockType) {
        super(productCode, brand, productName, price, gauge, quantity, eraCode);
        this.rollingStockType = rollingStockType;
    }

    public RollingStock() {

    }

    public RollingStockType getRollingStockType() {
        return rollingStockType;
    }

    public void setRollingStockType(RollingStockType rollingStockType) {
        this.rollingStockType = rollingStockType;
    }
}
