module com.tll.gui {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;

    opens com.tll.gui to javafx.fxml;
    exports com.tll.gui;
}