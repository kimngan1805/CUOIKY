package cuoiki.pizzaorderapp.Controller;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

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
    public void login(){
        String sql="select * from LoginCustomer where username=? and password=?";
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
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            }else{
                if (resultSet.next()){
                    alert=new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText(" Successfully Login !");
                    alert.showAndWait();
                    // ẩn trang login sau khi đăng nhập thành
                    signin_btn.getScene().getWindow().hide();
                    // link để qua 1 trang mới sau khi login thành công
                    Parent root= FXMLLoader.load(getClass().getResource("View/hello-view.fxml"));
                    Stage stage= new Stage();
                    Scene scene= new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                }else{
                    alert=new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Wrong username/password");
                    alert.showAndWait();
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private  Alert alert;
    public void create(){

        if(username_2.getText().isEmpty()||pass_2.getText().isEmpty()||confirm_pass.getText().isEmpty()){
            alert= new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
        }else{
            String create="insert into LoginCustomer (username, password,confirm_password)"+"values(?,?,?)";
            DatabaseConnection connectNow= new DatabaseConnection();
            Connection connect=connectNow.getConnection();
            try{
                preparedStatement=connect.prepareStatement(create);
                preparedStatement.setString(1,username_2.getText());
                preparedStatement.setString(2,pass_2.getText());
                preparedStatement.setString(3,confirm_pass.getText());
                preparedStatement.executeUpdate();
                alert= new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information message");
                alert.setHeaderText(null);
                alert.setContentText("Create account is successfully!");
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
