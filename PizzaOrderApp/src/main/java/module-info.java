module cuoiki.pizzaorderapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires jbcrypt;

    opens cuoiki.pizzaorderapp to javafx.fxml;
    exports cuoiki.pizzaorderapp;
    exports cuoiki.pizzaorderapp.Controller;
    opens cuoiki.pizzaorderapp.Controller to javafx.fxml;

}