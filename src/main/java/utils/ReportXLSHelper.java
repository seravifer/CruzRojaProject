package utils;

import model.Record;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ReportXLSHelper {

    public ReportXLSHelper(File file, List<Record> query) throws IOException {
        HSSFWorkbook workBook = new HSSFWorkbook();
        HSSFSheet sheet = workBook.createSheet();
        HSSFRow headingRow = sheet.createRow(0);
        headingRow.createCell((short) 0).setCellValue("ID_RECORD");
        headingRow.createCell((short) 1).setCellValue("FECHA");
        headingRow.createCell((short) 2).setCellValue("CÓDIGO");
        headingRow.createCell((short) 3).setCellValue("RECURSO");
        headingRow.createCell((short) 4).setCellValue("ASAMBLEA");
        headingRow.createCell((short) 5).setCellValue("HORA DE INICIO");
        headingRow.createCell((short) 6).setCellValue("HORA DE FIN");
        headingRow.createCell((short) 7).setCellValue("ÁREA");
        headingRow.createCell((short) 8).setCellValue("SERVICIO");
        headingRow.createCell((short) 9).setCellValue("DIRECCIÓN");
        headingRow.createCell((short) 10).setCellValue("ASIS - H");
        headingRow.createCell((short) 11).setCellValue("ASIS - M");
        headingRow.createCell((short) 12).setCellValue("EVAC - H");
        headingRow.createCell((short) 13).setCellValue("EVAC - M");
        headingRow.createCell((short) 14).setCellValue("REGISTRO");
        headingRow.createCell((short) 15).setCellValue("OPERATIVO");
        headingRow.createCell((short) 16).setCellValue("NOTAS");
        short rowNo = 1;
        for (Record r : query) {
            HSSFRow row = sheet.createRow(rowNo);
            Object[] info = r.getInfo();
            for (int i = 0; i < 17; i++) {
                row.createCell((short) i).setCellValue((String) info[i]);
            }
            rowNo++;
        }
        try {
            FileOutputStream fos = new FileOutputStream(file);
            workBook.write(fos);
            fos.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Invalid directory or file not found");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error occurred while writting excel file to directory");
        }
    }
}
