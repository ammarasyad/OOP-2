package com.tll.gui.factory;

import com.tll.backend.model.bill.TemporaryBill;
import com.tll.backend.model.user.Member;
import com.tll.backend.repository.impl.barang.BarangRepository;
import com.tll.backend.repository.impl.bill.FixedBillRepository;
import com.tll.backend.repository.impl.bill.TemporaryBillRepository;
import com.tll.backend.repository.impl.user.CustomerRepository;
import com.tll.backend.repository.impl.user.MemberRepository;
import com.tll.gui.AutoCompleteComboBox;
import com.tll.gui.controllers.*;
import com.tll.gui.models.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class PageFactory {

    public static VBox getMainPage(MainPageModel mainPageModel){
        VBox mainPage = new VBox();
        MainPageControl mc = new MainPageControl(mainPageModel);
        mc.startClock();
        mainPage.setPrefSize(600, 400);
        mainPage.setAlignment(Pos.TOP_CENTER);

        VBox kelompok = new VBox();

        Label clockLabel = new Label();
        clockLabel.setFont(new Font(49.0));

        Font font = new Font("Arial",20);

        HBox ezra = new HBox();
        Label nnezra = new Label("Ezra M C M H / 13521073");
        nnezra.setFont(font);
        ezra.getChildren().add(nnezra);

        HBox chris = new HBox();
        Label nnchris = new Label("Christian Albert Hasiholan / 13521078");
        nnchris.setFont(font);
        chris.getChildren().add(nnchris);

        HBox tobi = new HBox();
        Label nntobi = new Label("Tobias Natalio Sianipar / 13521090");
        nntobi.setFont(font);
        tobi.getChildren().add(nntobi);

        HBox ammar = new HBox();
        Label nnammar = new Label("Ammar Rasyad Chaeroel  / 13521136");
        nnammar.setFont(font);
        ammar.getChildren().add(nnammar);

        HBox zidane = new HBox();
        Label nnzidane = new Label("Zidane Firzatullah / 13521163");
        nnzidane.setFont(font);
        zidane.getChildren().add(nnzidane);

        Label bottomLabel = new Label();
        LocalDate currentDate = LocalDate.now();
        Label dateLabel = new Label(currentDate.toString());
        dateLabel.setFont(font);

        clockLabel.textProperty().bindBidirectional(mainPageModel.getClockLabel().textProperty());
        bottomLabel.textProperty().bindBidirectional(mainPageModel.getBottomLabel().textProperty());
        //        mainPageController.startClock();
//        mainPageController.startClock();

        kelompok.getChildren().addAll(ezra, chris, tobi, ammar, zidane);
        ezra.setAlignment(Pos.BOTTOM_CENTER);
        chris.setAlignment(Pos.BOTTOM_CENTER);
        tobi.setAlignment(Pos.BOTTOM_CENTER);
        ammar.setAlignment(Pos.BOTTOM_CENTER);
        zidane.setAlignment(Pos.BOTTOM_CENTER);

        String imagepath = System.getProperty("user.dir") + "/" + "gui/src/main/resources/Telolet.png";

        ImageView imageView = new ImageView();
        Image image = new Image(imagepath);
        imageView.setImage(image);
        imageView.setFitWidth(540);
        imageView.setFitHeight(360);
//        region.getChildren().add(imageView);
        //kelompok.setSpacing(10);
        //kelompok.setAlignment(Pos.BOTTOM_RIGHT );
        //ezra.setMargin()
        //VBox.setMargin(kelompok, new Insets(350,0,0,0));
        kelompok.setAlignment(Pos.BOTTOM_CENTER);
        VBox.setVgrow(kelompok, Priority.ALWAYS);
        mainPage.getChildren().addAll(clockLabel, dateLabel, imageView, kelompok);

        VBox.setVgrow(mainPage, Priority.ALWAYS);
        //mainPage.setSpacing(5);


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
        TextField nameTextField = registerPageModel.getNameTextField();
        nameTextField.setPromptText("e.g. Hayam Wuruk");

        Label phoneLabel = new Label("Nomor Telepon :");
        TextField phoneTextField = registerPageModel.getPhoneTextField();
        phoneTextField.setPromptText("e.g. 081806122004");

        Label idLabel = new Label("Id Member :");
        AutoCompleteComboBox idComboBox = registerPageModel.getAccounts();
        idComboBox.setEditable(true);

        leftVbox.getChildren().addAll(nameLabel, nameTextField, phoneLabel, phoneTextField, idLabel, idComboBox);
        HBox.setMargin(leftVbox, new Insets(10, 10, 10, 20)); // set margin of left VBox in HBox

        VBox rightVbox = new VBox();
        rightVbox.setAlignment(Pos.BOTTOM_RIGHT);
        rightVbox.setPrefSize(100, 200);

        Button registerButton = new Button("Register");
        registerButton.setOnAction(actionEvent -> {
            if (!checkEmpty(nameTextField, phoneTextField) || idComboBox.getSelectionModel().isEmpty()) {
                return;
            }
            registerPageControl.createMember();
            revertRedBorder(nameTextField, phoneTextField);
        });
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
        TextField nameTextField = updatePageModel.getNameTextField();
        Label phoneLabel = new Label("Nomor Telepon :");
        TextField phoneTextField = updatePageModel.getPhoneTextField();
        Label statusLabel = new Label("Tipe Akun :");
        ComboBox<String> accountStatus = updatePageModel.getAccountStatus();
        ComboBox<String> activity = updatePageModel.getActivity();

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
        updateButton.setOnAction(e -> updatePageControl.saveChanges());
        bottomRightVbox.getChildren().add(updateButton);

        VBox topRightVbox = new VBox();
        topRightVbox.setAlignment(Pos.TOP_LEFT);
        topRightVbox.setPadding(new Insets(10, 10, 0, 20));

        Label akunLabel = new Label("Pilih Akun :");
        AutoCompleteComboBox<Member> accounts = updatePageModel.getAccounts();
        accounts.setEditable(true);

        accounts.setMaxWidth(1.7976931348623157E308);
        accounts.setPrefWidth(2.0);

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

    public static VBox getHistoryPage(FixedBillRepository fixedBillRepository, HistoryPageModel historyPageModel){
        VBox historyPage = new VBox();
        HistoryPageControl historyPageControl = new HistoryPageControl(fixedBillRepository, historyPageModel);
        historyPage.setPadding(new Insets(10));
        historyPage.setSpacing(10);

        Label titleLabel = new Label("Transaction History");
        titleLabel.setFont(new Font(38));
        VBox.setMargin(titleLabel, new Insets(0, 0, 0, 10));

        VBox transactionWidgetsVBox = historyPageModel.getTransactionContainer();
        transactionWidgetsVBox.setSpacing(10);
        transactionWidgetsVBox.setPadding(new Insets(10));

        VBox transactionVBox = new VBox();
        Label transactionLabel = new Label("Transaction");
        Button refreshButton = new Button("↻");
        HBox refreshHbox = new HBox(refreshButton);
        HBox.setHgrow(refreshHbox, Priority.ALWAYS);
        refreshHbox.setAlignment(Pos.BOTTOM_RIGHT);
        HBox transHbox = new HBox(transactionLabel, refreshHbox);
        HBox.setHgrow(transHbox, Priority.ALWAYS);
        ScrollPane transactionScrollPane = new ScrollPane(transactionWidgetsVBox);
        transactionScrollPane.setPrefHeight(327.0);
        transactionScrollPane.setMinWidth(230);
        transactionScrollPane.setFitToWidth(true);
        VBox.setVgrow(transactionScrollPane, Priority.ALWAYS);

        refreshButton.setOnAction(actionEvent -> historyPageControl.refreshHistory());


        transactionVBox.getChildren().addAll(transHbox, transactionScrollPane);
        VBox.setMargin(transactionVBox, new Insets(10, 20, 10, 10));

        VBox detailVBox = new VBox();
        VBox.setVgrow(detailVBox, Priority.ALWAYS);
        VBox.setMargin(detailVBox, new Insets(10, 10, 10, 10));

        Label detailLabel = new Label("Detail");
        TextArea detailTextArea = historyPageModel.getDetailTextArea();
        detailTextArea.setEditable(false);
        VBox.setVgrow(detailTextArea, Priority.ALWAYS);
        detailVBox.setMinWidth(300);

        HBox bottomRightHbox = new HBox();
        Button getPathButton = new Button("Select Folder");
        DirectoryChooser directoryChooser = new DirectoryChooser();

        Stage fileStage = new Stage();
        getPathButton.setOnAction(e -> {
            File selectedFile = directoryChooser.showDialog(fileStage);
            if (selectedFile != null) {
                historyPageModel.setSavePath(selectedFile.getAbsolutePath());
            }
        });

        TextField path = new TextField();
        path.setPromptText("Enter save folder");
        TextField fileName = new TextField();
        fileName.setPromptText("Enter file name");
        Button savePDFButton = new Button("Save All");
        Button saveOnePDFButton = new Button("Save Details");
        savePDFButton.setOnAction(e -> {
            try {
                historyPageControl.savePDF(fileName.getText()+".pdf");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        saveOnePDFButton.setOnAction(e -> {
            try {
                historyPageControl.savePDFcurrent(fileName.getText()+".pdf");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        HBox hbox1 = new HBox(path,getPathButton);
        HBox hBox2 = new HBox(fileName,savePDFButton,saveOnePDFButton);
        HBox.setHgrow(hBox2, Priority.ALWAYS);
        HBox.setHgrow(hbox1, Priority.ALWAYS);
        bottomRightHbox.getChildren().addAll(hbox1, hBox2);
        HBox.setHgrow(bottomRightHbox, Priority.ALWAYS);

        detailVBox.getChildren().addAll(detailLabel, detailTextArea, bottomRightHbox);

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

    public static VBox getKasirPage(TemporaryBillRepository temporaryBillRepository, BarangRepository barangRepository, FixedBillRepository fixedBillRepository,
                                    CustomerRepository customerRepository, MemberRepository memberRepository, KasirPageModel kasirPageModel, List<NodeFactory> additions){
        VBox kasirPage = new VBox();
        KasirPageControl kasirPageControl = new KasirPageControl(temporaryBillRepository, fixedBillRepository, barangRepository, customerRepository, memberRepository, kasirPageModel);

        kasirPage.setPadding(new Insets(10));
        kasirPage.setSpacing(10);

        Label titleLabel = new Label("Kasir");
        titleLabel.setFont(new Font(38));
        VBox.setMargin(titleLabel, new Insets(0, 0, 0, 10));

        TextField searchField = new TextField();
        Button searchButton = new Button("Search");

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            // your function logic here
            kasirPageControl.searchByName(newValue);
        });

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

        FlowPane productVBox = kasirPageModel.getProductsList();
//        productVBox.setSpacing(10);
        productVBox.setPadding(new Insets(10));
        productVBox.setHgap(10);
        productVBox.setVgap(10);

        VBox selectedVBox = kasirPageModel.getSelectedItem();
        selectedVBox.setSpacing(10);
        selectedVBox.setPadding(new Insets(10));

        VBox productListVBox = new VBox();
        Label transactionLabel = new Label("Products");
        Button refreshButton = new Button("↻");
        HBox refreshHbox = new HBox(refreshButton);
        HBox.setHgrow(refreshHbox, Priority.ALWAYS);
        refreshHbox.setAlignment(Pos.BOTTOM_RIGHT);
        HBox transHbox = new HBox(transactionLabel, refreshHbox);
        HBox.setHgrow(transHbox, Priority.ALWAYS);
        ScrollPane selectedPane = new ScrollPane(productVBox);
        selectedPane.setPrefHeight(327.0);
        selectedPane.setMinWidth(230);
        selectedPane.setFitToWidth(true);
        VBox.setVgrow(selectedPane, Priority.ALWAYS);

        productListVBox.getChildren().addAll(searchBar,transHbox, selectedPane);
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

        Label detailLabel = kasirPageModel.getBillStatus();
        detailLabel.setPadding(new Insets(0, 0, 9, 10));
        ScrollPane selectedProduct = new ScrollPane(selectedVBox);
        selectedProduct.setFitToWidth(true);
        selectedProduct.setPrefHeight(300);
        selectedProduct.setMaxHeight(300);
//        selectedProduct.setFitToWidth(true);
        VBox.setVgrow(selectedProduct, Priority.ALWAYS);

        VBox bottomRightVbox = new VBox();
        bottomRightVbox.setAlignment(Pos.BOTTOM_RIGHT);

        HBox leftSetuHbox = new HBox();
        Button saveTempButton = new Button("Save Bill");
        leftSetuHbox.getChildren().addAll(saveTempButton);
        HBox.setHgrow(leftSetuHbox, Priority.ALWAYS);

        HBox setuHbox = new HBox();
        Button billButton = new Button("Checkout");
        AutoCompleteComboBox<Member> members = kasirPageModel.getMembers();
        HBox.setHgrow(setuHbox, Priority.ALWAYS);
        members.setPrefWidth(220);
        setuHbox.setAlignment(Pos.BOTTOM_RIGHT);
        CheckBox useMember = kasirPageModel.getUseMember();
        useMember.setStyle("-fx-font-size: 16px;");
        useMember.setPadding(new Insets(0, 10, 0, 10));
        setuHbox.getChildren().addAll(leftSetuHbox,new Label("Use Member"), useMember, members,billButton);

        VBox kasirAdditionVBox = new VBox();
        VBox.setVgrow(kasirAdditionVBox, Priority.ALWAYS);
        additions.forEach(el -> {
            kasirAdditionVBox.getChildren().add(el.getNode());
        });

        bottomRightVbox.getChildren().addAll(kasirAdditionVBox, setuHbox);
        bottomRightVbox.setMinHeight(100);
        VBox.setVgrow(bottomRightVbox, Priority.ALWAYS);
        billButton.setOnAction(event -> {
            kasirPageControl.checkOut();
            billButton.setOnAction(event1 -> {});
        });
        saveTempButton.setOnAction(event -> kasirPageControl.saveTemporaryBill());
        refreshButton.setOnAction(event -> kasirPageControl.refreshProductList());

        detailVBox.getChildren().addAll(detailLabel, selectedProduct, bottomRightVbox);

//        HBox mainHBox = new HBox(transactionVBox, detailVBox);
        detailVBox.setPadding(new Insets(45, 0, 0, 0));
        bottomRightVbox.setPadding(new Insets(10, 30, 30, 30));
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

    public static VBox getSetting(List<File> fileList, SettingPageModel settingPageModel, List<Node> pluginNodes) {
        VBox settingPage = new VBox();
        SettingPageControl settingPageControl = new SettingPageControl(fileList, settingPageModel);
        settingPage.setSpacing(10);

        Label titleLabel = new Label("Setting");
        titleLabel.setFont(new Font(38));
        VBox.setMargin(titleLabel, new Insets(0, 0, 0, 10));

        VBox mainVbox = new VBox();
        mainVbox.setStyle("-fx-background-color: #ddd;");

        VBox dataVbox = new VBox();
        dataVbox.setPrefSize(100, 200);
        dataVbox.setSpacing(10);
        dataVbox.setPadding(new Insets(10));

        Label databoxTitle = new Label("Data Store");
        databoxTitle.setFont(new Font(24));
        VBox.setMargin(databoxTitle, new Insets(0, 0, 0, 0));

        HBox loadHbox = new HBox();
        FileChooser loadFileChooser = new FileChooser();

        Stage loadStage = new Stage();
        loadStage.setTitle("Select File");

        Button selectButton = new Button("Open File");
        Label fileLabel = new Label("File :");
        fileLabel.setFont(new Font(14));

        selectButton.setOnAction(e -> {
            File selectedFile = loadFileChooser.showOpenDialog(loadStage);
            fileLabel.setText("File :" + selectedFile.getName());
        });

        loadHbox.setSpacing(40);
        loadHbox.getChildren().addAll(selectButton, fileLabel);
        loadHbox.setAlignment(Pos.CENTER_LEFT);

        HBox formatHbox = new HBox();
        Label formatLabel = new Label("Format File :");
        formatLabel.setFont(new Font(14));

        ComboBox formatFile = new ComboBox<>();
        formatFile.getItems().addAll("JSON", "XML");
        formatFile.getSelectionModel().selectFirst();
        formatHbox.getChildren().addAll(formatLabel, formatFile);
        formatHbox.setSpacing(40);
        formatHbox.setAlignment(Pos.CENTER_LEFT);

        HBox saveHbox = new HBox();
        FileChooser saveFileChooser = new FileChooser();
        if (formatFile.getSelectionModel().getSelectedItem() == "JSON") {
            saveFileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON File", "*.json"));
        }
        else {
            saveFileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML File", "*.xml"));
        }

        Stage saveStage = new Stage();
        saveStage.setTitle("Select File");

        Button saveButton = new Button("Save File");
        Label saveLabel = new Label("");
        fileLabel.setFont(new Font(14));

        saveButton.setOnAction(e -> {
            File selectedFile = saveFileChooser.showSaveDialog(saveStage);
            if (selectedFile.exists()) {
                saveLabel.setText("File saved");
            }
            else {
                saveLabel.setText("Failed to save file. Try again");
            }
        });

        saveHbox.setSpacing(40);
        saveHbox.getChildren().addAll(saveButton, saveLabel);
        saveHbox.setAlignment(Pos.CENTER_LEFT);

        dataVbox.setSpacing(20);
        dataVbox.setStyle("-fx-border-color: black;" + "-fx-border-style: hidden hidden solid hidden");
        dataVbox.getChildren().addAll(databoxTitle, loadHbox, formatHbox, saveHbox);

        VBox pluginVbox = new VBox();
        pluginVbox.setPrefSize(100, 200);
        pluginVbox.setSpacing(10);
        pluginVbox.setPadding(new Insets(10));

        Label pluginboxTitle = new Label("Plugin");
        pluginboxTitle.setFont(new Font(24));
        VBox.setMargin(pluginboxTitle, new Insets(0, 0, 0, 0));

        FileChooser pluginChooser = new FileChooser();

        Stage pluginStage = new Stage();
        pluginStage.setTitle("Select File");

        Button selectPluginButton = new Button("Select File");

        selectPluginButton.setOnAction(e -> {
            File selectedFile = pluginChooser.showOpenDialog(loadStage);
            if (selectedFile != null) {
                fileList.add(selectedFile);
                settingPageControl.refreshFileList();
            }
        });

        FlowPane pluginListBox = settingPageModel.getPluginList();
        pluginListBox.getChildren().addAll(pluginNodes);
        pluginListBox.setStyle("-fx-border-color: black;");
        pluginListBox.setMinHeight(200);
        pluginListBox.setPadding(new Insets(10));
        pluginListBox.setHgap(100);
        pluginListBox.setVgap(10);

        pluginVbox.getChildren().setAll(pluginboxTitle, selectPluginButton, pluginListBox);

        mainVbox.getChildren().addAll(dataVbox, pluginVbox);

        settingPage.getChildren().addAll(titleLabel, mainVbox);

        VBox.setVgrow(mainVbox, Priority.ALWAYS);

        return settingPage;
    }
    public static VBox getInsertBarangPage(BarangRepository barangRepository){
        VBox InsertPage = new VBox();
        InsertPage.setPadding(new Insets(10));
        InsertPage.setSpacing(10);

        InsertPage.setPrefSize(600, 400);
        InsertPage.setAlignment(Pos.TOP_LEFT);

        Label label = new Label("Tambah barang");
        label.setFont(new Font(38.0));
        VBox.setMargin(label, new Insets(0, 0, 0, 10)); // set left margin of label

        HBox hbox = new HBox();
        hbox.setStyle("-fx-background-color: #ddd;");

        VBox leftVbox = new VBox();
        leftVbox.setPrefSize(100, 200);
        leftVbox.setSpacing(10);
        leftVbox.setPadding(new Insets(10));

        Label stokLabel = new Label("Stok :");
        TextField stokTextField = new TextField();
        stokTextField.setPromptText("e.g. 5 ");

        Label nameLabel = new Label("Nama Barang :");
        TextField nameTextField = new TextField();
        nameTextField.setPromptText("e.g. Barang ");

        Label PriceLabel = new Label("Harga :");
        TextField PriceTextField = new TextField();
        PriceTextField.setPromptText("e.g. 20000");

        Label BuyPriceLabel = new Label("Harga Beli :");
        TextField BuyPriceTextField = new TextField();
        BuyPriceTextField.setPromptText("e.g. 10000");

        Label KategoriLabel = new Label("Kategori :");
        TextField KategoriTextField = new TextField();
        KategoriTextField.setPromptText("e.g. Makan");

        Label OnSaleLabel = new Label("Dijual :");
        HBox saleHbox = new HBox();
        RadioButton fs = new RadioButton("iya");
        fs.setPadding(new Insets(0, 20, 0, 0));
        RadioButton nfs = new RadioButton("tidak");
        saleHbox.getChildren().addAll(fs, nfs);

        HBox fileHbox = new HBox();
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPG Files", "*.jpg"),
                new FileChooser.ExtensionFilter("JPEG Files", "*.jpeg"),
                new FileChooser.ExtensionFilter("PNG Files", "*.png")

        );

        Stage fileStage = new Stage();
        fileStage.setTitle("Select File");

        Button selectButton = new Button("Select File");
        Label fileLabel = new Label("File :");
        fileLabel.setFont(new Font(14));

        AtomicReference<File> selectedFile = new AtomicReference<>();
        selectButton.setOnAction(e -> {
            selectedFile.set(fileChooser.showOpenDialog(fileStage));
            fileLabel.setText("File :" + selectedFile.get().getName());
        });

        fileHbox.setSpacing(40);
        fileHbox.getChildren().addAll(selectButton, fileLabel);
        fileHbox.setAlignment(Pos.CENTER_LEFT);

        leftVbox.getChildren().addAll(stokLabel, stokTextField ,nameLabel, nameTextField, PriceLabel, PriceTextField,BuyPriceLabel,
                BuyPriceTextField, KategoriLabel, KategoriTextField, OnSaleLabel, saleHbox, fileHbox);
        HBox.setMargin(leftVbox, new Insets(10, 10, 10, 20)); // set margin of left VBox in HBox

        VBox rightVbox = new VBox();
        rightVbox.setAlignment(Pos.BOTTOM_RIGHT);
        rightVbox.setPrefSize(100, 200);

//        System.out.println(selectedFile.get().getName());
        Button TambahButton = new Button("Tambah Barang");
        TambahButton.setOnAction(event -> PageActionFactory.doInsertBarang( stokTextField.getText(), nameTextField.getText(),
                PriceTextField.getText(), BuyPriceTextField.getText(), KategoriTextField.getText(),
                selectedFile.get().getName(),true ,barangRepository));

        rightVbox.getChildren().add(TambahButton);

        VBox.setMargin(TambahButton, new Insets(0, 30, 30, 0)); // set margin of button in right VBox
        rightVbox.setPadding(new Insets(0, 30, 30, 0)); // set padding of right VBox

        hbox.getChildren().addAll(leftVbox, rightVbox);
        InsertPage.getChildren().addAll(label, hbox);

        HBox.setHgrow(leftVbox, Priority.ALWAYS); // set HGrow to fill half of HBox
        HBox.setHgrow(rightVbox, Priority.ALWAYS);
        VBox.setVgrow(hbox, Priority.ALWAYS);

        return InsertPage;
    }

    private static boolean checkEmpty(TextField... textFields) {
        var foundEmpty = false;
        for (var textField: textFields) {
            if (textField.getText().isBlank()) {
                textField.setStyle("-fx-border-color: red");
                foundEmpty = true;
            }
        }
        return !foundEmpty;
    }

    private static void revertRedBorder(TextField... textFields) {
        for (var textField: textFields) {
            textField.setStyle("-fx-border-color: none");
        }
    }

    public static VBox getKasirPage(TemporaryBill temporaryBill){
        return getKasirPage(temporaryBill);
    }


}