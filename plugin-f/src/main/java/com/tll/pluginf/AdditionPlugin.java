package com.tll.pluginf;

import com.tll.backend.datastore.loader.JsonAdapter;
import com.tll.backend.model.bill.Bill;
import com.tll.backend.model.bill.TemporaryBill;
import com.tll.gui.controllers.AppController;
import com.tll.plugin.AutoWired;
import com.tll.plugin.Plugin;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public class AdditionPlugin extends Plugin {

    private static final String FILE_PATH = "src/main/resources/addition-plugin-state.json";
    private static final JsonAdapter jsonAdapter = new JsonAdapter(FILE_PATH);

    @AutoWired(identifier = "AppController")
    private AppController appController;

    private AdditionState additionState;

    @Override
    public void load() {
        var fileExists = loadFileIfExists();

        if (!fileExists) {
            additionState = new AdditionState(new BigDecimal(1), false);
        }

        TextField idTextField = new TextField();
        idTextField.setPromptText("Masukkan id bill");

        AtomicReference<FieldStatus> idStatus = new AtomicReference<>(new FieldStatus(false));
        idTextField.textProperty().addListener(el -> {
            try {
                if (!additionState.isEnabled()) {
                    return;
                }

                var id = Integer.parseInt(idTextField.getText());
                Optional<TemporaryBill> optTempBill = appController.getTemporaryBillRepository().findById(id);
                if (optTempBill.isEmpty()) {
                    idTextField.setStyle("-fx-border-color: red");
                    idStatus.get().setValid(false);
                    return;
                }
                idStatus.get().setValid(true);
                idTextField.setStyle("-fx-border-color: none");
            } catch (NumberFormatException ex) {
                idTextField.setStyle("-fx-border-color: red");
                idStatus.get().setValid(false);
            }
        });

        var discTextField = new TextField();
        discTextField.setPromptText("discount in percent. ex: 60");

        discTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                if (!additionState.isEnabled()) {
                    return;
                }

                if (!idStatus.get().isValid()) {
                    discTextField.setStyle("-fx-border-color: red");
                    return;
                }
                var disc = 100 - Integer.parseInt(discTextField.getText());
                var id = Integer.parseInt(idTextField.getText());
                if (disc >= 0 && disc <= 100) {
                    Optional<TemporaryBill> optTempBill = appController.getTemporaryBillRepository().findById(id);
                    if (optTempBill.isEmpty()) {
                        discTextField.setStyle("-fx-border-color: red");
                        return;
                    }
                    var tempBill = optTempBill.get();
                    tempBill.setPriceMultiplier(BigDecimal.valueOf((float) disc / 100));
                    discTextField.setStyle("-fx-border-color: none");
                } else {
                    discTextField.setStyle("-fx-border-color: red");
                }
            } catch (NumberFormatException ex) {
                discTextField.setStyle("-fx-border-color: red");
            }
            additionState.setPriceAddition(Bill.getPriceAddition());
        });

        HBox hBox = new HBox();
        hBox.getChildren().addAll(idTextField, discTextField);

        TextField tsField = new TextField();
        tsField.setPromptText("masukkan tax & service charge");
        if (additionState.getPriceAddition() != null) {
            tsField.setText(String.valueOf(additionState.getPriceAddition()));
        }
        tsField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                if (!additionState.isEnabled()) {
                    return;
                }
                var addition = Integer.parseInt(tsField.getText());
                if (addition >= 0) {
                    Bill.setPriceAddition(new BigDecimal(addition));
                    tsField.setStyle("-fx-border-color: none");
                } else {
                    tsField.setStyle("-fx-border-color: red");
                }
            } catch (NumberFormatException ex) {
                tsField.setStyle("-fx-border-color: red");
            }
            additionState.setPriceAddition(Bill.getPriceAddition());
        });

        var saveButton = new Button();
        saveButton.setText("save");
        saveButton.setOnAction(el -> save());

        var button = new Button();
        button.setText(additionState.isEnabled() ? "disable addition" : "enable addition");
        button.setOnAction(el -> {
            if (additionState.isEnabled()) {
                button.setText("enable addition");
                additionState.setEnabled(false);
                Bill.setPriceAddition(new BigDecimal(0));
                return;
            }
            button.setText("disable addition");
            additionState.setEnabled(true);
            Bill.setPriceAddition(additionState.getPriceAddition());
        });

        VBox vbox = new VBox();
        vbox.getChildren().addAll(hBox, tsField, button);

        appController.getPluginNodes().add(vbox);
    }

    private void save() {
        try {
            jsonAdapter.save(List.of(additionState));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean loadFileIfExists() {
        try {
            var res = jsonAdapter.load(AdditionState.class);
            if (res.size() < 1) {
                return false;
            }
            this.additionState = res.get(0);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

}