package com.biblio.views.report;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JFileChooser;

import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDate;

public class PdfExporter {

    public static void exportTable(
            JTable table,
            String title
    ) {

        JFileChooser chooser = new JFileChooser();

        chooser.setSelectedFile(
                new File(
                        title.replace(" ", "_")
                        + "_"
                        + LocalDate.now()
                        + ".pdf"
                )
        );

        int option = chooser.showSaveDialog(null);

        if (option != JFileChooser.APPROVE_OPTION) {
            return;
        }

        try {

            String path = chooser.getSelectedFile()
                    .getAbsolutePath();

            if (!path.endsWith(".pdf")) {
                path += ".pdf";
            }

            Document document = new Document();

            PdfWriter.getInstance(
                    document,
                    new FileOutputStream(path)
            );

            document.open();

            // =========================
            // TITRE
            // =========================
            Font titleFont = new Font(
                    Font.HELVETICA,
                    18,
                    Font.BOLD
            );

            Paragraph titleParagraph =
                    new Paragraph(title, titleFont);

            titleParagraph.setSpacingAfter(10);

            document.add(titleParagraph);

            // DATE
            document.add(
                    new Paragraph(
                            "Date : " + LocalDate.now()
                    )
            );

            document.add(
                    new Paragraph(" ")
            );

            // =========================
            // TABLEAU
            // =========================

            int columnCount =
                    table.getColumnCount();

            PdfPTable pdfTable =
                    new PdfPTable(columnCount);

            pdfTable.setWidthPercentage(100);

            // HEADERS
            for (int i = 0;
                 i < columnCount;
                 i++) {

                PdfPCell header =
                        new PdfPCell(
                                new Phrase(
                                        table.getColumnName(i)
                                )
                        );

                pdfTable.addCell(header);
            }

            // ROWS
            for (int row = 0;
                 row < table.getRowCount();
                 row++) {

                for (int col = 0;
                     col < columnCount;
                     col++) {

                    Object value =
                            table.getValueAt(
                                    row,
                                    col
                            );

                    pdfTable.addCell(
                            value == null
                            ? ""
                            : value.toString()
                    );
                }
            }

            document.add(pdfTable);

            document.close();

            JOptionPane.showMessageDialog(
                    null,
                    "PDF généré avec succès !"
            );

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    null,
                    "Erreur export PDF : "
                    + e.getMessage()
            );

            e.printStackTrace();
        }
    }
}
