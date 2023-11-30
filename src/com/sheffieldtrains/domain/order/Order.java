package com.sheffieldtrains.domain.order;

import com.sheffieldtrains.domain.product.ProductType;
import com.sheffieldtrains.domain.user.User;
import com.sheffieldtrains.service.UnknownOrderLineException;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class Order {
    private Long orderNumber;
    private Date orderDate;
    private OrderStatus status=OrderStatus.PENDING;
    private int orderLineNumberCounter=1;
    private Integer userId;

    //only populated when an order is created from DB
    private User user;

    private List<OrderLine> orderLines=new ArrayList<>();

    public Order(){}

    /*public Order(Integer userId, OrderLine orderLine) {
        this.userId = userId;
        //orderLine.setLineNumber(orderLineNumberCounter++);
        this.orderLines.add(orderLine);
    }*/

    public Order(Integer userId, Date orderDate, String productCode, ProductType productType,  int quantity ,  float productPrice) {
        this.userId = userId;
        OrderLine orderLine= new OrderLine(productCode, quantity,  productPrice*quantity);
        this.orderLines.add(orderLine);
    }

    public Order(Integer userId, Date orderDate, OrderLine orderLine) {
        this.userId = userId;
        this.orderDate=orderDate;
        this.orderLines.add(orderLine);
    }

    public Long getOrderNumber() {
        return orderNumber;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public Integer getUserId() {
       return user!=null? user.getUserID(): userId;
    }

    public List<OrderLine> getOrderLines() {
        return orderLines;
    }

    public void addOrderLine(String productCode,
                             ProductType productType,
                             int quantity,
                             float productPrice) {
        OrderLine existingOrderLine= findOrderLine(productCode);
        //if it has an orderLine for the same product, merge quantity and price
        if (existingOrderLine!=null) {
            int newQuantity= quantity +existingOrderLine.getQuantity();
            existingOrderLine.setQuantity(newQuantity);
            existingOrderLine.setPrice(existingOrderLine.getPrice() + quantity * productPrice);
        }
        else {//if no existing orderLine, creat a new orderLine and add it to order.
            OrderLine newOrderLine=new OrderLine(productCode, productType, quantity, productPrice);
            //newOrderLine.setLineNumber(orderLineNumberCounter++);
            orderLines.add(newOrderLine);
        }
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public OrderLine findOrderLine(String proudctCode) {
        for(OrderLine orderLine: orderLines){
            if (orderLine.getProductCode().equals(proudctCode)){
                return orderLine;
            }
        }
        return null;
    }

    public void removeOrderLine(String productCode) {
        Iterator<OrderLine> iterator = orderLines.iterator();
        while (iterator.hasNext()){
            if(iterator.next().getProductCode().equals(productCode)){
                iterator.remove();
                return;
            }
        }
    }

    public void modifyOrderLine(String productCode, int quantity, float price){
        OrderLine orderLine= findOrderLine(productCode);
        if (orderLine==null)  throw new UnknownOrderLineException();
        if (quantity==0) {
                removeOrderLine(productCode);
        }
        else {
                orderLine.setQuantity(quantity);
        }
    }

    public boolean isEmpty() {
        return orderLines.isEmpty();
    }

    //assign line number to each OrderLine which will be saved in DB.
    public void assignOrderLineIds() {
        for(OrderLine orderLine: orderLines){
            orderLine.setLineNumber(orderLineNumberCounter);
            orderLineNumberCounter++;
        }
    }

    public void setOrderNumber(Long orderNumber) {
        this.orderNumber= orderNumber;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate=orderDate;
    }

    public void setUser(User user) {
        this.user=user;
    }

    public void setOrderLines(List<OrderLine> orderLines) {
        this.orderLines=orderLines;
    }

    public User getUser() {
        return user;
    }
}



