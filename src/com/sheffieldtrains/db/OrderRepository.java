package com.sheffieldtrains.db;

import com.sheffieldtrains.domain.order.Order;
import com.sheffieldtrains.domain.order.OrderLine;
import com.sheffieldtrains.domain.product.*;
import com.sheffieldtrains.domain.user.Address;
import com.sheffieldtrains.domain.user.User;
import com.sheffieldtrains.domain.user.UserRole;
import com.sheffieldtrains.service.ProductService;

import java.sql.*;
import java.util.*;

public class OrderRepository extends Repository {


    private static String allPendingOrderSQL = """
                    SELECT
                        o.orderNumber,
                        o.orderDate,
                        o.status,
                        u.email AS userEmail,
                        u.forename AS userForename,
                        u.surname AS userSurname,
                        ur.roleName,
                        a.houseNumber,
                        a.roadName,
                        a.cityName,
                        a.postcode,
                        ol.lineNumber,
                        ol.productCode,
                        ol.quantity,
                        p.brand,
                        p.productName,
                        p.price,
                        p.gauge,
                        p.productType
                    FROM
                        team066.Order  o 
                    JOIN team066.User u ON o.userID = u.userID
                    JOIN team066.UserInRole  ur ON u.userId=ur.userId
                    JOIN team066.Address  a ON u.houseNumber = a.houseNumber AND u.postcode = a.postcode
                    JOIN team066.OrderLine  ol ON o.orderNumber = ol.orderNumber
                    JOIN team066.Product  p ON ol.productCode = p.productCode
                    WHERE
                        o.status = 'PENDING'
                    Order By o.orderNumber ASC;
                """;



    public static void persistOrder(Order order) {
        //Todo: to implement
    }

    public static List<Order> getAllOrders(String userId) {
        //Todo
        return null;
    }

    public static List<Order> getAllHistoricalOrders() {
        //Todo
        return null;
    }

    public static List<Order> getHistoricalOrdersFromUser(String userId) {
        //Todo
        return null;
    }

    public static List<Order> getAllOrdersToBeProcessed()  {
        try {
            Map<Integer, Order> orderMap = new HashMap<>();
            PreparedStatement stmt = getConnection().prepareStatement(allPendingOrderSQL);
            ResultSet resultSet = stmt.executeQuery();

            List<Order> orders = new ArrayList<>();
//            ProductSubclassCreator subclassCreator=new ProductSubclassCreator();


           /* PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                // Convert the list to an array
                Array array = connection.createArrayOf("INTEGER", productIds.toArray());

                // Set the array as a parameter
                preparedStatement.setArray(1, array);*/

            while (resultSet.next()) {
                // Create Order
                Order order = new Order();
                Long orderNumber=resultSet.getLong("orderNumber");
                order.setOrderNumber(orderNumber);
                order.setOrderDate(resultSet.getDate("orderDate"));
//                order.setStatus(OrderStatus.valueOf(status));

                // Create User
                User user = new User();
                user.setUserID(resultSet.getInt("userID"));
                user.setEmail(resultSet.getString("userEmail"));
                user.setForename(resultSet.getString("userEmail"));
                user.setSurname(resultSet.getString("userSurname"));
                user.addRole(UserRole.valueOf(resultSet.getString("roleName")));

                // Create Address
                Address address = new Address();
                address.setHouseNumber(resultSet.getString("houseNumber"));
                address.setRoadName(resultSet.getString("roadName"));
                address.setCityName(resultSet.getString("cityName"));
                address.setPostcode(resultSet.getString("postcode"));

                // Set address properties...
                user.setAddress(address);
                order.setUser(user);

                // Create OrderLines
                List<OrderLine> orderLines = new ArrayList<>();
                do {
                    OrderLine orderLine = new OrderLine();
                    orderLine.setLineNumber(resultSet.getInt("lineNumber"));
                    Product product =buildProduct(resultSet);
                    orderLine.setProduct(product);
                    orderLine.setQuantity(resultSet.getInt("quantity"));
                    orderLines.add(orderLine);
                } while (resultSet.next() && resultSet.getInt("orderNumber") == orderNumber);

                order.setOrderLines(orderLines);
                orders.add(order);
            }
            //get all products and relink them.

        }
        catch (SQLException ex) {
            throw new RuntimeException("DB Error, cannot get orders");
        }


        return null;
    }

    private static Product buildProduct(ResultSet resultSet) throws SQLException{
        // Create Product and set properties...
                            /*ol.lineNumber,
                            ol.productCode,
                            ol.quantity,
                            p.brand,
                            p.productName,
                            p.price,
                            p.gauge,
                            p.productType*/

                    /*String productCode,
                    String brand,
                    String productName,
                    float price,
                    Gauge gauge*/
        //the shared info for Product
        String  productCode= resultSet.getString("productCode");
       /* String  brand =resultSet.getString("brand");
        String  productName= resultSet.getString("productName");
        float   price=resultSet.getFloat("price");
        Gauge   gauge= Gauge.valueOf(resultSet.getString("gauge"));*/
        ProductType productType=ProductType.valueOf(resultSet.getString("productType"));
        ProductService.getProduct(productCode, productType);
        return null;
    }

}