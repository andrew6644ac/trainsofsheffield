package com.sheffieldtrains.db;

import com.sheffieldtrains.domain.order.Order;
import com.sheffieldtrains.domain.order.OrderLine;
import com.sheffieldtrains.domain.order.OrderStatus;
import com.sheffieldtrains.domain.product.*;
import com.sheffieldtrains.domain.user.User;
import com.sheffieldtrains.service.ProductService;
import com.sheffieldtrains.service.UnknownUserException;
import com.sheffieldtrains.service.UserService;

import java.sql.*;
import java.util.*;
import java.util.Date;

public class OrderRepository extends Repository {
    private static String queryClausePlaceHolder="(clause)";
    private static final String GET_ORDERS_BY_USER_SQL = """
                    SELECT
                        o.orderNumber,
                        o.orderDate,
                        o.status,
                        o.userID,
                        ol.lineNumber,
                        ol.productCode,
                        ol.quantity,
                        p.brand,
                        p.productName,
                        p.price,
                        p.gauge,
                        p.productType
                    FROM
                         team066.Order1  o
                    JOIN team066.OrderLine  ol ON o.orderNumber = ol.orderNumber
                    JOIN team066.Product  p ON ol.productCode = p.productCode
                    WHERE
                        o.userID = ?
                    Order By o.orderNumber, ol.lineNumber;
""";
    private static String ALL_ORDER_SQL = """
                    SELECT
                        o.orderNumber,
                        o.orderDate,
                        o.status,
                        u.userID,
                        u.email ,
                        ol.lineNumber,
                        ol.productCode,
                        ol.quantity,               
                        p.productType,
                        p.price,
                        p.productType
                    FROM
                         team066.Order1  o 
                    JOIN team066.User u ON o.userID = u.userID               
                    JOIN team066.OrderLine  ol ON o.orderNumber = ol.orderNumber
                    JOIN team066.Product  p ON ol.productCode = p.productCode
                        (clause)
                    Order By o.orderNumber ASC;
                """;

    private static final String DELETE_ORDER_lINE_BY_USER_ID_SQL = """
                    DELETE FROM team066.OrderLine
                    WHERE  orderNumber IN (SELECT orderNumber FROM Order1 WHERE userId=?)
                    """;
                    
    private static final String DELETE_ORDER_BY_USER_ID_SQL = """
                    DELETE FROM team066.Order1
                    WHERE  userID=?
                   """;


    private static String INSERT_ORDER_SQL= """
            INSERT INTO team066.Order1 (orderDate, status, userID)
            VALUES (?, ?, ?)
            """;

    private static String INSERT_ORDER_LINE_SQL= """
            INSERT INTO team066.OrderLine (lineNumber, orderNumber, productCode, quantity)
            VALUES (?, ?, ?, ?)
            """;

    private static String DELETE_ORDER_lINE_BY_ORDER_ID_SQL="""
        DELETE FROM team066.OrderlINE
        WHERE  orderNumber=?
""";
    private static String DELETE_ORDER_BY_ORDER_ID_SQL="""
        DELETE FROM team066.Order1
        WHERE  orderNumber=?
""";

    public static void saveOrder(Order order) {
       try (Connection conn=getConnection();
            PreparedStatement orderStmt=conn.prepareStatement(INSERT_ORDER_SQL, Statement.RETURN_GENERATED_KEYS);
            PreparedStatement orderLineStmt=conn.prepareStatement(INSERT_ORDER_LINE_SQL))  {
           conn.setAutoCommit(false);
           java.util.Date utilDate = order.getOrderDate();
           // Convert java.util.Date to java.sql.Date
           java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
           orderStmt.setDate(1, /*(java.sql.Date)*/ sqlDate); /*order parameters: orderDate, status, userID*/
           orderStmt.setString(2, order.getStatus().toString());
           orderStmt.setInt(3, order.getUserId());
           int affectedRows =orderStmt.executeUpdate();
           Integer generatedOrderId=null;
           if (affectedRows>0) {
               try (ResultSet generatedKeys = orderStmt.getGeneratedKeys()) {
                   if (generatedKeys.next()) {
                       /* Integer generatedUserId = generatedKeys.getInt("userID");*/
                        generatedOrderId = generatedKeys.getInt(1);
                   }
               }
           }
           //todo: need to grab the order number just generated.
           for (OrderLine orderLine: order.getOrderLines()){
               Long orderNumber=order.getOrderNumber();
               /*lineNumber, orderNumber, productCode, quantity*/
               orderLineStmt.setInt(1, orderLine.getLineNumber());
               orderLineStmt.setInt(2,generatedOrderId );
               orderLineStmt.setString(3, orderLine.getProductCode());
               orderLineStmt.setInt(4, orderLine.getQuantity());
               orderLineStmt.executeUpdate();
           }
           conn.commit();
       } catch (SQLException e) {
           throw new RuntimeException(e);
       }
    }

    public static List<Order> getAllNonPendingOrders(Integer userId) {
        ResultSet resultSet = null;
        List<Order> orders=new ArrayList<>();
        String whereClause = " WHERE o.userID=? AND o.status!='PENDING'";
        String sqlString = ALL_ORDER_SQL.replace(queryClausePlaceHolder, whereClause);
        try (PreparedStatement stmt = getConnection().prepareStatement(sqlString)) {
            stmt.setInt(1, userId);
            resultSet = stmt.executeQuery();
            orders= buildOrderList(resultSet);
        } catch (SQLException ex) {
            throw new RuntimeException("Database errors when trying to get all orders for userID:" + userId);
        }
        return orders;
    }

    public static Order getOrder(Long orderId) {
        ResultSet resultSet = null;
        Order order=null;
        String whereClause = " WHERE o.orderNumber=? ";
        String sqlString = ALL_ORDER_SQL.replace(queryClausePlaceHolder, whereClause);
        try (PreparedStatement stmt = getConnection().prepareStatement(sqlString)) {
            stmt.setLong(1, orderId);
            resultSet = stmt.executeQuery();
            List<Order> orders= buildOrderList(resultSet);
            if (orders.isEmpty()) {
                throw new RuntimeException("Cannot find order with id: "+ orderId);
            }
            order=orders.get(0);
        } catch (SQLException ex) {
            throw new RuntimeException("Database errors when trying to get all orders for userID:" + orderId);
        }
        return order;
    }


    private static List<Order> buildOrderList(ResultSet resultSet) {
        List<Order> orders=new ArrayList<>();
        long currentOrderNumber=-1;
        Order order=null;
        try {
            while (resultSet.next()) {
                    /*o.orderNumber,
                        o.orderDate,
                        o.status,
                        u.userID,
                        u.email ,
                        ol.lineNumber,
                        ol.productCode,
                        ol.quantity,
                        p.productType,
                        p.price,
                        p.productType
                        */
                Long orderNumber = resultSet.getLong("orderNumber");
                if (orderNumber!=currentOrderNumber) {
                    Date orderDate = resultSet.getDate("orderDate");
                    OrderStatus status = OrderStatus.valueOf(resultSet.getString("status"));
                    int userID = resultSet.getInt("userID");
                    String email = resultSet.getString("email");
                    order = new Order();
                    order.setOrderNumber(orderNumber);
                    order.setOrderDate(orderDate);
                    order.setStatus(status);
                    User user = UserService.getUser(email);
                    order.setUser(user);
                    orders.add(order);
                    currentOrderNumber=orderNumber;
                }
                /*do {*/
                    OrderLine orderLine = new OrderLine();
                    orderLine.setLineNumber(resultSet.getInt("lineNumber"));
                    orderLine.setProductCode(resultSet.getString("productCode"));
                    orderLine.setProductType(ProductType.valueOf(resultSet.getString("productType").toUpperCase(Locale.ROOT)));
                   // Product product = ProductService
                    //todo: revisit this code.  orderLine.setProduct(product);
                    int quantity= resultSet.getInt("quantity");
                    float price = resultSet.getFloat("price");
                    orderLine.setQuantity(quantity);
                    orderLine.setPrice(quantity*price);
//                    orderLines.add(orderLine);
         /*       } while (resultSet.next() && resultSet.getLong("orderNumber") == orderNumber);*/
                /*order.setOrderLines(orderLines);*/
//                orders.add(order);
                    order.getOrderLines().add(orderLine);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return orders;
    }

    public static List<Order> getAllHistoricalOrders(Integer userId) {
            ResultSet resultSet = null;
            List<Order> orders=new ArrayList<>();
            String whereClause = " WHERE o.userID=? ";
            String sqlString = ALL_ORDER_SQL.replace(queryClausePlaceHolder, whereClause);
            try (PreparedStatement stmt = getConnection().prepareStatement(sqlString)) {
                stmt.setInt(1, userId);
                resultSet = stmt.executeQuery();
                orders= buildOrderList(resultSet);
            } catch (SQLException ex) {
                throw new RuntimeException("Database errors when trying to get all orders from userID:" + userId);
            }
            return orders;
    }

    public static List<Order> getHistoricalOrdersFromUser(Integer userId) {
        //Todo
        return getAllNonPendingOrders(userId);
    }

    public static List<Order> getAllOrdersToBeFulfilled()  {
        ResultSet resultSet =null;
        String whereClause=" WHERE o.status = 'CONFIRMED' ";
        List<Order> orders=new ArrayList<>();
        String sqlString = ALL_ORDER_SQL.replace(queryClausePlaceHolder, whereClause);
        try (PreparedStatement stmt = getConnection().prepareStatement(sqlString)) {
            resultSet = stmt.executeQuery();
            orders = buildOrderList(resultSet);
        }
        catch (SQLException ex) {
            new RuntimeException("Database errors when trying to get all pending orders" );
        }
        return orders;
    }

    public static List<Order> getAllOrders()  {
        ResultSet resultSet =null;
        String whereClause=" ";
        List<Order> orders=new ArrayList<>();
        String sqlString = ALL_ORDER_SQL.replace(queryClausePlaceHolder, whereClause);
        try (PreparedStatement stmt = getConnection().prepareStatement(sqlString)) {
            resultSet = stmt.executeQuery();
            orders = buildOrderList(resultSet);
        }
        catch (SQLException ex) {
            new RuntimeException("Database errors when trying to get all pending orders" );
        }
        return orders;
    }

    public static void deleteOrderByUserId(Integer userId) {
        if (userId==null){
            throw new UnknownUserException("UserId is null or empty.");
        }
        try(Connection conn=getConnection();
            PreparedStatement deleteOrderLineStmt=getConnection().prepareStatement(DELETE_ORDER_lINE_BY_USER_ID_SQL);
            PreparedStatement deleteOrderStmt=getConnection().prepareStatement(DELETE_ORDER_BY_USER_ID_SQL)
        ){
            List<Order> orders= getAllNonPendingOrders(userId);
            conn.setAutoCommit(false);
            for(Order order: orders) {
                deleteOrderLineStmt.setInt(1, userId);
                deleteOrderLineStmt.executeUpdate();
                deleteOrderStmt.setInt(1, userId);
            }
            conn.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleteOrderByOrderId(Long orderId) {
        try(Connection conn=getConnection();
            PreparedStatement deleteOrderLineStmt=getConnection().prepareStatement(DELETE_ORDER_lINE_BY_ORDER_ID_SQL);
            PreparedStatement deleteOrderStmt=getConnection().prepareStatement(DELETE_ORDER_BY_ORDER_ID_SQL)
        ){
            conn.setAutoCommit(false);
            deleteOrderLineStmt.setLong(1, orderId);
            deleteOrderStmt.setLong(1, orderId);
            deleteOrderLineStmt.executeUpdate();
            deleteOrderStmt.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void fulfillOrder(Long orderId) {
        if (orderId==null){
            throw new RuntimeException("Cannot find order to fulfill, orderId is null");
        }
        Order order =getOrder(orderId);
        String productUpdateSql="UPDATE team066.Product SET quantity=? WHERE productCode=?";
        String fulfillSQL="UPDATE team066.Order1 SET status='FULFILLED' WHERE orderNumber=? ";
        if (order!=null) {
            List<OrderLine> orderLines=order.getOrderLines();
            try(Connection conn=getConnection();
//
                PreparedStatement productUpdateStmt=conn.prepareStatement(productUpdateSql);
                PreparedStatement orderUpdateStmt=conn.prepareStatement(fulfillSQL);
            ) {
                conn.setAutoCommit(false);
                for (OrderLine orderLine : orderLines) {
                    int orderQuantity = orderLine.getQuantity();
                    Product product = ProductService.getProduct(orderLine.getProductCode(), orderLine.getProductType());
                    int productQuantity = product.getQuantity();
                    if (orderQuantity > productQuantity) {
                        throw new OrderFullfillmentException("Order cannot be fulfilled due to insufficient stock for: " + orderLine.getProductCode());
                    }
                    int newQuantity = productQuantity - orderQuantity;
                    product.setQuantity(newQuantity);
                    productUpdateStmt.setInt(1, newQuantity);
                    productUpdateStmt.setString(2, product.getProductCode());
                    productUpdateStmt.executeUpdate();
                }
                orderUpdateStmt.setLong(1, orderId);
                orderUpdateStmt.executeUpdate();
                conn.commit();
            }
            catch (SQLException e) {
                    throw new RuntimeException("Database Error error when trying to fulfill the order: " + orderId);
                }
            }
        }

    public static void rejectOrder(Long orderNumber) {
        String fulfillSQL="UPDATE team066.Order1 SET status='REJECTED' WHERE orderNumber=? ";
        try(Connection conn=getConnection();
            PreparedStatement stmt=conn.prepareStatement(fulfillSQL)){
            stmt.setLong(1, orderNumber);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}