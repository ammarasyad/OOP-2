package com.tll.gui.models;

import com.tll.gui.controllers.MainPageModel;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainPageControl {
    private MainPageModel mainPageModel;
    public MainPageControl(MainPageModel mainPageModel){
        this.mainPageModel = mainPageModel;
    }
    public void startClock(){
        mainPageModel.clockLabel.setText("00:00:00");

        // Create a Timeline to update the label every second
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            // Update the label with the current time
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            mainPageModel.clockLabel.setText(dateFormat.format(new Date()));
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
}
