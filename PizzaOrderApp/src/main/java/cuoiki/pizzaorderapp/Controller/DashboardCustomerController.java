package cuoiki.pizzaorderapp.Controller;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import cuoiki.pizzaorderapp.Model.AlertMessage;
import cuoiki.pizzaorderapp.Model.OrderDAO;
import cuoiki.pizzaorderapp.Model.OrderData;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import cuoiki.pizzaorderapp.Model.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
public class DashboardCustomerController implements Initializable {

    @FXML
    private GridPane menu_gridpane;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane dashboardCustomerForm;

    @FXML
    private Button menu_clearBtn;

    @FXML
    private TableColumn<?, ?> menu_col_productName;

    @FXML
    private Button menu_deleteBtn;

    @FXML
    private GridPane menu_gridPane;

    @FXML
    private Button menu_payBtn;

    @FXML
    private TableColumn<?, ?> menu_product_price;

    @FXML
    private TableColumn<?, ?> menu_product_quantity;

    @FXML
    private TableView<ProductData> menu_tableView;
    @FXML
    private GridPane menu_scrollPane;
    @FXML
    private Label menu_total;

    @FXML
    private Label menu_total_quantity;
    @FXML
    private Label welcome_customer;

    @FXML
    private ChoiceBox<?> menu_typeOrder;
    private Connection connect;
    private PreparedStatement preparedStatement;
    private Statement statement;
    private ResultSet resultSet;
    private AlertMessage alert= new AlertMessage();

    private void displayUsername() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connect = connectNow.getConnection();
        String query = "SELECT username_cus FROM Customer WHERE id_cus = ?"; // Thay thế bằng câu truy vấn thực tế của bạn
        try {
            // Thực hiện câu truy vấn
            preparedStatement = connect.prepareStatement(query);
            preparedStatement.setInt(1, 1); // Ví dụ: truy vấn username cho user có id = 1
            resultSet = preparedStatement.executeQuery();

            // Xử lý kết quả truy vấn
            if (resultSet.next()) {
                String username = resultSet.getString("username_cus");
                welcome_customer.setText(username);
            } else {
                // Xử lý khi không tìm thấy người dùng
                welcome_customer.setText(null);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Đóng các tài nguyên
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    private  ObservableList<ProductData> cardListData= FXCollections.observableArrayList();
    private static ObservableList<ProductData> orderListData= FXCollections.observableArrayList();

    public static void addToOrder(ProductData selectedProduct) {
        orderListData.add(selectedProduct);
    }
    public void setWelcomeMessage(String username) {
        welcome_customer.setText( username);
    }
    public ObservableList<ProductData>menuGetData(){
        ObservableList<ProductData>listData=FXCollections.observableArrayList();
        String sql="select * from Product";
        DatabaseConnection connectNow= new DatabaseConnection();
        Connection connect=connectNow.getConnection();
        try{
            preparedStatement=connect.prepareStatement(sql);
            resultSet=preparedStatement.executeQuery();
            ProductData productData;
            while (resultSet.next()){
                productData=new ProductData(resultSet.getString("id_prod"),resultSet.getString("name_prod"),resultSet.getDouble("price_prod"),resultSet.getString("image_prod"));
                listData.add(productData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  listData;
    }
    public void menuDisplayCard(){
        cardListData.clear();
        cardListData.addAll(menuGetData());
        menu_gridpane.getRowConstraints().clear();
        menu_gridpane.getColumnConstraints().clear();
        int row=0;
        int column=0;
        for(int q=0; q< cardListData.size(); q++){
            try{
                FXMLLoader loader= new FXMLLoader();
                loader.setLocation(getClass().getResource("/cuoiki/pizzaorderapp/View/CardProduct.fxml"));
                AnchorPane pane= loader.load();
                CardProductController card= loader.getController();
                card.setData(cardListData.get(q));
                if(column==2){
                    column=0;
                    row+=1;
                }
                menu_gridpane.add(pane,column++, row);
                GridPane.setMargin(pane,new Insets(10));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void Type (){
        List<String> listY= new ArrayList<>();
        for(String data: CustomerData.type){
            listY.add(data);
        }
        ObservableList ListData= FXCollections.observableArrayList(listY);
        menu_typeOrder.setItems(ListData);
    }
    @FXML
    private boolean confirmDeleteAction(ProductData product) {
        //  thông báo xác nhận xóa sản phẩm và trả về true nếu người dùng đồng ý
         alert.ConfirmMessage("Bạn có chắc chắn muốn xóa sản phẩm "+product.getProduct_name()+"?");
         return true; // Tạm thời trả về true để mô phỏng việc xác nhận xóa
    }
    @FXML
    private void handleDeleteButtonAction(ActionEvent event) {
        ProductData selectedProduct = menu_tableView.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            // Xác nhận xóa sản phẩm và cập nhật lại danh sách đơn hàng và TableView
            boolean confirmed = confirmDeleteAction(selectedProduct);
            if (confirmed) {
                orderListData.remove(selectedProduct); // Xóa sản phẩm khỏi danh sách đơn hàng
                // Cập nhật lại TableView (menu_tableView)
                menu_tableView.refresh();
            }
        }
    }
    @FXML
    private void handleRefreshButtonAction(ActionEvent event) {
        // Làm mới danh sách đơn hàng và cập nhật lại TableView
        orderListData.clear();
        // Gọi phương thức để lấy lại danh sách đơn hàng từ cơ sở dữ liệu
        menu_tableView.refresh(); // Làm mới TableView để hiển thị lại dữ liệu mới
    }
    // Phương thức để cập nhật số lượng sản phẩm
    private void updateTotalQuantityLabel() {
        int totalQuantity = 0;
        for (ProductData product : orderListData) {
            totalQuantity += product.getQuantity();
        }
        menu_total_quantity.setText(String.valueOf(totalQuantity));
    }
    // Phương thức để cập nhật tổng tiền thanh toán
    private void updateTotalPriceLabel() {
        double totalPrice = 0.0;
        for (ProductData product : orderListData) {
            totalPrice += product.getQuantity() * product.getProduct_price();
        }
        menu_total.setText(String.format("%.2f", totalPrice)); // Định dạng hiển thị số tiền với hai chữ số sau dấu thập phân
    }
    private OrderDAO orderDAO = new OrderDAO();
    private Socket socket;
    private ObjectOutputStream outputStream;

    private boolean confirmPaymentAction() {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Xác nhận thanh toán");
        confirmationAlert.setHeaderText("Bạn có chắc chắn muốn thanh toán đơn hàng này?");
        confirmationAlert.setContentText("Hành động này không thể hoàn tác.");
        Optional<ButtonType> result = confirmationAlert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    @FXML
    private void handlePaymentButtonAction(ActionEvent event) {

        boolean confirmed = confirmPaymentAction();
        if (confirmed) {
            // Lấy tên người dùng từ welcome_customer.getText()
            String username = welcome_customer.getText().replace("Welcome, ", "");

            // Lấy loại đơn hàng từ menu_typeOrder.getValue() (cần ép kiểu cho phù hợp)
            String orderType = (String) menu_typeOrder.getValue(); // Cần ép kiểu thích hợp

            // Tổng hoá đơn
            double totalPrice = Double.parseDouble(menu_total.getText());
            String product_name=menu_col_productName.getCellFactory().toString();
            // Thêm đơn hàng vào cơ sở dữ liệu
            OrderDAO orderDAO = new OrderDAO();
            orderDAO.addOrder(username, orderListData, orderType, totalPrice,product_name);

            // Xóa danh sách đơn hàng hiện tại sau khi thanh toán
            orderListData.clear();
            menu_tableView.refresh();

            // Hiển thị thông báo cho người dùng
            alert.SuccessMessage("Đơn hàng đã được thanh toán thành công!");

            // Cập nhật lại tổng số lượng và tổng giá trị về 0
            updateTotalQuantityLabel();
            updateTotalPriceLabel();
            java.util.Date date= new java.util.Date();
            java.sql.Date sqlDate= new java.sql.Date(date.getTime());
            // Tạo đối tượng OrderData và gửi qua socket
            OrderData orderData = new OrderData("0", Integer.parseInt(menu_total_quantity.getText()), totalPrice, username, orderType,sqlDate);
            sendOrderToServer(orderData);
        }
    }
    private static final String SERVER_ADDRESS = "localhost";
    private static final int PORT = 8888;
    private void sendOrderToServer(OrderData orderData) {
        new Thread(() -> {
            try {
                Socket socket = new Socket(SERVER_ADDRESS, PORT);
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                oos.writeObject(orderData);
                oos.flush();
                oos.close();
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        menuDisplayCard();
        Type();
        // hiển thị sản phẩm từ card product lên table view
        menu_col_productName.setCellValueFactory(new PropertyValueFactory<>("product_name"));
        menu_product_price.setCellValueFactory(new PropertyValueFactory<>("product_price"));
        menu_product_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        menu_tableView.setItems(orderListData);
        //  phương thức để khi khách hàng thêm sản phẩm có thể tự động cập nhật lệnh quantity và tổng hoá đơn
        orderListData.addListener((ListChangeListener.Change<? extends ProductData> change) -> {
            while (change.next()) {
                if (change.wasAdded() || change.wasRemoved() || change.wasUpdated()) {
                    updateTotalQuantityLabel();
                    updateTotalPriceLabel();
                }
            }
        });
    }


}
