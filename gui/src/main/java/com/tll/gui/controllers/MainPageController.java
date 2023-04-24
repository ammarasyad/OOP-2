package com.tll.gui.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.util.Duration;
import lombok.Getter;

import java.text.SimpleDateFormat;
import java.util.Date;
@Getter
public class MainPageController {
    public Label clockLabel;
    public Label bottomLabel;
    public MainPageController(){
        clockLabel = new Label("Clock Label");
        bottomLabel = new Label("Bottom Label");
    }
    public void startClock(){
        clockLabel.setText("00:00:00");

        // Create a Timeline to update the label every second
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            // Update the label with the current time
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            clockLabel.setText(dateFormat.format(new Date()));
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
}
