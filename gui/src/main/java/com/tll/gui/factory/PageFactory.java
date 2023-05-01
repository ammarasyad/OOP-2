package com.tll.gui.factory;

import com.tll.gui.AutoCompleteComboBox;
import com.tll.gui.ProductWidget;
import com.tll.gui.TransactionWidget;
import com.tll.gui.controllers.MainPageModel;
import com.tll.gui.controllers.RegisterPageModel;
import com.tll.gui.controllers.UpdatePageModel;
import com.tll.gui.models.MainPageControl;
import com.tll.gui.models.RegisterPageControl;
import com.tll.gui.models.UpdatePageControl;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.util.BitSet;

public class PageFactory {
    public static VBox getMainPage(MainPageModel mainPageModel){
        VBox mainPage = new VBox();
        MainPageControl mc = new MainPageControl(mainPageModel);
        mc.startClock();
        mainPage.setPrefSize(600, 400);
        mainPage.setAlignment(Pos.TOP_CENTER);

        Label clockLabel = new Label();
        clockLabel.setFont(new Font(49.0));

        Label bottomLabel = new Label();

        clockLabel.textProperty().bindBidirectional(mainPageModel.getClockLabel().textProperty());
        bottomLabel.textProperty().bindBidirectional(mainPageModel.getBottomLabel().textProperty());
//        mainPageController.startClock();

        mainPage.getChildren().addAll(clockLabel, bottomLabel);

        return mainPage;
    }

    public static VBox getRegisterPage(RegisterPageModel registerPageModel){
        VBox registerPage = new VBox();
        RegisterPageControl registerPageControl = new RegisterPageControl(registerPageModel);

        registerPage.setPrefSize(600, 400);
        registerPage.setAlignment(Pos.TOP_LEFT);

        Label label = new Label("Register");
        label.setFont(new Font(38.0));
        VBox.setMargin(label, new Insets(0, 0, 0, 10)); // set left margin of label

        HBox hbox = new HBox();
        hbox.setStyle("-fx-background-color: #ddd;");

        VBox leftVbox = new VBox();
        leftVbox.setPrefSize(100, 200);
        leftVbox.setSpacing(10);
        leftVbox.setPadding(new Insets(10));

        Label nameLabel = new Label("Nama :");
        TextField nameTextField = new TextField();
        nameTextField.setPromptText("e.g. Hayam Wuruk");

        Label phoneLabel = new Label("Nomor Telepon :");
        TextField phoneTextField = new TextField();
        phoneTextField.setPromptText("e.g. 081806122004");

        leftVbox.getChildren().addAll(nameLabel, nameTextField, phoneLabel, phoneTextField);
        HBox.setMargin(leftVbox, new Insets(10, 10, 10, 20)); // set margin of left VBox in HBox

        VBox rightVbox = new VBox();
        rightVbox.setAlignment(Pos.BOTTOM_RIGHT);
        rightVbox.setPrefSize(100, 200);

        Button registerButton = new Button("Register");
        rightVbox.getChildren().add(registerButton);

        VBox.setMargin(registerButton, new Insets(0, 30, 30, 0)); // set margin of button in right VBox
        rightVbox.setPadding(new Insets(0, 30, 30, 0)); // set padding of right VBox

        hbox.getChildren().addAll(leftVbox, rightVbox);
        registerPage.getChildren().addAll(label, hbox);

        HBox.setHgrow(leftVbox, Priority.ALWAYS); // set HGrow to fill half of HBox
        HBox.setHgrow(rightVbox, Priority.ALWAYS);
        VBox.setVgrow(hbox, Priority.ALWAYS);

        return registerPage;
    }

    public static VBox getUpdatePage(UpdatePageModel updatePageModel){
        VBox updatePage = new VBox();
        UpdatePageControl updatePageControl = new UpdatePageControl(updatePageModel);

        updatePage.setPrefSize(600, 400);
        updatePage.setAlignment(Pos.TOP_LEFT);

        Label label = new Label("Update Account");
        label.setFont(new Font(38.0));
        VBox.setMargin(label, new Insets(0, 0, 0, 10)); // set left margin of label

        HBox hbox = new HBox();
        hbox.setStyle("-fx-background-color: #ddd;");

        VBox leftVbox = new VBox();
        leftVbox.setPrefSize(100, 200);
        leftVbox.setSpacing(10);
        leftVbox.setPadding(new Insets(10));

        Label nameLabel = new Label("Nama :");
        TextField nameTextField = new TextField();
        Label phoneLabel = new Label("Nomor Telepon :");
        TextField phoneTextField = new TextField();
        Label statusLabel = new Label("Status Akun :");
        ComboBox<String> accountStatus = new ComboBox<>();
        accountStatus.itemsProperty().bindBidirectional(updatePageModel.getAccountStatus().itemsProperty());
        ComboBox<String> activity = new ComboBox<>();
        activity.itemsProperty().bindBidirectional(updatePageModel.getActivity().itemsProperty());

        leftVbox.getChildren().addAll(nameLabel, nameTextField, phoneLabel, phoneTextField,
                statusLabel, accountStatus, activity);
        HBox.setMargin(leftVbox, new Insets(10, 10, 10, 20)); // set margin of left VBox in HBox

        VBox rightVbox = new VBox();
//        VBox.setMargin(registerButton, new Insets(0, 30, 30, 0)); // set margin of button in right VBox
        rightVbox.setPadding(new Insets(10, 30, 30, 0)); // set padding of right VBox

        VBox bottomRightVbox = new VBox();
        bottomRightVbox.setAlignment(Pos.BOTTOM_RIGHT);
        bottomRightVbox.setPrefSize(100, 200);

        Button updateButton = new Button("Update");
        bottomRightVbox.getChildren().add(updateButton);

        VBox topRightVbox = new VBox();
        topRightVbox.setAlignment(Pos.TOP_LEFT);
        topRightVbox.setPadding(new Insets(10, 10, 0, 20));

        Label akunLabel = new Label("Pilih Akun :");
        ComboBox<String> accounts = new ComboBox<>();
        accounts.setEditable(true);
        accounts.itemsProperty().bind(updatePageModel.getAccounts().itemsProperty());
        updatePageModel.getAccounts().getEditor().textProperty().bind(accounts.getEditor().textProperty());

        accounts.setMaxWidth(1.7976931348623157E308);
        accounts.setPrefWidth(2.0);
        //set function
        accounts.valueProperty().addListener((obs, oldVal, newVal) -> {
            // Update the TextField text based on the selected value
            updatePageModel.getAccounts().getEditor().textProperty().unbind();
            nameTextField.setText(newVal);
        });

        accounts.getEditor().setOnKeyTyped(event -> {
            // Check if the event source is the editor's text field
            updatePageModel.getAccounts().getEditor().textProperty().bind(accounts.getEditor().textProperty());
        });

        topRightVbox.getChildren().addAll(akunLabel, accounts);

        rightVbox.getChildren().addAll(topRightVbox, bottomRightVbox);
        hbox.getChildren().addAll(leftVbox, rightVbox);
        updatePage.getChildren().addAll(label, hbox);

        VBox.setVgrow(bottomRightVbox, Priority.ALWAYS);
        VBox.setVgrow(topRightVbox, Priority.ALWAYS);
        HBox.setHgrow(leftVbox, Priority.ALWAYS); // set HGrow to fill half of HBox
        HBox.setHgrow(rightVbox, Priority.ALWAYS);
        VBox.setVgrow(hbox, Priority.ALWAYS);

        return updatePage;
    }

    public static VBox getHistoryPage(){
        VBox historyPage = new VBox();
        historyPage.setPadding(new Insets(10));
        historyPage.setSpacing(10);

        Label titleLabel = new Label("Transaction History");
        titleLabel.setFont(new Font(38));
        VBox.setMargin(titleLabel, new Insets(0, 0, 0, 10));

        VBox transactionWidgetsVBox = new VBox();
        transactionWidgetsVBox.setSpacing(10);
        transactionWidgetsVBox.setPadding(new Insets(10));
        for(int i = 0; i < 6; i++){
            TransactionWidget widget1 = new TransactionWidget("John Doe", "12345", "2023-05-01");
            TransactionWidget widget2 = new TransactionWidget("Jane Smith", "67890", "2023-05-02");
            TransactionWidget widget3 = new TransactionWidget("Alice Johnson", "54321", "2023-05-03");

            transactionWidgetsVBox.getChildren().addAll(widget1, widget2, widget3);
        }

        VBox transactionVBox = new VBox();
        Label transactionLabel = new Label("Transaction");
        ScrollPane transactionScrollPane = new ScrollPane(transactionWidgetsVBox);
        transactionScrollPane.setPrefHeight(327.0);
        transactionScrollPane.setMinWidth(230);
        transactionScrollPane.setFitToWidth(true);
        VBox.setVgrow(transactionScrollPane, Priority.ALWAYS);


        transactionVBox.getChildren().addAll(transactionLabel, transactionScrollPane);
        VBox.setMargin(transactionVBox, new Insets(10, 20, 10, 10));

        VBox detailVBox = new VBox();
        VBox.setVgrow(detailVBox, Priority.ALWAYS);
        VBox.setMargin(detailVBox, new Insets(10, 10, 10, 10));

        Label detailLabel = new Label("Detail");
        TextArea detailTextArea = new TextArea();
        detailTextArea.setEditable(false);
        VBox.setVgrow(detailTextArea, Priority.ALWAYS);
        detailVBox.setMinWidth(300);

        detailVBox.getChildren().addAll(detailLabel, detailTextArea);

//        HBox mainHBox = new HBox(transactionVBox, detailVBox);


        SplitPane mainHBox = new SplitPane(transactionVBox, detailVBox);
        HBox.setHgrow(transactionVBox, Priority.ALWAYS);
        HBox.setHgrow(detailVBox, Priority.ALWAYS);

        historyPage.getChildren().addAll(titleLabel, mainHBox);
        VBox.setVgrow(mainHBox, Priority.ALWAYS);
        VBox.setVgrow(transactionVBox, Priority.ALWAYS);
        VBox.setVgrow(detailVBox, Priority.ALWAYS);
        VBox.setVgrow(historyPage, Priority.ALWAYS);

        return historyPage;
    }

    public static VBox getKasirPage(){
        VBox kasirPage = new VBox();
        kasirPage.setPadding(new Insets(10));
        kasirPage.setSpacing(10);

        Label titleLabel = new Label("Kasir");
        titleLabel.setFont(new Font(38));
        VBox.setMargin(titleLabel, new Insets(0, 0, 0, 10));

        TextField searchField = new TextField();
        Button searchButton = new Button("Search");

        // Configure the search button action
        searchButton.setOnAction(event -> {
            String searchTerm = searchField.getText();
            // Perform the search operation
            System.out.println("Performing search: " + searchTerm);
        });

        // Create the search bar layout
        HBox searchBar = new HBox(searchField, searchButton);
        searchBar.setSpacing(10);
        searchBar.setPadding(new Insets(10));

        VBox productVBox = new VBox();
        productVBox.setSpacing(10);
        productVBox.setPadding(new Insets(10));
        for(int i = 0; i < 6; i++){
            ProductWidget widget1 = new ProductWidget("Susumu", "12345", "rp 999999");
            ProductWidget widget2 = new ProductWidget("Anumu", "67890", "rp 80.000");
            ProductWidget widget3 = new ProductWidget("GoofyAhhCrack", "54321", "rp 619");

            productVBox.getChildren().addAll(widget1, widget2, widget3);
        }

        VBox productListVBox = new VBox();
        Label transactionLabel = new Label("Products");
        ScrollPane transactionScrollPane = new ScrollPane(productVBox);
        transactionScrollPane.setPrefHeight(327.0);
        transactionScrollPane.setMinWidth(230);
        transactionScrollPane.setFitToWidth(true);
        VBox.setVgrow(transactionScrollPane, Priority.ALWAYS);


        productListVBox.getChildren().addAll(searchBar,transactionLabel, transactionScrollPane);
        VBox.setMargin(productListVBox, new Insets(10, 20, 10, 10));

        VBox detailVBox = new VBox();
        VBox.setVgrow(detailVBox, Priority.ALWAYS);
        VBox.setMargin(detailVBox, new Insets(10, 10, 10, 10));

        // ngebuat buttonbox
        Label quantityLabel = new Label("1");
        Button plusButton = new Button("+");
        Button minusButton = new Button("-");
        HBox buttonBox = new HBox(10, minusButton, quantityLabel, plusButton);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);
        HBox.setHgrow(buttonBox, Priority.ALWAYS);


        VBox selectedVBox = new VBox();
        selectedVBox.setSpacing(10);
        selectedVBox.setPadding(new Insets(10));

        for(int i = 0; i < 1; i++){
            ProductWidget widget1 = new ProductWidget("Susumu", "12345", "rp 999999");
            ProductWidget widget2 = new ProductWidget("Anumu", "67890", "rp 80.000");
            ProductWidget widget3 = new ProductWidget("GoofyAhhCrack", "54321", "rp 619");

            // nambahin buttonbox untuk setiap widget
            widget1.getChildren().add(widget1.getButtonBox());
            widget2.getChildren().add(widget2.getButtonBox());
            widget3.getChildren().add(widget3.getButtonBox());
            selectedVBox.getChildren().addAll(widget1, widget2, widget3);
        }

        Label detailLabel = new Label("Detail");
        ScrollPane selectedProduct = new ScrollPane(selectedVBox);
        selectedProduct.setFitToWidth(true);
//        selectedProduct.setFitToWidth(true);
        VBox.setVgrow(selectedProduct, Priority.ALWAYS);

        VBox bottomRightVbox = new VBox();
        bottomRightVbox.setAlignment(Pos.BOTTOM_RIGHT);

        Button billButton = new Button("Bill");
        bottomRightVbox.getChildren().addAll(billButton);
        bottomRightVbox.setMinHeight(100);
        VBox.setVgrow(bottomRightVbox, Priority.ALWAYS);

        detailVBox.getChildren().addAll(detailLabel, selectedProduct, bottomRightVbox);

//        HBox mainHBox = new HBox(transactionVBox, detailVBox);
        detailVBox.setPadding(new Insets(45, 0, 0, 0));
        bottomRightVbox.setPadding(new Insets(10, 30, 30, 0));
        SplitPane mainHBox = new SplitPane(productListVBox, detailVBox);
        HBox.setHgrow(productVBox, Priority.ALWAYS);
        HBox.setHgrow(detailVBox, Priority.ALWAYS);

        kasirPage.getChildren().addAll(titleLabel, mainHBox);
        VBox.setVgrow(mainHBox, Priority.ALWAYS);
        VBox.setVgrow(productVBox, Priority.ALWAYS);
        VBox.setVgrow(detailVBox, Priority.ALWAYS);
        VBox.setVgrow(kasirPage, Priority.ALWAYS);

        return kasirPage;
    }


}