package com.tll.backend.laporan;

import com.tll.backend.model.bill.FixedBill;
import lombok.AllArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;

import java.util.ArrayList;
import java.util.List;

import java.awt.*;
import java.math.BigDecimal;
import java.io.IOException;
import java.util.concurrent.*;

@AllArgsConstructor
public class Laporan {
    private final String path;
    private final List<FixedBill> bills;

    public Laporan(String path, FixedBill bill) {
        this(path, List.of(bill));
    }

    // Submit this to a thread pool
    public void save() throws IOException {
        List<LaporanPage> pages = new ArrayList<>();
        try (PDDocument document = new PDDocument()) {
            for (var bill : bills) {
                pages.add(buildPage(document, bill));
            }

            for (var page : pages) {
                document.addPage(page);
            }
            document.save(path);
        }
    }

    private LaporanPage buildPage(PDDocument document, FixedBill bill) throws IOException {
        LaporanPage page = new LaporanPage();
        int pageHeight = (int) page.getMediaBox().getHeight();
        try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
            contentStream.setStrokingColor(Color.DARK_GRAY);
            contentStream.setLineWidth(1);

            int x = 50;
            int y = pageHeight - 50;

            int height = 25;
            int width = 80;

            for (var o : bill.getCart()) {
                var barang = o.getValue0();
                var harga = barang.getHarga().multiply(new BigDecimal(o.getValue1()));

                contentStream.addRect(x, y, width + 300, -height);

                contentStream.beginText();
                contentStream.newLineAtOffset(x + 30, y - height + 10);
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.TIMES_ROMAN), 12);
                contentStream.showText(barang.getNama());
                contentStream.endText();

                x += width + 300;

                contentStream.addRect(x, y, width + 50, -height);

                contentStream.beginText();
                contentStream.newLineAtOffset(x + 30, y - height + 10);
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.TIMES_ROMAN), 12);
                contentStream.showText(harga.toString());
                contentStream.endText();

                y -= height;
                x = 50;
            }

            contentStream.stroke();
        }
        return page;
    }
}
