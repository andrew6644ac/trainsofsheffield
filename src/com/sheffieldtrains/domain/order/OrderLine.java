package com.sheffieldtrains.domain.order;

import com.sheffieldtrains.domain.product.Product;
import com.sheffieldtrains.domain.product.ProductType;

import java.util.Objects;

public class OrderLine {

    private String productCode;
    private Integer lineNumber;
//    private String productCode;

   /* private Product product;*/
    private int quantity;
    private float price;

    private ProductType productType;

    public OrderLine(String productCode,  ProductType productType, int quantity, float price) {
        this.productCode = productCode;
       /* this.product=new Product(productCode, null, null, price, null, null);*/
        this.quantity = quantity;
        this.price = price;
        this.productType=productType;
    }

    public OrderLine() {}

    public OrderLine(String productCode, Integer lineNumber, int quantity, float price) {
        this.productCode = productCode;
        this.lineNumber = lineNumber;
        this.quantity = quantity;
        this.price = price;
    }

    public OrderLine(String productCode, int quantity, float price) {
        this.productCode = productCode;
        this.quantity = quantity;
        this.price = price;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public String getProductCode() {
//        return productCode;
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

    public Product getProduct(){
        //todo: if we don't need Product, just productCode and type??
        return null;
    }

    /*public void setProduct(Product product) {
        this.product=product;
    }*/

    public void setProductCode(String productCode){
        this.productCode=productCode;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderLine)) return false;
        OrderLine orderLine = (OrderLine) o;
        return quantity == orderLine.quantity && Float.compare(orderLine.price, price) == 0 && productCode.equals(orderLine.productCode) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(productCode, quantity, price);
    }
}
