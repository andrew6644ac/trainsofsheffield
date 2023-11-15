package com.sheffieldtrains.domain.order;

import com.sheffieldtrains.domain.product.Product;

public class OrderLine {
    private Integer lineNumber;
//    private String productCode;

    private Product product;
    private int quantity;
    private float price;

    public OrderLine(String productCode, int quantity, float price) {
//        this.productCode = productCode;
        this.product=new Product(productCode, null, null, price, null, resultSet.getString("productType"));
        this.quantity = quantity;
        this.price = price;
    }

    public OrderLine() {}

    public int getLineNumber() {
        return lineNumber;
    }

    public String getProductCode() {
//        return productCode;
        return product.getProductCode();
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

    public Product getProduct(){
        return product;
    }
}
