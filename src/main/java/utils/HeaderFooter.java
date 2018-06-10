package utils;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.IOException;

public class HeaderFooter extends PdfPageEventHelper {

    public void onStartPage(PdfWriter pdfWriter, Document document) {
        try {
            Image head = Image.getInstance(getClass().getResource("/img/pdf_header.png"));
            document.add(head);
        } catch (IOException | DocumentException ex) {
            ex.printStackTrace();
        }
    }

    public void onCloseDocument(PdfWriter pdfWriter, Document document) {
        try {
            Image foot = Image.getInstance(getClass().getResource("/img/pdf_footer.png"));
            document.add(foot);
        } catch (IOException | DocumentException ex) {
            ex.printStackTrace();
        }
    }

}