package cuoiki.pizzaorderapp.Model;
import java.sql.Connection;
import java.sql.DriverManager;
public class DatabaseConnection {
    public static Connection getConnection() {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/orderpizzamanagement", "root", "Qnhi78py@#");
            return connect;
        }catch(Exception e) {e.printStackTrace();}
        return null;
    }
}