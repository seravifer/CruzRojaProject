package utils;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.IOException;

public class HeaderFooter extends PdfPageEventHelper {

    public void onStartPage(PdfWriter pdfWriter, Document document) {
        try {
            com.itextpdf.text.Image head = com.itextpdf.text.Image.getInstance("src/main/resources/img/pdf_header.png");
            document.add(head);
        } catch (IOException | DocumentException ex) {
            ex.printStackTrace();
        }
    }

    public void onCloseDocument(PdfWriter pdfWriter, Document document) {
        System.out.println("HOLA");
        try {
            com.itextpdf.text.Image foot = com.itextpdf.text.Image.getInstance("src/main/resources/img/pdf_footer.png");
            document.add(foot);
        } catch (IOException | DocumentException ex) {
            ex.printStackTrace();
        }
    }

}