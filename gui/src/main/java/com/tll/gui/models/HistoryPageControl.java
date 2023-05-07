package com.tll.gui.models;

import com.tll.backend.laporan.Laporan;
import com.tll.backend.model.barang.Barang;
import com.tll.backend.model.bill.FixedBill;
import com.tll.backend.repository.impl.bill.FixedBillRepository;
import com.tll.gui.ProductWidget;
import com.tll.gui.TransactionWidget;
import com.tll.gui.controllers.HistoryPageModel;
import org.javatuples.Pair;

import java.io.IOException;
import java.util.List;

public class HistoryPageControl {
    private HistoryPageModel historyPageModel;
    private FixedBillRepository fixedBillRepository;
    public HistoryPageControl(FixedBillRepository fixedBillRepository, HistoryPageModel historyPageModel){
        this.historyPageModel = historyPageModel;
        this.fixedBillRepository = fixedBillRepository;
        refreshHistory();
    }

    public void refreshHistory(){
        System.out.println("refreshed");
        historyPageModel.getTransactionContainer().getChildren().clear();
        for(FixedBill fixedBill : fixedBillRepository.findAll()){
            TransactionWidget transactionWidget = new TransactionWidget(fixedBill);

            transactionWidget.setOnMouseClicked(event -> {
                showDetails(transactionWidget);
            });

            historyPageModel.getTransactionContainer().getChildren().addAll(transactionWidget);
        }
    }

    public void showDetails(TransactionWidget transactionWidget){
        FixedBill fixedBill = transactionWidget.getFixedBill();
        String listItem = "";
        for(Pair<Barang, Integer> item : fixedBill.getCart()){
            listItem = listItem + " > " + item.getValue0().getNama() + "\t.\t.\t.\t.\t.\t.\t." + item.getValue1() + " x "
                        + "$" + item.getValue0().getHarga() + "\n";
        }
        historyPageModel.getDetailTextArea().setText(
                        "UID   :\t" + fixedBill.getUserId() +
                        "\nBarang : \n" + listItem +
                                "\n Total : " + fixedBill.getTotalPrice()

        );
    }

    public void savePDF(String fileName) throws IOException {
        Laporan laporan = new Laporan(historyPageModel.getSavePath()+"\\"+fileName,
                (List<FixedBill>) fixedBillRepository.findAll());
        laporan.save();
    }
}
