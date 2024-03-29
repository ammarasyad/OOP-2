module com.tll.gui {
    requires javafx.controls;
    requires java.sql;
    requires org.slf4j;
//    requires javafx.fxml;
    requires lombok;
    requires backend;
    requires javatuples;
    requires org.jetbrains.annotations;

    opens com.tll.gui to javafx.fxml;
    exports com.tll.gui;
    exports com.tll.gui.controllers;
}