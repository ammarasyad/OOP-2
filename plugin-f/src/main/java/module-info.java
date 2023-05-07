module com.tll.pluginf {
    requires javafx.controls;
    requires backend;
    requires com.tll.gui;

    opens com.tll.pluginf to javafx.fxml;
    exports com.tll.pluginf;
}