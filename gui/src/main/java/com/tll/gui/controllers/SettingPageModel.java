package com.tll.gui.controllers;

import javafx.scene.layout.FlowPane;
import lombok.Getter;
import lombok.Setter;

@Getter
public class SettingPageModel {
    private FlowPane pluginList;
    @Setter
    private String folderPath;

    public SettingPageModel() {
        pluginList = new FlowPane();
    }
}
