/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.print.PrinterJob;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.*;
import service.DAO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ReportFormController {

    @FXML
    private JFXCheckBox inter_by_gender;
    @FXML
    private JFXCheckBox service_hours;
    @FXML
    private JFXCheckBox cb_resource;
    @FXML
    private JFXCheckBox cb_area;
    @FXML
    private JFXCheckBox cb_applicant;
    @FXML
    private JFXCheckBox total_transfer;
    @FXML
    private JFXComboBox<Resource> resourceID;
    @FXML
    private JFXComboBox<Area> areaID;
    @FXML
    private JFXComboBox<Service> service_areaID;
    @FXML
    private JFXComboBox<Applicant> applicantID;
    @FXML
    private JFXDatePicker init_dateID;
    @FXML
    private JFXDatePicker finish_dateID;
    @FXML
    private JFXComboBox<Assembly> assemblyID;
    @FXML
    private Label title;
    @FXML
    private Label text;
    @FXML
    private JFXButton generate_button;
    @FXML
    private AnchorPane informe;
    @FXML
    private JFXProgressBar progressBar;

    private void loadView() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/view/reportForm.fxml"));
            fxmlLoader.setController(this);

            Scene scene = new Scene(fxmlLoader.load(), 1200, 720);
            Stage stage = new Stage();

            stage.setTitle("Generador de informes de Cruz Roja");
            stage.setScene(scene);
            stage.show();
            init();
        } catch (IOException e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create new Window.", e);
        }

    }

    public ReportFormController() {
        loadView();
    }

    private void init() {
        try {
            resourceID.setDisable(true);
            areaID.setDisable(true);
            service_areaID.setDisable(true);
            applicantID.setDisable(true);

            List<Assembly> assemblies = DAO.assemblyDao.queryBuilder().query();
            List<Applicant> applicants = DAO.applicantDao.queryBuilder().query();
            List<Resource> resources = DAO.resourceDao.queryBuilder().query();
            List<Area> areas = DAO.areaDao.queryBuilder().query();
            List<Service> services = DAO.servicesDao.queryBuilder().query();

            assemblyID.getItems().addAll(assemblies);
            resourceID.getItems().addAll(resources);
            areaID.getItems().addAll(areas);
            applicantID.getItems().addAll(applicants);
            service_areaID.getItems().addAll(services);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void show_report(ActionEvent event) throws SQLException {
//        Assembly a = assemblyID.getValue();
//        LocalDate sd = init_dateID.getValue();
//        LocalDate fd = finish_dateID.getValue();
//        QueryBuilder<Record, Integer> qb = DAO.recordDao.queryBuilder();
//        Where<Record, Integer> where = qb.where().between("date", sd, fd);
//        where.and().eq("assembly_id", a.getID_assembly());
//        List<Record> query = where.query();
//        StyleBuilder boldStyle = stl.style().bold();
//        StyleBuilder boldCenteredStyle = stl.style(boldStyle)
//                .setHorizontalAlignment(HorizontalAlignment.CENTER);
//        StyleBuilder columnTitleStyle = stl.style(boldCenteredStyle)
//                .setBorder(stl.pen1Point())
//                .setBackgroundColor(Color.WHITE);
//        StyleBuilder titleStyle = stl.style(boldCenteredStyle)
//                             .setVerticalAlignment(VerticalAlignment.MIDDLE)
//                             .setFontSize(15);
//              
//        
//        try {
//            TextColumnBuilder<String> resourceColumn = col.column("Resource", "resource", type.stringType()).setStyle(boldStyle);
//            report()
//                    .setColumnTitleStyle(columnTitleStyle)
//                    .highlightDetailEvenRows()
//                    .columns(
//                            col.column("ID_Record", "id_record", type.stringType()),
//                            col.column("Date", "date", type.stringType()),
//                            resourceColumn,
//                            col.column("Assistance_h", "assistance_h", type.stringType()),
//                            col.column("Assistance_m", "assistance_m", type.stringType()),
//                            col.column("Evacuated_h", "evacuated_h", type.stringType()),
//                            col.column("Evacuated_m", "evacuated_m", type.stringType()))
//                    .title(//shows report title
//                            cmp.horizontalList()
//                                    .add(
//                                            cmp.image(getClass().getResourceAsStream("/img/logo.png")).setFixedDimension(80, 80),
//                                            cmp.text("    Informe de la Cruz Roja").setStyle(titleStyle).setHorizontalAlignment(HorizontalAlignment.LEFT),
//                                            cmp.text("Asamblea de " + a.getName_assembly()).setStyle(titleStyle).setHorizontalAlignment(HorizontalAlignment.RIGHT))
//                                    .newRow()
//                                    .add(cmp.filler().setStyle(stl.style().setTopBorder(stl.pen2Point())).setFixedHeight(10)))
//                    .pageFooter(cmp.pageXofY().setStyle(boldCenteredStyle))
//                    .setDataSource(createDataSource(query))
//                    .groupBy(resourceColumn)
//                    .show();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        
//    }
//
//    private JRDataSource createDataSource(List<Record> lista) {
//        DRDataSource dataSource = new DRDataSource("id_record", "date", "resource", "assistance_h",
//                "assistance_m", "evacuated_h", "evacuated_m");
//        for (Record re : lista) {
//            dataSource.add(re.getID_record() + "", re.getDate(),
//                    re.getResource().getName_resource(), re.getAssistance_h() + "",
//                    re.getAssistance_m() + "", re.getEvacuated_h() + "", re.getEvacuated_m() + "");
//        }
//        return dataSource;
}

    @FXML
    private void generate_report(ActionEvent event) {
        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null) {
            System.out.println(job.jobStatusProperty().asString());

            boolean printed = job.printPage(informe);
            if (printed) {
                job.endJob();
            } else {
                System.out.println("Printing failed.");
            }
        } else {
            System.out.println("Could not create a printer job.");
        }
    }

    @FXML
    private void press_resource(ActionEvent event) {
        System.out.println("Hula");
        resourceID.setDisable(!cb_resource.isSelected());
    }

    @FXML
    private void press_area(ActionEvent event) {
        areaID.setDisable(!cb_area.isSelected());
        service_areaID.setDisable(!cb_area.isSelected());
    }

    @FXML
    private void press_applicant(ActionEvent event) {
        applicantID.setDisable(!cb_applicant.isSelected());
    }
}
