package cuoiki.pizzaorderapp.Model;
import cuoiki.pizzaorderapp.Controller.ProductData;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
public class OrderSensor {
    public void sendOrderData(List<ProductData> orderListData) {
        try (
                Socket socket = new Socket("localhost", 8888); // Thay localhost và số cổng tương ứng của order app
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())
        ) {
            out.writeObject(orderListData);
            System.out.println("Đã gửi đơn hàng đến ứng dụng đặt hàng.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}