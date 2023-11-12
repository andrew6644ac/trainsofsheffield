package com.sheffieldtrains.service;

import com.sheffieldtrains.db.OrderRepository;
import com.sheffieldtrains.domain.order.Order;
import com.sheffieldtrains.domain.order.OrderLine;
import com.sheffieldtrains.domain.order.OrderStatus;
import java.util.*;

public class OrderService {
    private static List<Order> ORDERS_FOR_STAFF_TO_PROCESS= new ArrayList<>();
    private static ProductService PRODUCT_SERVICE=new ProductService();
    private  Map<Integer, Order> userPendingOrders=new HashMap<>();

    public boolean addToOrder(Integer userId, String productCode, int quantity) {
        boolean result=true;
        if (userId==null) throw new UnknownUserException("UserId cannot be null.");
        Order orderForUser=userPendingOrders.get(userId);
        //haven't created a pending order before
        //obtain product price
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

    public boolean modifyOrderLine(String userId, String productCode, int quantity) {
        //Todo: modify orderline, if resulted empty order, cancel it.
        return true;
    }


    public List<Order> getHistoricalOrder(){
        //Todo:
        return null;
    }


    public void confirmOrder(Integer userId) {
        //Todo:
        //get the order for the user;
        //change the status to confirmed
        //move to staff processing queue
        //persist
    }

    /*public boolean confirmOrder(Integer userId, Map<String, Integer> orderItems)  {
        if (userId==null||orderItems==null||orderItems.isEmpty()) {
            throw new IllegalOrderException();
        }
        Order order=null;
        for(Map.Entry<String, Integer> item: orderItems.entrySet()) {
                String productCode=item.getKey();
                Integer quantity=item.getValue();
                float price=getProductPrice(productCode);
                OrderLine orderLine= new OrderLine(productCode, quantity, price*quantity);
                order=new Order(userId, orderLine);
        }
        order.setStatus(OrderStatus.CONFIRMED);
        ORDERS_FOR_STAFF_TO_PROCESS.add(order);
        persist(order);
        return true;
    }*/


    public Order getPendingOrder(String userId){
        if (userId==null){
            throw new UnknownUserException("User Id cannot be null");
        }
       return userPendingOrders.get(userId);
    }

    public List<Order> getAllOrders(String userId){
       return OrderRepository.getAllOrders(userId);
    }

    public List<Order> getAllOrdersToBeProcessed(){
        return ORDERS_FOR_STAFF_TO_PROCESS;
    }

    public void fulfillOrder(Long orderId){
        //Todo:
        //loop through orders in the queue, fulfill them in turn. change their status to fullfilled, and remove it from the queue.
        //archive the changed order into db.
    }

    private float getProductPrice(String productId) {
        //Todo: need to go to grab the price from ProductService;
        return PRODUCT_SERVICE.getProductPrice(productId);
    }

    private void persist(Order order) {
        OrderRepository.persistOrder(order);
    }

}
