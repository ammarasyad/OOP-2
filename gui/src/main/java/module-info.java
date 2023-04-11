module com.tll.gui {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.tll.gui to javafx.fxml;
    exports com.tll.gui;
}