package com.sheffieldtrains.service;

import com.sheffieldtrains.domain.order.Order;
import com.sheffieldtrains.domain.order.OrderLine;

import java.util.*;


public class OrderService {
    private static List<Order> ORDERS_FOR_STAFF_TO_PROCESS= new ArrayList<>();
    Map<Integer, Order> userPendingOrders=new HashMap<>();

    public boolean addToOrder(Integer userId, String productCode, int quantity) {
        boolean result=true;
        if (userId==null) throw new UnknownUserException("UserId cannot be null.");
        Order orderForUser=userPendingOrders.get(userId);
        //haven't created a pending order before
        //obtain a price
        float productPrice= getProductPrice(productCode);
        if (orderForUser==null) {
            //creat a new OrderLine;
            OrderLine orderLine= new OrderLine(productCode, quantity,  productPrice*quantity);
            Order order=new Order(userId, orderLine);
            userPendingOrders.put(userId, order);
        }
        else {
                //did the user have an orderLine of the same product, if yes, merge quantity.
                orderForUser.processNewOrderLine(productCode, quantity, productPrice);
        }
        return result;
    }

    private void processOrderLine(String productCode, int quantity, Order orderForUser, float productPrice) {
        OrderLine existingOrderLine= orderForUser.getExistingOrderLine(productCode);
        if (existingOrderLine!=null) {
            int newQuantity= quantity +existingOrderLine.getQuantity();
            existingOrderLine.setQuantity(newQuantity);
            existingOrderLine.setPrice(existingOrderLine.getPrice() + quantity * productPrice);
        }
    }

    private float getProductPrice(String productId) {
        //Todo: need to go to grab the price from ProductService;
        return 100;
    }
}
