package com.sheffieldtrains.service;

import com.sheffieldtrains.db.OrderRepository;
import com.sheffieldtrains.domain.order.Order;
import com.sheffieldtrains.domain.order.OrderLine;
import com.sheffieldtrains.domain.order.OrderStatus;
import com.sheffieldtrains.domain.product.ProductType;
import com.sheffieldtrains.domain.user.User;

import java.util.*;

public class OrderService {
    private static List<Order> ORDERS_FOR_STAFF_TO_PROCESS= new ArrayList<>();
    private static Map<Integer, Order> userPendingOrders=new HashMap<>();

    public static boolean addToOrder(Integer userId, String productCode, ProductType productType, int quantity, float productPrice) {
        boolean result=true;
        if (userId==null) throw new UnknownUserException("UserId cannot be null.");
        Order orderForUser=userPendingOrders.get(userId);
        //haven't had a pending order at the moment
        //obtain product price
//        float productPrice= getProductPrice(productCode, productType);
        float orderLinePrice=productPrice*quantity;
        if (orderForUser==null) {
            //creat a new OrderLine;
            OrderLine orderLine= new OrderLine(productCode,   productType,  quantity,  orderLinePrice);
            Order order=new Order(userId, new Date(),  orderLine);
            userPendingOrders.put(userId, order);
        }
        else {
                //did the user have an orderLine of the same product, if yes, merge quantity.
                orderForUser.addOrderLine(productCode, productType, quantity, orderLinePrice);
        }
        return result;
    }


    public void modifyOrderLine(String userId, String productCode, int quantity) {
        Order order=userPendingOrders.get(userId);
        if (order==null) throw new UnknownOrderException();
        OrderLine orderLine= order.findOrderLine(productCode);
        if (orderLine==null)  throw new UnknownOrderLineException();
        if (quantity==0) {
            order.removeOrderLine(productCode);
        }
        else {
            orderLine.setQuantity(quantity);
            orderLine.setPrice(quantity* orderLine.getPrice());
        }
        if (order.isEmpty()){
                userPendingOrders.remove(userId);
            }
    }

    public List<Order> getHistoricalOrdersFromUser(Integer userId){
        return OrderRepository.getHistoricalOrdersFromUser(userId);
    }

    public void confirmOrder(Integer userId) {
        Order order=userPendingOrders.get(userId);
        if (userId==null) throw new OrderNotFoundException("The userId has no pending order to confirm");
        order.setStatus(OrderStatus.CONFIRMED);
        order.assignOrderLineIds();
        OrderRepository.saveOrder(order);
        //todo: need to rethink about the
        User user=new User();
        user.setUserID(userId);
        UserService.makeUserCustomer(user);
        //after confirmation, the user has no longer a pending order.
        userPendingOrders.remove(userId);
        //todo: ??ORDERS_FOR_STAFF_TO_PROCESS.add(order);
    }

    public static void confirmOrderForUser(Integer userId, Order order) {
     //   Order order=userPendingOrders.get(userId);
        if (userId==null) throw new OrderNotFoundException("The userId has no pending order to confirm");
        order.setStatus(OrderStatus.CONFIRMED);
        order.assignOrderLineIds();
        OrderRepository.saveOrder(order);
        //todo: need to rethink about the
        User user=new User();
        user.setUserID(userId);
        UserService.makeUserCustomer(user);
        //after confirmation, the user has no longer a pending order.
      //  userPendingOrders.remove(userId);
        //todo: ??ORDERS_FOR_STAFF_TO_PROCESS.add(order);
    }

    /*public boolean confirmOrder(Integer userId, Map<String, Integer> orderItems)  {
        if (userId==null||orderItems==null||orderItems.isEmpty()) {
            throw new IllegalOrderException();
        }
        Order order=null;
        for(Map.Entry<String, Integer> item: orderItems.entrySet()) {
                String productCode=item.getKey();
                Integer quantity=item.getValue();
                float price=*//*getProductPrice(productCode, null);*//* 0; //todo: sort out productType
                OrderLine orderLine= new OrderLine(productCode, quantity, price*quantity);
                order=new Order(userId, orderLine);
        }
        order.setStatus(OrderStatus.CONFIRMED);
        ORDERS_FOR_STAFF_TO_PROCESS.add(order);
        persist(order);
        return true;
    }*/


    public Order getPendingOrder(Integer userId){
        if (userId==null){
            throw new UnknownUserException("User Id cannot be null");
        }
       return userPendingOrders.get(userId);
    }

    public List<Order> getAllOrdersToBeFulfilled(){
        List<Order> orderList= OrderRepository.getAllOrdersToBeFulfilled();
        Comparator comparator= new Comparator<Order>() {
            @Override
            public int compare(Order order1, Order order2) {
                return order1.getOrderDate().compareTo(order2.getOrderDate());
            }
        };
        Collections.sort(orderList, comparator );
        return orderList;
    }

    public static void fulfillOrder(Long orderId){
        OrderRepository.fulfillOrder(orderId);
    }


    public static void rejectOrder(Long orderNumber) {
        OrderRepository.rejectOrder(orderNumber);
    }

    private static float getProductPrice(String productCode, ProductType productType) {
        //Todo: need to go to grab the price from ProductService;
        return ProductService.getProductPrice(productCode, productType);
    }


    public void deleteOrderByUserId(Integer userId){
        OrderRepository.deleteOrderByUserId(userId);
    }

    public void deleteOrderByOrderId(Long orderId){
        OrderRepository.deleteOrderByOrderId(orderId);
    }
}
