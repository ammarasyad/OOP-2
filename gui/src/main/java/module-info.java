module com.tll.gui {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;
    requires backend;
    requires javatuples;

    opens com.tll.gui to javafx.fxml;
    exports com.tll.gui;
}