package utils;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javax.imageio.ImageIO;
import model.Record;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class ReportHelper {

    Document document;
    private static final com.itextpdf.text.Font catFont = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.COURIER, 18,
            com.itextpdf.text.Font.BOLD);
    private static final com.itextpdf.text.Font dugFont = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.COURIER, 12,
            com.itextpdf.text.Font.NORMAL);

    public ReportHelper(String path, List<String> text, List<Node> graphs, List<Record> query)
            throws FileNotFoundException, DocumentException, IOException {
        document = new Document();
        String ruta = new File(".").getCanonicalPath().concat("informe.pdf");
        PdfWriter instance = PdfWriter.getInstance(document, new FileOutputStream(ruta));
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
            } catch (Exception e) {

            }
        }
        document.close();

        // HSSFWorkbook
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Información");
        ArrayList<Object[]> data = new ArrayList<Object[]>();
        data.add(new String[]{"Identificador", "Fecha", "Código", "Recurso", "Asamblea", "Hora inicio",
            "Hora fin", "Área", "Servicio", "Dirección", "Asistidos - H", "Asistidos - M", "Evacuados - H",
            "Evacuados - M", "Registro", "Observaciones", "Operativo"});
        for (Record r : query) {
            data.add(new Object[]{r.getID_record(), r.getDate().toString(), r.getCode(), r.getResource().getCode(),
                r.getAssembly().getName(), r.getStartTime().toString(), r.getEndTime().toString(), r.getArea().getName(),
                r.getService().getName(), r.getAddress(), r.getAssistance_h(), r.getAssistance_m(), r.getEvacuated_h(),
                r.getEvacuated_m(), r.getRegistry(), r.getNotes(), r.getOperative().toString()});
        }
        int rownum = 0;
        for (Object[] data_array : data) {
            Row row = sheet.createRow(rownum++);
            int cellnum = 0;
            for (Object obj : data_array) {
                Cell cell = row.createCell(cellnum++);
                if (obj instanceof String) {
                    cell.setCellValue((String) obj);
                } else if (obj instanceof Double) {
                    cell.setCellValue((Double) obj);
                } else if (obj instanceof Integer) {
                    cell.setCellValue((Integer) obj);
                } else {
                    cell.setCellValue((String) "-");
                }
            }
        }
        
        try {
            FileOutputStream out = new FileOutputStream(new File("informe_excel.xlsx"));
            workbook.write(out);
            out.close();
            System.out.println("CountriesDetails.xlsx has been created successfully");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            workbook.close();
        }
    }

    private void addTitle(String text) throws DocumentException {
        Paragraph p = new Paragraph(text, catFont);
        document.add(p);
    }

    private void addText(String text) throws DocumentException {
        Paragraph p = new Paragraph(text, dugFont);
        document.add(p);
    }

    private void addGraph(Node n) throws IOException, BadElementException, DocumentException {
        WritableImage wisnap = n.snapshot(new SnapshotParameters(), null);
        ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
        ImageIO.write(SwingFXUtils.fromFXImage(wisnap, null), "png", byteOutput);
        com.itextpdf.text.Image snap;
        snap = com.itextpdf.text.Image.getInstance(byteOutput.toByteArray());
        document.add(snap);
        document.newPage();
    }
}
