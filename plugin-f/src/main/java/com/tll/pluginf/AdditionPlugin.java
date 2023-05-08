package com.tll.pluginf;

import com.tll.backend.datastore.loader.JsonAdapter;
import com.tll.backend.model.bill.Bill;
import com.tll.backend.model.bill.TemporaryBill;
import com.tll.gui.controllers.AppController;
import com.tll.gui.factory.NodeFactory;
import com.tll.plugin.AutoWired;
import com.tll.plugin.Plugin;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public class AdditionPlugin extends Plugin {

    private static final String FILE_PATH = "addition-plugin-state.json";
    private static final JsonAdapter jsonAdapter = new JsonAdapter(FILE_PATH);

    @AutoWired(identifier = "AppController")
    private AppController appController;

    private AdditionState additionState;

    @Override
    public void load() {
        var fileExists = loadFileIfExists();

        if (!fileExists) {
            additionState = new AdditionState(new BigDecimal(1), true);
        }
        Bill.setPriceAddition(additionState.getPriceAddition());
        TextField idTextField = new TextField();
        idTextField.setPromptText("Masukkan id bill");

        AtomicReference<FieldStatus> idStatus = new AtomicReference<>(new FieldStatus(false));
        idTextField.textProperty().addListener(el -> {
            try {
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
        NodeFactory nodeFactory = () -> {
            return new Label("Tax & Service Charge: " + additionState.getPriceAddition());
        };

        TextField tsField = new TextField();
        tsField.setPromptText("masukkan tax & service charge");
        if (additionState.getPriceAddition() != null) {
            tsField.setText(String.valueOf(additionState.getPriceAddition()));
        }
        tsField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                var addition = Integer.parseInt(tsField.getText());
                if (addition >= 0) {
                    Bill.setPriceAddition(new BigDecimal(addition));
                    for (int i = 0; i < appController.getKasirAdditions().size(); i++) {
                        Node node = appController.getKasirAdditions().get(i).getNode();
                        if (node instanceof Label && ((Label) node).getText().startsWith("Tax & Service Charge: ")) {
                            appController.getKasirAdditions().remove(i);
                            NodeFactory newNodeFactory = () -> {
                                return new Label("Tax & Service Charge: " + additionState.getPriceAddition());
                            };
                            appController.getKasirAdditions().add(newNodeFactory);
                            break;
                        }
                    }
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

        var disableButton = new Button();
        disableButton.setText(additionState.isEnabled() ? "disable addition" : "enable addition");
        disableButton.setOnAction(el -> {
            if (additionState.isEnabled()) {
                disableButton.setText("enable addition");
                for (int i = 0; i < appController.getKasirAdditions().size(); i++) {
                    Node node = appController.getKasirAdditions().get(i).getNode();
                    if (node instanceof Label && ((Label) node).getText().startsWith("Tax & Service Charge: ")) {
                        appController.getKasirAdditions().remove(i);
                        break;
                    }
                }
                additionState.setEnabled(false);
                return;
            }
            disableButton.setText("disable addition");
            NodeFactory newNodeFactory = () -> {
                return new Label("Tax & Service Charge: " + additionState.getPriceAddition());
            };
            appController.getKasirAdditions().add(newNodeFactory);
            additionState.setEnabled(true);
        });

        VBox vbox = new VBox();
        vbox.getChildren().addAll(hBox, tsField, saveButton, disableButton);

        appController.getKasirAdditions().add(nodeFactory);
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
            if (res.size() != 1) {
                return false;
            }
            this.additionState = res.get(0);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}