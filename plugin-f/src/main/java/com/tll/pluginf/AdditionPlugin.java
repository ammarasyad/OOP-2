package com.tll.pluginf;

import com.tll.backend.datastore.loader.JsonAdapter;
import com.tll.backend.model.bill.Bill;
import com.tll.backend.model.bill.TemporaryBill;
import com.tll.gui.ClosableTab;
import com.tll.gui.controllers.AppController;
import com.tll.plugin.AutoWired;
import com.tll.plugin.Plugin;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public class AdditionPlugin extends Plugin {

    private static final String FILE_PATH = "src/main/resources/addition-plugin-state.json";
    private static final JsonAdapter jsonAdapter = new JsonAdapter(FILE_PATH);

    private static final String TAB_NAME = "tax, service, discount";

    @AutoWired(identifier = "AppController")
    private AppController appController;

    private AdditionState additionState;
    private MenuItem menuItem;

    @Override
    public void load() {
        menuItem = new MenuItem(TAB_NAME);
        menuItem.setOnAction(el -> openDiscountPage());
        var fileExists = loadFileIfExists();

        if (!fileExists) {
            additionState = new AdditionState(new BigDecimal(1), false);
        }

        var button = new Button();
        button.setText(additionState.isEnabled() ? "disable" : "enable");
        button.setOnAction(el -> {
            if (!additionState.isEnabled()) {
                button.setText("enable");
                additionState.setEnabled(false);
                Bill.setPriceAddition(new BigDecimal(0));
                removeMenu();
                return;
            }
            button.setText("disable");
            additionState.setEnabled(true);
            Bill.setPriceAddition(additionState.getPriceAddition());
            addMenu();
        });

        addMenu();
    }

    private void addMenu() {
        appController.getPages().getItems().add(menuItem);
    }

    private void removeMenu() {
        int i = 0;
        var items = appController.getPages().getItems();
        for (var item: items) {
            if (item.hashCode() == menuItem.hashCode()) {
                items.remove(i);
                return;
            }
            ++i;
        }
    }

    private void openDiscountPage() {
        VBox discountVBox = new VBox();

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

        TextField tsField = new TextField();
        tsField.setPromptText("masukkan tax & service charge");
        if (additionState.getPriceAddition() != null) {
            tsField.setText(String.valueOf(additionState.getPriceAddition()));
        }
        tsField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                var addition = Integer.parseInt(tsField.getText());
                if (addition >= 0) {
                    if (addition == 999) {
                        removeMenu();
                    }
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

        discountVBox.getChildren().addAll(idTextField, discTextField, tsField);

        ClosableTab closableTab = new ClosableTab(TAB_NAME);
        closableTab.setContent(discountVBox);
        appController.getTabPane().getTabs().add(closableTab);
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