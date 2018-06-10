package utils;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;

import javax.imageio.ImageIO;
import java.io.*;
import java.util.List;

public class ReportPDFHelper {

    private static final com.itextpdf.text.Font catFont =
            new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.COURIER, 18,
                    com.itextpdf.text.Font.BOLD);

    private static final com.itextpdf.text.Font dugFont =
            new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.COURIER, 12,
                    com.itextpdf.text.Font.NORMAL);

    private Document document;

    public ReportPDFHelper(File file, List<String> text, List<Node> graphs) throws DocumentException, IOException {
        document = new Document();
        PdfWriter instance = PdfWriter.getInstance(document, new FileOutputStream(file));
        HeaderFooter pdfEvent = new HeaderFooter();
        instance.setPageEvent(pdfEvent);
        document.open();
        addTitle(text.get(0));

        for (int i = 1; i < text.size(); i++) {
            try {
                addText(text.get(i));
                if (!graphs.isEmpty()) {
                    addGraph(graphs.get(i - 1));
                }
            } catch (Exception ignored) {}
        }

        document.close();
    }

    private void addTitle(String text) throws DocumentException {
        Paragraph p = new Paragraph(text, catFont);
        document.add(p);
    }

    private void addText(String text) throws DocumentException {
        Paragraph p = new Paragraph(text, dugFont);
        document.add(p);
    }

    private void addGraph(Node n) throws IOException, DocumentException {
        WritableImage wisnap = n.snapshot(new SnapshotParameters(), null);
        ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
        ImageIO.write(SwingFXUtils.fromFXImage(wisnap, null), "png", byteOutput);
        com.itextpdf.text.Image snap;
        snap = Image.getInstance(byteOutput.toByteArray());

        document.add(snap);
        document.newPage();
    }
}
