package cuoiki.pizzaorderapp.Controller;
import java.io.Serializable;
import java.util.Date;

public class ProductData implements Serializable{
    private String product_name;
    private double product_price;
    private String product_image;
    private String product_id;
    private int quantity;
    private double totalBill; // Tổng hoá đơn
    private String orderId; // Mã đơn hàng
    private String orderType;
    private String id_order;
    private String id_customer;
    private Date date;
    public ProductData(String product_id,String product_name,String product_image, double product_price,int quantity ){
        this.product_id=product_id;
        this.product_name=product_name;
        this.product_image=product_image;
        this.product_price=product_price;
        this.quantity=quantity;
    }
    public ProductData(String product_id,String product_name, double product_price ){
        this.product_id=product_id;
        this.product_name=product_name;
        this.product_price=product_price;
    }
    public ProductData(String product_id,String product_name, double product_price,String product_image ){
        this.product_id=product_id;
        this.product_name=product_name;
        this.product_price=product_price;
        this.product_image=product_image;
    }

    public ProductData(String product_id,String product_name, double product_price,int quantity) {
        this.product_id=product_id;
        this.product_name=product_name;
        this.product_price=product_price;
        this.quantity=quantity;
    }

    public double getTotalBill() {
        return totalBill;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public void setTotalBill(double totalBill) {
        this.totalBill = totalBill;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getId_customer() {
        return id_customer;
    }

    public String getId_order() {
        return id_order;
    }

    public void setId_customer(String id_customer) {
        this.id_customer = id_customer;
    }

    public void setId_order(String id_order) {
        this.id_order = id_order;
    }

    public ProductData(){}
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getProduct_image() {
        return product_image;
    }

    public String getProduct_name() {
        return product_name;
    }

    public Double getProduct_price() {
        return product_price;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public void setProduct_price(double product_price) {
        this.product_price = product_price;
    }


}
