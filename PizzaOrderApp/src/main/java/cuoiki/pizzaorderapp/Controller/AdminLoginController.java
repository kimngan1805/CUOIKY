package cuoiki.pizzaorderapp.Controller;

import cuoiki.pizzaorderapp.HelloApplication;
import cuoiki.pizzaorderapp.Model.DatabaseConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class AdminLoginController implements Initializable {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private PasswordField password;

    @FXML
    private Button sign_btn;

    @FXML
    private TextField username;
    private PreparedStatement preparedStatement;
    private Connection connect;
    private ResultSet resultSet;
    public void login(){
        String sql="select * from Administrator where username_admin=? and pass_admin=?";
        DatabaseConnection connectNow= new DatabaseConnection();
        Connection connect=connectNow.getConnection();
        try{
            preparedStatement=connect.prepareStatement(sql);
            preparedStatement.setString(1,username.getText());
            preparedStatement.setString(2,password.getText());
            resultSet=preparedStatement.executeQuery();
            Alert alert;
            if(username.getText().isEmpty()||password.getText().isEmpty()){
                alert=new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Hãy điền vào những chỗ còn trống!");
                alert.showAndWait();
            }else{
                if (resultSet.next()){
                    alert=new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText(" Đăng nhập thành công!");
                    alert.showAndWait();
                    // ẩn trang login sau khi đăng nhập thành
                    sign_btn.getScene().getWindow().hide();
                    // link để qua 1 trang mới sau khi login thành công
                    // Load và hiển thị dashboard
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/cuoiki/pizzaorderapp/View/dashboard.fxml"));
                    Parent root = loader.load();
                    Stage stage= new Stage();
                    Scene scene= new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                }else{
                    alert=new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Sai tên đăng nhập hoặc mật khẩu!");
                    alert.showAndWait();
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

}