package com.sheffieldtrains.domain.product;

public class LocomotiveInTrainSet {
    private Locomotive locomotive;
    private int quantity;

    public Locomotive getLocomotive() {
        return locomotive;
    }

    public int getQuantity() {
        return quantity;
    }

    public LocomotiveInTrainSet(Locomotive locomotive, int quantity) {
        this.locomotive = locomotive;
        this.quantity = quantity;
    }
}
