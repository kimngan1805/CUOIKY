package cuoiki.pizzaorderapp.Controller;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.scene.input.KeyEvent;
import org.mindrot.jbcrypt.BCrypt;
import cuoiki.pizzaorderapp.Model.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
public class CustomerLoginController implements Initializable {
    @FXML
    private Label labelError;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane SignIn_Page;

    @FXML
    private AnchorPane SignUpPage;

    @FXML
    private Hyperlink dangky;

    @FXML
    private PasswordField pass_2;
    @FXML
    private PasswordField confirm_pass;

    @FXML
    private PasswordField password;

    @FXML
    private Button sign_up_btn;

    @FXML
    private Button signin_btn;

    @FXML
    private Hyperlink signlink;

    @FXML
    private TextField username;

    @FXML
    private TextField username_2;
    private PreparedStatement preparedStatement;
    private Connection connect;
    private ResultSet resultSet;
    public void switchForm(ActionEvent event) {
        if(event.getSource() == signlink) {
            SignIn_Page.setVisible(true);
            SignUpPage.setVisible(false);
        } else if (event.getSource() == dangky){
            SignIn_Page.setVisible(false);
            SignUpPage.setVisible(true);
        }
    }
    public void login() {
        String sql = "select * from Customer where username_cus=? ";
        connect = DatabaseConnection.getConnection();
        String password2=password.getText();
        try {
            preparedStatement = connect.prepareStatement(sql);
            preparedStatement.setString(1, username.getText());
            resultSet = preparedStatement.executeQuery();
            Alert alert;
                if (resultSet.next()) {
                    String hashedPassword=resultSet.getString("pass_cus");
                    if (BCrypt.checkpw(password2, hashedPassword)) {
                        signin_btn.getScene().getWindow().hide();
                        // link để qua 1 trang mới sau khi login thành công
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/cuoiki/pizzaorderapp/View/DashboardCustomer.fxml"));
                        Parent root = loader.load();
                        Stage stage = new Stage();
                        Scene scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();
                    } else {
                        alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Sai tên đăng nhập hoặc mật khẩu!");
                        password.setText("");
                        password.requestFocus();
                        alert.showAndWait();
                    }

                }else{
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Không tìm thấy người dùng!");
                    username.setText("");
                    password.setText("");
                }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connect != null) connect.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private  Alert alert;
    public void create(){

        if(username_2.getText().isEmpty()||pass_2.getText().isEmpty()){
            alert= new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error message");
            alert.setHeaderText(null);
            alert.setContentText("Hãy điền vào những chỗ còn trống!");
            alert.showAndWait();
        }else{
            String hashedPassword = BCrypt.hashpw(pass_2.getText(), BCrypt.gensalt());
            String create="insert into Customer (username_cus, pass_cus)"+"values(?,?)";
            connect = DatabaseConnection.getConnection();
            try{
                preparedStatement=connect.prepareStatement(create);
                preparedStatement.setString(1,username_2.getText());
                preparedStatement.setString(2,hashedPassword);
                preparedStatement.executeUpdate();
                alert= new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information message");
                alert.setHeaderText(null);
                alert.setContentText("Tạo tài khoản thành công!");
                alert.showAndWait();
                username_2.setText("");
                pass_2.setText("");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
