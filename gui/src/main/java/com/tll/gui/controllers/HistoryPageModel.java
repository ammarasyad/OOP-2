package com.tll.gui.controllers;

import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import lombok.Getter;
import lombok.Setter;

@Getter
public class HistoryPageModel {
    private VBox transactionContainer;
    private TextArea detailTextArea;
    @Setter
    private String savePath;

    public HistoryPageModel(){
        transactionContainer = new VBox();
        detailTextArea = new TextArea();

    }
}
