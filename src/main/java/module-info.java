module com.fx {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.fx to javafx.fxml;
    exports com.fx;
}