package com.sheffieldtrains.domain.order;

public class OrderLine {
    private Integer lineNumber;
    private String productCode;
    private int quantity;
    private float price;

    public OrderLine(String productCode, int quantity, float price) {
        this.productCode = productCode;
        this.quantity = quantity;
        this.price = price;
    }


    public int getLineNumber() {
        return lineNumber;
    }

    public String getProductCode() {
        return productCode;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber=lineNumber;
    }

    public void setQuantity(int quantity) {
        this.quantity=quantity;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getPrice() {
        return price;
    }
}
