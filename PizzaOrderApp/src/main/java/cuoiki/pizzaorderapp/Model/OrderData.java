package cuoiki.pizzaorderapp.Model;

import cuoiki.pizzaorderapp.Controller.ProductData;

import java.io.Serializable;
import java.util.Date;

public class OrderData extends ProductData implements Serializable {
    private String orderId;
    private int quantity;
    private double totalPrice;
    private String username;
    private String product_name;
    private String orderType;
    private Date orderDate;
    public OrderData(String orderId,String product_name,int quantity, double totalPrice){
        this.orderId=orderId;
        this.product_name=product_name;
        this.quantity=quantity;
        this.totalPrice=totalPrice;
    }

    @Override
    public String getProduct_name() {
        return product_name;
    }

    @Override
    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public OrderData(String orderId, int quantity, double totalPrice, String username, String orderType, Date orderDate) {
        this.orderId = orderId;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.username = username;
        this.orderType = orderType;
        this.orderDate = orderDate;
    }

    public OrderData(String id) {

    }

    // Getters and setters
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }
}
