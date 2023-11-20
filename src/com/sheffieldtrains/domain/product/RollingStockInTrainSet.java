package com.sheffieldtrains.domain.product;

public class RollingStockInTrainSet {
    private RollingStock rollingStock;
    private int quantity;

    public RollingStockInTrainSet(RollingStock rollingStock, int quantity) {
        this.rollingStock = rollingStock;
        this.quantity = quantity;
    }

    public RollingStock getRollingStock() {
        return rollingStock;
    }

    public int getQuantity() {
        return quantity;
    }
}
