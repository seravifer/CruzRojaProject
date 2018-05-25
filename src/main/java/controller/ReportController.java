package controller;

import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
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
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import org.controlsfx.control.CheckComboBox;

public class ReportController {

    @FXML
    private JFXButton generate_button;
    @FXML
    private JFXDatePicker init_dateID;
    @FXML
    private JFXDatePicker finish_dateID;
    @FXML
    private CheckComboBox<Assembly> assemblyID;
    @FXML
    private CheckComboBox<Resource> resourceID;
    @FXML
    private CheckComboBox<Service> serviceID;
    @FXML
    private CheckComboBox<Area> areaID;
    @FXML
    private CheckComboBox<Hospital> hospitalID;
    @FXML
    private CheckComboBox<Applicant> applicantID;
    @FXML
    private JFXProgressBar progressBar;
    @FXML
    private AnchorPane report_ID;
    @FXML
    private Label text_ID;
    @FXML
    private Label title_ID;

    public ReportController() {
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

    private void init() {
        try {
            List<Assembly> assemblies = DAO.assembly.queryBuilder().query();
            List<Applicant> applicants = DAO.applicant.queryBuilder().query();
            List<Resource> resources = DAO.resource.queryBuilder().query();
            List<Area> areas = DAO.area.queryBuilder().query();
            List<Service> services = DAO.services.queryBuilder().query();
            List<Hospital> hospitals = DAO.hospital.queryBuilder().query();

            assemblyID.getItems().addAll(assemblies);
            applicantID.getItems().addAll(applicants);
            resourceID.getItems().addAll(resources);
            areaID.getItems().addAll(areas);
            serviceID.getItems().addAll(services);
            hospitalID.getItems().addAll(hospitals);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        init_dateID.setValue(LocalDate.of(2017, 12, 1));
        finish_dateID.setValue(LocalDate.now());
    }

    @FXML
    private void generate_report(ActionEvent event) {
        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null) {
            System.out.println(job.jobStatusProperty().asString());

            boolean printed = job.printPage(report_ID);
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
    private void show_report(ActionEvent event) throws SQLException {
        
        LocalDate sd = init_dateID.getValue();
        LocalDate fd = finish_dateID.getValue();
        
        QueryBuilder<Record, Integer> qb = DAO.record.queryBuilder();
        Where<Record, Integer> where = qb.where();
        where.between("date", sd, fd);
        int total = 1;
        /*where
                .eq("assembly_id", 1)
                .or()
                .eq("assembly_id", 2);
        where.and(2);*/

        ObservableList<Assembly> checkedAssembly = assemblyID.getCheckModel().getCheckedItems();
        ObservableList<Applicant> checkedApplicant = applicantID.getCheckModel().getCheckedItems();
        ObservableList<Resource> checkedResource = resourceID.getCheckModel().getCheckedItems();
        ObservableList<Area> checkedArea = areaID.getCheckModel().getCheckedItems();
        ObservableList<Service> checkedService = serviceID.getCheckModel().getCheckedItems();
        ObservableList<Hospital> checkedHospital = hospitalID.getCheckModel().getCheckedItems();


        for (int i = 0; i < checkedAssembly.size(); i++) {
            if (i == checkedAssembly.size() - 1) {
                where.eq("assembly_id", checkedAssembly.get(i).getID_assembly());
                total++;
            } else {
                where.eq("assembly_id", checkedAssembly.get(i).getID_assembly()).or();
            }
        }

        where.and(total);

        
        /*for (Applicant a : checkedApplicant) {
            where.or().eq("applicant_id", a.getID_applicant());
        }
        
        for (Resource r : checkedResource) {
            where.or().eq("resource_id", r.getID_resource());
        }
        
        for (Area a : checkedArea) {
            where.or().eq("area_id", a.getID_area());
        }
        
        for (Service s : checkedService) {
            where.or().eq("service_id", s.getID_service());
        }
        
        for (Hospital h : checkedHospital) {
            where.or().eq("hospital_id", h.getID());
        }*/
        
        List<Record> query = where.query();
        System.out.println(query.size() + "");
    }
}
