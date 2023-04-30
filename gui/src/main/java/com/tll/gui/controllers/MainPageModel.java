package com.tll.gui.controllers;

import javafx.scene.control.Label;
import lombok.Getter;

@Getter
public class MainPageModel {
    public Label clockLabel;
    public Label bottomLabel;
    public MainPageModel(){
        clockLabel = new Label("Clock Label");
        bottomLabel = new Label("Bottom Label");
    }

}
