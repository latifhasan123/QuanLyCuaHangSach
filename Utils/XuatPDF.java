package Utils;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.FileOutputStream;
import java.util.ArrayList;
import javax.swing.JTable;

public class XuatPDF {
    private static Font fontData = FontFactory.getFont("C:\\Windows\\Fonts\\Arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 12);
    private static Font fontTitle = FontFactory.getFont("C:\\Windows\\Fonts\\Arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 18, Font.BOLD);

    public void ghiPDF(String tenFile, String tieuDe, JTable table, ArrayList<String> info) {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(tenFile + ".pdf"));
            document.open();

            Paragraph title = new Paragraph(tieuDe, fontTitle);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph(" ")); 

            for (String s : info) {
                document.add(new Paragraph(s, fontData));
            }
            document.add(new Paragraph(" "));

            PdfPTable pdfTable = new PdfPTable(table.getColumnCount());
            pdfTable.setWidthPercentage(100);

            for (int i = 0; i < table.getColumnCount(); i++) {
                pdfTable.addCell(new Phrase(table.getColumnName(i), fontData));
            }

            for (int r = 0; r < table.getRowCount(); r++) {
                for (int c = 0; c < table.getColumnCount(); c++) {
                    pdfTable.addCell(new Phrase(table.getValueAt(r, c).toString(), fontData));
                }
            }

            document.add(pdfTable);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}