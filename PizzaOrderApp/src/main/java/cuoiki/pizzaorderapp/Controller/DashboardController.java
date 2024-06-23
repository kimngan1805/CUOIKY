package cuoiki.pizzaorderapp.Controller;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

import cuoiki.pizzaorderapp.Model.AlertMessage;
import cuoiki.pizzaorderapp.Model.DatabaseConnection;
import cuoiki.pizzaorderapp.Model.OrderData;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class DashboardController implements Initializable {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane main_form;

    @FXML
    private Label date;

    @FXML
    private Button exitBtn;

    @FXML
    private Label idC;

    @FXML
    private Label idO;

    @FXML
    private TextField info_productId;

    @FXML
    private TextField info_productName;

    @FXML
    private TextField info_productPrice;

    @FXML
    private ComboBox<?> info_productType;

    @FXML
    private Label nameC;

    @FXML
    private Button orderControll;

    @FXML
    private AnchorPane orderForm;

    @FXML
    private TableColumn<?, ?> order_col_date;

    @FXML
    private TableColumn<?, ?> order_col_idC;

    @FXML
    private TableColumn<?, ?> order_col_idO;

    @FXML
    private TableColumn<?, ?> order_col_nameC;

    @FXML
    private TableColumn<?, ?> order_col_totalP;

    @FXML
    private TableColumn<?, ?> order_col_totalQ;

    @FXML
    private TableColumn<?, ?> order_col_typeO;

    @FXML
    private TableView<?> order_tableView;

    @FXML
    private TableColumn<?, ?> prodOrder_col_id;

    @FXML
    private TableColumn<?, ?> prodOrder_col_name;

    @FXML
    private TableColumn<?, ?> prodOrder_col_price;

    @FXML
    private TableColumn<?, ?> prodOrder_col_quantity;

    @FXML
    private TableView<OrderData> prodOrder_tableView;

    @FXML
    private Button productControll;

    @FXML
    private AnchorPane productForm;

    @FXML
    private Button product_addBtn;

    @FXML
    private Button product_addImageBtn;

    @FXML
    private Button product_clearBtn;

    @FXML
    private TableColumn<ProductData, String> product_col_id;

    @FXML
    private TableColumn<ProductData, String> product_col_name;

    @FXML
    private TableColumn<ProductData, String> product_col_price;

    @FXML
    private Button product_deleteBtn;

    @FXML
    private ImageView product_imageView;

    @FXML
    private TableView<ProductData> product_tableView;

    @FXML
    private Button product_updateBtn;

    @FXML
    private Button purchaseOrderControll;

    @FXML
    private AnchorPane purchaseOrderForm;

    @FXML
    private Button statistic;

    @FXML
    private AnchorPane statisticForm;

    @FXML
    private Label totalP;

    @FXML
    private Label totalQ;

    @FXML
    private Label typeO;
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private Statement statement;
    private Image image;
    private AlertMessage alert= new AlertMessage();
    // switch page
    public void SwitchForm(ActionEvent event){
        if(event.getSource()==statistic){
            statisticForm.setVisible(true);
            productForm.setVisible(false);
            orderForm.setVisible(false);
        }else if(event.getSource()==productControll){
            statisticForm.setVisible(false);
            productForm.setVisible(true);
            orderForm.setVisible(false);
        }else if(event.getSource()==orderControll){
            statisticForm.setVisible(false);
            productForm.setVisible(false);
            orderForm.setVisible(true);
        }
    }
    public void exitButton( ActionEvent event){
        Stage stage= (Stage)exitBtn.getScene().getWindow();
        stage.close();
    }

    //----------Trang quản lý sản phẩm---------
    public void productAdd() {

        String sql = "insert into Product (id_prod, image_prod, name_prod, price_prod)"+"values(?,?,?,?)";
        connect = DatabaseConnection.getConnection();
        try{

            if(info_productId.getText().isEmpty()
                    || info_productName.getText().isEmpty()
                    || info_productPrice.getText().isEmpty()
                    || getData.path == null || getData.path == ""){
                alert.ErrorMessage("Hãy điền vào những chỗ còn trống");
            }else{

                String checkData = "SELECT id_prod FROM Product WHERE id_prod = '"
                        +info_productId.getText()+"'";

                statement = connect.createStatement();
                result = statement.executeQuery(checkData);

                if(result.next()){
                    alert.ErrorMessage("ID sản phẩm đã tồn tại!");
                }else{

                    prepare = connect.prepareStatement(sql);
                    prepare.setString(1, info_productId.getText());

                    String uri = getData.path;
                    uri = uri.replace("\\", "\\\\");
                    prepare.setString(2, uri);

                    prepare.setString(3, info_productName.getText());
                    prepare.setString(4, info_productPrice.getText());

                    prepare.executeUpdate();

                    alert.SuccessMessage("Thêm sản phẩm thành công!");

                    // TO BE UPDATED THE TABLEVIEW
                    productShowListData();
                    // CLEAR FIELDS
                    productClear();
                }
            }
        }catch(Exception e){e.printStackTrace();}

    }

    public void productUpdate(){

        String uri = getData.path;
        uri = uri.replace("\\", "\\\\");

        String sql = "UPDATE Product SET name_prod = '"
                +info_productName.getText()+"', price_prod = '"
                +info_productPrice.getText()+"', image_prod = '"
                +uri+"' WHERE id_prod = '"+info_productId.getText()+"'";

        connect = DatabaseConnection.getConnection();

        try{
            if(info_productId.getText().isEmpty()
                    || info_productName.getText().isEmpty()
                    || info_productPrice.getText().isEmpty()
                    || getData.path == null || getData.path == ""){
                alert.ErrorMessage("Hãy điền vào những chỗ còn trống!");
            }else{
                    statement = connect.createStatement();
                    statement.executeUpdate(sql);
                    alert.SuccessMessage("Cập nhật thành công!");

                    productShowListData();
                    productClear();
            }
        }catch(Exception e){e.printStackTrace();}

    }

    public void productDelete() {
        String sql = "DELETE FROM Product WHERE id_prod = '"
                +info_productId.getText()+"'";

        connect = DatabaseConnection.getConnection();

        try{
            statement = connect.createStatement();
            statement.executeUpdate(sql);

            alert.SuccessMessage("Xóa thành công!");

            productShowListData();
            productClear();
        }catch(Exception e){e.printStackTrace();}
    }

    public void productClear() {
        info_productId.setText("");
        info_productPrice.setText("");
        info_productName.setText("");

        getData.path = "";

        product_imageView.setImage(null);
    }

    public void productInsertImage() {
        FileChooser open = new FileChooser();
        open.setTitle("Open Image File");
        open.getExtensionFilters().add(new FileChooser.ExtensionFilter("File Image", "*jpg", "*png"));

        File file = open.showOpenDialog(main_form.getScene().getWindow());

        if(file != null){
            getData.path = file.getAbsolutePath();

            image = new Image(file.toURI().toString(), 140, 120, false, true);
            product_imageView.setImage(image);
        }
    }

    public ObservableList<ProductData> productListData() {
        ObservableList<ProductData> listData = FXCollections.observableArrayList();
        String sql = "select * from Product";
        connect = DatabaseConnection.getConnection();
        try {
            prepare = connect.prepareStatement(sql);
            result=prepare.executeQuery();

            ProductData prodD;

            while (result.next()){
                prodD= new ProductData(result.getString("id_prod"),result.getString("name_prod"),
                        result.getDouble("price_prod"),result.getString("image_prod"));
                listData.add(prodD);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }
    private ObservableList<ProductData> productList;
    public void productShowListData(){
        productList = productListData();

        product_col_id.setCellValueFactory(new PropertyValueFactory<>("product_id"));
        product_col_name.setCellValueFactory(new PropertyValueFactory<>("product_name"));
        product_col_price.setCellValueFactory(new PropertyValueFactory<>("product_price"));

        product_tableView.setItems(productList);
    }

    public void productSelect(){
        ProductData prodD = product_tableView.getSelectionModel().getSelectedItem();
        int num = product_tableView.getSelectionModel().getSelectedIndex();

        if((num - 1) < -1){ return; }

        info_productId.setText(String.valueOf(prodD.getProduct_id()));
        info_productName.setText(prodD.getProduct_name());
        info_productPrice.setText(String.valueOf(prodD.getProduct_price()));

        getData.path = prodD.getProduct_image();

        String uri = "file:" + prodD.getProduct_image();

        image = new Image(uri, 140, 120, false, true);

        product_imageView.setImage(image);
    }

    private ObservableList<OrderData> orderListData = FXCollections.observableArrayList();

    private ServerSocket serverSocket;
    private static final int PORT = 8888;
    private void startServer() {
        new Thread(() -> {
            try {
                serverSocket = new ServerSocket(PORT);
                while (true) {
                    Socket socket = serverSocket.accept();
                    ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                    OrderData orderData = (OrderData) ois.readObject();
                    Platform.runLater(() -> updateOrderTable(orderData));
                    ois.close();
                    socket.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
    private void updateOrderTable(OrderData orderData) {
        // Cập nhật bảng order_tableView với dữ liệu mới
        orderListData.add(orderData);
        order_tableView.refresh();
    }
    // Đảm bảo bạn đóng serverSocket khi ứng dụng đóng
    public void stopServer() {
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void startOrderDataServer() {
        new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(12345)) {
                while (true) {
                    try (Socket clientSocket = serverSocket.accept();
                         ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream())) {
                        OrderData orderData = (OrderData) in.readObject();
                        Platform.runLater(() -> orderListData.add(orderData));
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public ObservableList<OrderData> OrderDataList() {
        ObservableList<OrderData> listData = FXCollections.observableArrayList();
        String selectData = "select * from Orders";
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connect = connectNow.getConnection();
        try {
            prepare = connect.prepareStatement(selectData);
            result = prepare.executeQuery();
            OrderData oData;
            while (result.next()) {
                oData = new OrderData(result.getString("id"), result.getString("product_name"),
                        result.getInt("quantity"), result.getDouble("total_price"));
                listData.add(oData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }

    private ObservableList<OrderData> OrderListData;

    public void showOrderData() {
        OrderListData = OrderDataList();
        prodOrder_col_id.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        prodOrder_col_name.setCellValueFactory(new PropertyValueFactory<>("product_name"));
        prodOrder_col_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        prodOrder_col_price.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        prodOrder_tableView.setItems(OrderListData);
    }

    private int OrderID;

    public void OrderSelectedData() {
        OrderData oData = (OrderData) order_tableView.getSelectionModel().getSelectedItem();
        int num = order_tableView.getSelectionModel().getSelectedIndex();
        if ((num - 1) < -1) return;
        idO.setText(oData.getOrderId());
        idC.setText(oData.getUsername());
        totalQ.setText(String.valueOf(oData.getQuantity()));
        typeO.setText(oData.getOrderType());
        date.setText(String.valueOf(oData.getDate()));

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        productShowListData();
    }
}
