package com.tll.gui;

import com.tll.gui.controllers.AppController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.tll.gui.models.AppModel;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Hello World!");

        AppController ac = new AppController();
        VBox vbox = new AppModel(ac);

        Scene scene = new Scene(vbox, 820, 640);
        stage.setTitle("Hello!");


        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}