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
import java.util.concurrent.*;

public class HistoryPageControl {
    private final HistoryPageModel historyPageModel;
    private final FixedBillRepository fixedBillRepository;
    private FixedBill currentFB;
    private static final ScheduledExecutorService executor = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());
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
        currentFB = transactionWidget.getFixedBill();
        StringBuilder listItem = new StringBuilder();
        for(Pair<Barang, Integer> item : currentFB.getCart()){
            listItem.append(" > ").append(item.getValue0().getNama()).append("\t.\t.\t.\t.\t.\t.\t.").append(item.getValue1()).append(" x ").append("$").append(item.getValue0().getHarga()).append("\n");
        }
        historyPageModel.getDetailTextArea().setText(
                "UID   :\t" + currentFB.getUserId() +
                        "\nBarang : \n" + listItem +
                        "\n Total : " + currentFB.getTotalPrice()

        );
    }

    public void savePDF(String fileName) throws IOException {
        Laporan laporan = new Laporan(historyPageModel.getSavePath()+"/"+fileName,
                (List<FixedBill>) fixedBillRepository.findAll());
        executor.scheduleAtFixedRate(() -> {
            try {
                laporan.save();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }, 10, 10, TimeUnit.SECONDS);
    }

    public void savePDFcurrent(String fileName) throws IOException {
        if(currentFB != null) {
            Laporan laporan = new Laporan(historyPageModel.getSavePath() + "/" + fileName,
                    currentFB);
            executor.scheduleAtFixedRate(() -> {
                try {
                    laporan.save();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }, 10, 10, TimeUnit.SECONDS);
        }
    }
}
