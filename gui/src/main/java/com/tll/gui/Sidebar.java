package com.tll.gui;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.text.Font;
import javafx.scene.control.Label;



import java.text.SimpleDateFormat;
import java.util.Date;

public class Sidebar extends Application{
    public Label clockLabel;
    public Label bottomLabel;

    @Override
    public void start(Stage primaryStage) {
        //clockLabel = new Label("Clock Label");
        //bottomLabel = new Label("Bottom Label");

        // Membuat tombol-tombol untuk sidepanel
        //Button button1 = new Button("Button 1");
        //Button button2 = new Button("Button 2");
        //Button button3 = new Button("Button 3");

        // Membuat label untuk menampilkan waktu
        clockLabel = new Label();
        clockLabel.setStyle("-fx-text-fill: #ffffff;");
        startClock();

        // Membuat layout VBox untuk sidepanel
        VBox vbox = new VBox();
        vbox.getChildren().addAll(clockLabel);
        //vbox.setAlignment(Pos.CENTER);
        //vbox.setSpacing(10);
        vbox.setStyle("-fx-background-color: #000000;");
        vbox.setPrefWidth(200);
        vbox.setMaxWidth(200);
        vbox.setMargin(clockLabel,new Insets(10,0,0,50));

        // Membuat layout VBox untuk menampung main content
        VBox mainContent = new VBox();
        mainContent.setStyle("-fx-background-color: #eeeeee;");
        mainContent.setPrefWidth(800);
        mainContent.setMaxWidth(800);

        // Membuat layout HBox untuk menampung sidepanel dan main content
        HBox root = new HBox();
        root.getChildren().addAll(vbox, mainContent);

        // Menampilkan scene
        Scene scene = new Scene(root, 1000, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Sidepanel Example");
        primaryStage.show();
    }
    public VBox getSidebar (){
        // Membuat label untuk menampilkan waktu
        clockLabel = new Label();
        clockLabel.setStyle("-fx-text-fill: #ffffff;");
        startClock();

        // Membuat layout VBox untuk sidepanel
        VBox vbox = new VBox();
        vbox.getChildren().addAll(clockLabel);
        //vbox.setAlignment(Pos.CENTER);
        //vbox.setSpacing(10);
        vbox.setStyle("-fx-background-color: #0077B5;");
        vbox.setPrefWidth(200);
        vbox.setMaxWidth(200);
        vbox.setAlignment(Pos.TOP_CENTER);
        vbox.setMargin(clockLabel,new Insets(25,0,0,0));



        return vbox;

    }
    public void startClock(){
        clockLabel.setText("00:00:00");
        clockLabel.setStyle("-fx-font-size: 24pt; -fx-font-weight: 100; -fx-text-fill: #ffffff; -fx-background-color: #0077B5 ");
        // clockLabel.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID,
                // CornerRadii.EMPTY, new BorderWidths(3))));

        //clockLabel.setfont(new Font(24));

        // Create a Timeline to update the label every second
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            // Update the label with the current time
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            clockLabel.setText(dateFormat.format(new Date()));
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
