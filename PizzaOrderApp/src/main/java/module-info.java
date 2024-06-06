module cuoiki.pizzaorderapp {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens cuoiki.pizzaorderapp to javafx.fxml;
    exports cuoiki.pizzaorderapp;
    exports cuoiki.pizzaorderapp.Controller;
    opens cuoiki.pizzaorderapp.Controller to javafx.fxml;
}