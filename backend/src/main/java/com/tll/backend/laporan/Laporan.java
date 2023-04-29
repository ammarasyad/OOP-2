package com.tll.backend.laporan;

import com.tll.backend.model.barang.Barang;
import com.tll.backend.model.bill.FixedBill;
import lombok.AllArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.javatuples.Pair;

import java.awt.*;
import java.math.BigDecimal;
import java.io.IOException;

@AllArgsConstructor
public class Laporan {
    private final String path;
    private final FixedBill bill;

    // Spawns a new thread to save the document, with a 10-second delay at the end
    public void save() {
        new Thread(() -> {
            LaporanPage page = new LaporanPage();
            try (PDDocument document = new PDDocument();
                 PDPageContentStream contentStream = new PDPageContentStream(document, page)) {

                buildTable(page, contentStream);

                document.addPage(page);
                document.save(path);

                Thread.sleep(10000);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void buildTable(LaporanPage page, PDPageContentStream contentStream) throws IOException {
        int pageHeight = (int) page.getMediaBox().getHeight();

        contentStream.setStrokingColor(Color.DARK_GRAY);
        contentStream.setLineWidth(1);

        int x = 50;
        int y = pageHeight - 50;

        int height = 25;
        int width = 80;

        for (Pair<Barang, Integer> o : bill.getCart()) {
            contentStream.addRect(x, y, width + 30, -height);

            contentStream.beginText();
            contentStream.newLineAtOffset(x + 30, y - height + 10);
            contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.TIMES_ROMAN), 12);
            contentStream.showText(o.getValue0().getNama());
            contentStream.endText();

            x += width + 30;

            contentStream.addRect(x, y, width + 30, -height);

            contentStream.beginText();
            contentStream.newLineAtOffset(x + 30, y - height + 10);
            contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.TIMES_ROMAN), 12);
            contentStream.showText(o.getValue0().getHarga().multiply(new BigDecimal(o.getValue1())).toString());
            contentStream.endText();

            y -= height;
            x = 50;
        }

        contentStream.stroke();
    }
}
