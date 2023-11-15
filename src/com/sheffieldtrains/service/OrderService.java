package com.sheffieldtrains.service;

import com.sheffieldtrains.db.OrderRepository;
import com.sheffieldtrains.domain.order.Order;
import com.sheffieldtrains.domain.order.OrderLine;
import com.sheffieldtrains.domain.order.OrderStatus;

import java.util.*;

public class OrderService {
    private static List<Order> ORDERS_FOR_STAFF_TO_PROCESS= new ArrayList<>();
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
                orderForUser.addOrderLine(productCode, quantity, productPrice);
        }
        return result;
    }

    public void modifyOrderLine(String userId, String productCode, int quantity) {
        Order order=userPendingOrders.get(userId);
        if (order==null) throw new UnknownOrderException();
        OrderLine orderLine= order.findOrderLine(productCode);
        if (orderLine==null)  throw new UnknownOrderLineException();
        order.removeOrderLine(productCode);
        if (order.isEmpty()){
                userPendingOrders.remove(userId);
            }
    }


    public List<Order> getAllHistoricalOrders(){
        return OrderRepository.getAllHistoricalOrders();
    }

    public List<Order> getHistoricalOrdersFromUser(String userId){
        return OrderRepository.getHistoricalOrdersFromUser(userId);
    }

    public void confirmOrder(Integer userId) {
        Order order=userPendingOrders.get(userId);
        if (userId==null) throw new OrderNotFoundException("The userId has no pending order to confirm");
        order.setStatus(OrderStatus.CONFIRMED);
        order.assignOrderLineIds();
        OrderRepository.persistOrder(order);
        ORDERS_FOR_STAFF_TO_PROCESS.add(order);
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


    public List<Order> getAllOrdersToBeProcessed(){
        return OrderRepository.getAllOrdersToBeProcessed();
    }

    public void fulfillOrder(Long orderId){

        //Todo:
        //loop through orders in the queue, change their status to fullfilled, and remove it from the queue.
        //archive the changed order into db.
    }

    private float getProductPrice(String productCode) {
        //Todo: need to go to grab the price from ProductService;
        return ProductService.getProductPrice(productCode);
    }

    private void persist(Order order) {
        OrderRepository.persistOrder(order);
    }

}
