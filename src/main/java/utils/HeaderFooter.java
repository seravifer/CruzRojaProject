package utils;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HeaderFooter extends PdfPageEventHelper {
  public void onStartPage(PdfWriter pdfWriter, Document document) {
      try {
          com.itextpdf.text.Image head = com.itextpdf.text.Image.getInstance("src/main/resources/img/pdf_header.png");
          document.add(head);
      } catch (BadElementException ex) {
          Logger.getLogger(HeaderFooter.class.getName()).log(Level.SEVERE, null, ex);
      } catch (IOException ex) {
          Logger.getLogger(HeaderFooter.class.getName()).log(Level.SEVERE, null, ex);
      } catch (DocumentException ex) {
          Logger.getLogger(HeaderFooter.class.getName()).log(Level.SEVERE, null, ex);
      }
  }
 
  public void onCloseDocument(PdfWriter pdfWriter, Document document) {
      System.out.println("HOLA");
      try {
          com.itextpdf.text.Image foot = com.itextpdf.text.Image.getInstance("src/main/resources/img/pdf_footer.png");
          document.add(foot);
      } catch (BadElementException ex) {
          Logger.getLogger(HeaderFooter.class.getName()).log(Level.SEVERE, null, ex);
      } catch (IOException ex) {
          Logger.getLogger(HeaderFooter.class.getName()).log(Level.SEVERE, null, ex);
      } catch (DocumentException ex) {
          Logger.getLogger(HeaderFooter.class.getName()).log(Level.SEVERE, null, ex);
      }
  }
}