package com.tll.pluginf;

import com.tll.backend.model.bill.Bill;
import com.tll.gui.controllers.AppController;
import com.tll.plugin.AutoWired;
import com.tll.plugin.Plugin;
import javafx.scene.control.TextField;

import java.math.BigDecimal;

public class HelloApplication extends Plugin {

    @AutoWired(identifier = "AppController")
    private AppController appController;

    @Override
    public void load() {
        var additions = appController.getKasirAdditions();
        var discTextField = new TextField();
        discTextField.setPromptText("discount in percent. ex: 60");
        discTextField.textProperty().addListener(el -> {
            HelloController helloController = new HelloController();
            helloController.test();
            try {
                var disc = 100 - Integer.parseInt(discTextField.getText());
                if (disc >= 0 && disc <= 100) {
                    Bill.priceMultiplier = BigDecimal.valueOf((float) disc / 100);
                    discTextField.setStyle("-fx-border-color: none");
                } else {
                    discTextField.setStyle("-fx-border-color: red");
                }
            } catch (NumberFormatException ex) {
                discTextField.setStyle("-fx-border-color: red");
            }
        });
        additions.add(discTextField);
    }


}