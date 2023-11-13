package com.sheffieldtrains.domain.product;

public abstract class Product {
    protected String productCode;
    protected String brand;
    protected String productName;
    protected float price;
    protected Gauge gauge;

    public Product(String productCode,
                   String brand,
                   String productName,
                   float price,
                   Gauge gauge) {
        this.productCode = productCode;
        this.brand = brand;
        this.productName = productName;
        this.price = price;
        this.gauge = gauge;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Gauge getGauge() {
        return gauge;
    }

    public void setGauge(Gauge gauge) {
        this.gauge = gauge;
    }


}
