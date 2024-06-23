package cuoiki.pizzaorderapp.Controller;
import cuoiki.pizzaorderapp.Model.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.ResourceBundle;

public class CardProductController implements Initializable {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane cardForm;

    @FXML
    private Button prod_addBtn;

    @FXML
    private ImageView prod_imageView;

    @FXML
    private Label prod_name;

    @FXML
    private Label prod_price;
    private ProductData productData;
    @FXML
    private Spinner<Integer> prod_spinner;
    private SpinnerValueFactory<Integer>spin;
    private Connection connect;
    private PreparedStatement preparedStatement;
    private Statement statement;
    private ResultSet resultSet;
    private Image image;
    private String productId;
    public  void  setData(ProductData productData){
        this.productData=productData;
        productId=productData.getProduct_id();
        prod_name.setText(productData.getProduct_name());
        prod_price.setText(String.valueOf(productData.getProduct_price()));
        if (productData.getProduct_image() != null && !productData.getProduct_image().isEmpty()) {
            File imageFile = new File(productData.getProduct_image());
            if (imageFile.exists()) {
                image = new Image(imageFile.toURI().toString(), 225, 175, false, true);
                prod_imageView.setImage(image);
            } else {
                // Xử lý nếu không tìm thấy file ảnh
                prod_imageView.setImage(null); // hoặc hiển thị một ảnh mặc định khác
            }
        }
        pr=productData.getProduct_price();
    }

    private  int quantity;
    public void setQuanlity(){
        spin= new SpinnerValueFactory.IntegerSpinnerValueFactory(0,100,0);
            prod_spinner.setValueFactory(spin);
    }
    private double Price;
    private double pr;
    private void handleAddButtonAction(ActionEvent event) {
        int quantity = prod_spinner.getValue(); // Lấy số lượng từ Spinner
        String productName = prod_name.getText(); // Lấy tên sản phẩm từ Label
        double productPrice = pr; // Lấy giá sản phẩm đã lưu từ setData()

        // Tạo một đối tượng ProductData để chuyển dữ liệu cho DashboardCustomerController
        ProductData selectedProduct = new ProductData(productId, productName, productPrice, quantity);

        // Gọi phương thức để thêm sản phẩm vào TableView của DashboardCustomerController
        DashboardCustomerController.addToOrder(selectedProduct);
    }





    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setQuanlity();
        prod_addBtn.setOnAction(this::handleAddButtonAction); // Thiết lập sự kiện cho nút "Thêm"
    }
}
