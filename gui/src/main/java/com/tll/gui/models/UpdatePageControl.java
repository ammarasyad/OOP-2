package com.tll.gui.models;

import com.tll.gui.controllers.UpdatePageModel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class UpdatePageControl {
    UpdatePageModel updatePageModel;
    public UpdatePageControl(UpdatePageModel updatePageModel){
        this.updatePageModel = updatePageModel;
    }
}
