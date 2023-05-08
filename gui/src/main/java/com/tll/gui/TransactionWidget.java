package com.tll.gui;

import com.tll.backend.model.bill.FixedBill;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import lombok.Getter;

@Getter
public class TransactionWidget extends HBox {
    private Label uidLabel;
    private Label idLabel;
    private Label totalLabel;
    private FixedBill fixedBill;

    public TransactionWidget(FixedBill fixedBill) {
        super();
        this.fixedBill = fixedBill;
        uidLabel = new Label("UID: " + fixedBill.getUserId());
        uidLabel.setPadding(new Insets(5, 0, 0, 5));
        idLabel = new Label("BILL ID: " + fixedBill.getId());
        idLabel.setPadding(new Insets(5, 0, 0, 5));
        totalLabel = new Label("TOTAL: " + fixedBill.getTotalPrice());
        totalLabel.setPadding(new Insets(5, 0, 0, 5));

        // Customize labels if needed

        setPadding(new Insets(5));
        getChildren().addAll(uidLabel, idLabel, totalLabel);
        HBox.setHgrow(this, Priority.ALWAYS);

        setMaxWidth(1.7976931348623157E308);
        setPrefWidth(2);

        // Add border to the TransactionWidget
        BorderStroke borderStroke = new BorderStroke(
                Color.BLACK,
                BorderStrokeStyle.SOLID,
                null,
                new BorderWidths(1));
        setBorder(new Border(borderStroke));
    }
}
