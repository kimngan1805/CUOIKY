package cuoiki.pizzaorderapp.Model;
import java.sql.Connection;
import java.sql.DriverManager;
public class DatabaseConnection {
    public Connection databaseLink;
    public Connection getConnection() {
        String databaseName = "PizzaManagement";
        String databaseUser = "root";
        String databasePassword = "Kimngan1805@";
        // sử dụng để kết nối với csdl
        String url = "jdbc:mysql://localhost/" + databaseName;
        try {
            // sử dụng để đăng kí jdbc driver với hệ thống
            Class.forName("com.mysql.cj.jdbc.Driver");
            // tạo 1 kết nối đến csdl với url tên người dùng và mật khẩu đc cung cấp
            databaseLink = DriverManager.getConnection(url, databaseUser, databasePassword);
            return databaseLink;
        } catch (Exception e) {
            // được sử dụng để in ra ngoại lệ và dòng mã tạo ra ngoại lệ đó
            e.printStackTrace();
        }
        return null;
    }
}
