package com.tll.gui.controllers;

import com.tll.backend.model.bill.TemporaryBill;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import lombok.Getter;

import java.io.File;
import java.util.List;

@Getter
public class SettingPageModel {
    private FlowPane pluginList;

    public SettingPageModel(){
        pluginList = new FlowPane();
    }
}
