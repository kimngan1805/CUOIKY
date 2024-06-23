package cuoiki.pizzaorderapp.Model;
import cuoiki.pizzaorderapp.Controller.ProductData;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;
public class OrderDAO implements Serializable {
    private AlertMessage alert = new AlertMessage();


    public OrderDAO() {
    }

    private List<OrderData> orders = new ArrayList<>();

    private PreparedStatement preparedStatement;

    public void addOrder(String username, List<ProductData> productList, String orderType, double totalPrice, String product_name) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        String insertOrder = "INSERT INTO Orders (username_customer, order_type, total_price,order_date,product_name ) VALUES (?, ?, ?,?,?)";
        String insertOrderDetails = "INSERT INTO OrderDetails (orderId, productId, quantity, price) VALUES (?, ?, ?, ?)";

        try {
            java.util.Date date= new java.util.Date();
            java.sql.Date sqlDate= new java.sql.Date(date.getTime());
            PreparedStatement preparedStatement = connectDB.prepareStatement(insertOrder, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, orderType);
            preparedStatement.setDouble(3, totalPrice);
            preparedStatement.setString(4, String.valueOf(sqlDate));
            preparedStatement.setString(5, product_name);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating order failed, no rows affected.");
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int orderId = generatedKeys.getInt(1);

                    for (ProductData product : productList) {
                        PreparedStatement orderDetailsStatement = connectDB.prepareStatement(insertOrderDetails);
                        orderDetailsStatement.setInt(1, orderId);
                        orderDetailsStatement.setString(2, product.getProduct_id());
                        orderDetailsStatement.setInt(3, product.getQuantity());
                        orderDetailsStatement.setDouble(4, product.getProduct_price());

                        orderDetailsStatement.executeUpdate();
                    }
                } else {
                    throw new SQLException("Creating order failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
