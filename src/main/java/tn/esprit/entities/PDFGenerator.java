package tn.esprit.entities;

import javafx.stage.FileChooser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class PDFGenerator {

    private static final int MARGIN = 100;
    private static final int LINE_SPACING = 20;

    public void generatePDF(Map<Integer, Panier> panierMap) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save PDF File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PDF Files", "*.pdf")
        );
        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            try (PDDocument document = new PDDocument()) {
                PDPage page = new PDPage();
                document.addPage(page);

                try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                    contentStream.beginText();
                    contentStream.newLineAtOffset(MARGIN, 700);
                    contentStream.showText("Basket Contents");
                    contentStream.endText();

                    drawTableHeaders(contentStream);

                    int y = 650;
                    for (Panier panier : panierMap.values()) {
                        y -= LINE_SPACING;
                        contentStream.beginText();
                        contentStream.newLineAtOffset(MARGIN, y);
                        contentStream.showText(panier.getNom_prod());
                        contentStream.newLineAtOffset(150, 0);
                        contentStream.showText(String.valueOf(panier.getPrix_prod()));
                        contentStream.endText();
                    }

                    drawTotalPrice(contentStream, panierMap);
                }

                document.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void drawTableHeaders(PDPageContentStream contentStream) throws IOException {
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
        contentStream.beginText();
        contentStream.newLineAtOffset(MARGIN, 630);
        contentStream.showText("Product Name");
        contentStream.newLineAtOffset(150, 0);
        contentStream.showText("Price");
        contentStream.endText();
    }

    private void drawTotalPrice(PDPageContentStream contentStream, Map<Integer, Panier> panierMap) throws IOException {
        double totalPrice = 0;
        for (Panier panier : panierMap.values()) {
            totalPrice += panier.getPrix_prod();
        }
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
        contentStream.beginText();
        contentStream.newLineAtOffset(MARGIN, 50);
        contentStream.showText("Total Price: " + totalPrice);
        contentStream.endText();
    }
}
