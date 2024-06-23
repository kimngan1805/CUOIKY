package cuoiki.pizzaorderapp.Model;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.util.Optional;
public class AlertMessage {
    public static String path;
    private Alert alert;
    public void SuccessMessage(String message){
        alert= new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information message");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }
    public void ErrorMessage(String message){
        alert= new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error message");
        alert.setHeaderText(null);
        // thiết lập nội dung hiển thị của đối tượng
        alert.setContentText(message);
        alert.show();
    }
    public boolean ConfirmMessage(String message) {
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation message");
        alert.setHeaderText(null);
        alert.setContentText(message);
        // hiển thị hôph thoại thông tin
        Optional<ButtonType> option = alert.showAndWait();
        // chờ người dùng nhấn ok
        return option.get().equals(ButtonType.OK);
    }
}
