package com.sheffieldtrains.domain.order;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order {
    private Long orderNumber;
    private Date orderDate;
    private OrderStatus status=OrderStatus.PENDING;
    private int orderLineNumberCounter=1;
    private Integer userId;
    private List<OrderLine> orderLines=new ArrayList<>();

    public Order(Integer userId, OrderLine orderLine) {
        this.userId = userId;
        orderLine.setLineNumber(orderLineNumberCounter++);
        this.orderLines.add(orderLine);
    }

    public OrderLine getExistingOrderLine(String productCode) {
        //Todo: go through all my orderlines, to see if it already has an orderline for the productCode.
        return null;
    }

    public void processNewOrderLine(String productCode,
                                    int quantity,
                                    float productPrice) {
        OrderLine existingOrderLine= getExistingOrderLine(productCode);
        //if it has an orderLine for the same product, merge quantity and price
        if (existingOrderLine!=null) {
            int newQuantity= quantity +existingOrderLine.getQuantity();
            existingOrderLine.setQuantity(newQuantity);
            existingOrderLine.setPrice(existingOrderLine.getPrice() + quantity * productPrice);
        }
        else {//if no existing orderLine, creat a new orderLine and add it to order.
            OrderLine newOrderLine=new OrderLine(productCode, quantity, productPrice);
            newOrderLine.setLineNumber(orderLineNumberCounter++);
            orderLines.add(newOrderLine);
        }
    }
}



