package com.tll.gui.factory;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.util.Duration;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SidebarFactory {

    private static final int MAIN_SIDEBAR_WIDTH = 200;
    private static final int MAIN_SIDEBAR_HEIGHT = 200;

    public static VBox getMainSidebar() {
        // Membuat label untuk menampilkan waktu
        Label clockLabel = new Label();
        clockLabel.setStyle("-fx-text-fill: #ffffff;");
        clockLabel.setText("00:00:00");
        clockLabel.setStyle("-fx-font-size: 24pt; -fx-font-weight: 100; -fx-text-fill: #ffffff; -fx-background-color: #0077B5 ");


        // Create a thread pool with a single thread
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(() -> updateClock(clockLabel), 0, 1, TimeUnit.SECONDS);

        // Membuat layout VBox untuk sidepanel
        VBox vbox = new VBox();
        vbox.getChildren().addAll(clockLabel);
        vbox.setStyle("-fx-background-color: #0077B5;");
        vbox.setPrefWidth(MAIN_SIDEBAR_WIDTH);
        vbox.setMaxWidth(MAIN_SIDEBAR_HEIGHT);
        vbox.setAlignment(Pos.TOP_CENTER);
        VBox.setMargin(clockLabel,new Insets(25,0,0,0));

        return vbox;
    }

    public static void startClock(Label clockLabel){
        clockLabel.setText("00:00:00");
        clockLabel.setStyle("-fx-font-size: 24pt; -fx-font-weight: 100; -fx-text-fill: #ffffff; -fx-background-color: #0077B5 ");
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            // Update the label with the current time
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            clockLabel.setText(dateFormat.format(new Date()));
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private static void updateClock(Label timeLabel) {
        // Get the current time
        Date now = new Date();
        // Format the time as a string
        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm:ss");
        String timeStr = formatter.format(now);

        // Update the label on the JavaFX Application Thread
        javafx.application.Platform.runLater(() -> timeLabel.setText(timeStr));
    }

}
