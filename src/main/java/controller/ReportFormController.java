/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXProgressBar;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.print.PrinterJob;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;
import model.*;
import service.DAO;
import service.DataBase;

/**
 * FXML Controller class
 *
 * @author Elio
 */
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
        
        try {
            // Provisional, hacer con queryBuilder
            List<Record> records = DAO.recordDao.queryBuilder().query();
            ArrayList<Record> filtro = new ArrayList<Record>();
            LocalDate sd = init_dateID.getValue();
            LocalDate fd = finish_dateID.getValue();
            Assembly a = assemblyID.getValue();
            // Opcional
            Resource r = null;
            if (cb_resource.isSelected()) {
                r = resourceID.getValue();
            }
            Area ar = null;
            Service s = null;
            if (cb_area.isSelected()) {
                ar = areaID.getValue();
                s = service_areaID.getValue();
            }
            Applicant ap = null;
            if (cb_applicant.isSelected()) {
                ap = applicantID.getValue();
            }
            for (Record re : records) {
                LocalDate rdate = LocalDate.parse(re.getDate());
                if (sd.isBefore(rdate) && fd.isAfter(rdate) && a.equals(re.getAssembly())) {
                    filtro.add(re);
                }
            }
            title.setText("Informe - Asamblea de" + a.getName_assembly());
            text.setText("Se han realizado " + filtro.size() + " registros de intervenciones en el lapso"
                    + "\n de tiempo entre " + sd.toString() + " y " + fd.toString());
        } catch (Exception e) {
            String al = "Por favor, revisa los siguientes campos: \n";
            if (init_dateID.getValue() == null) {
                al = al + "- Fecha de inicio \n";
            }
            if (finish_dateID.getValue() == null) {
                al = al + "- Fecha de fin \n";
            }
            if (assemblyID.getValue() == null) {
                al = al + "- Asamblea \n";
            }
            if (cb_resource.isSelected() && resourceID.getValue() == null) {
                al = al + "- Recurso/Recursos \n";
            }
            if (cb_area.isSelected() && (areaID.getValue() == null
                    || service_areaID.getValue() == null)) {
                al = al + "- Área y/o servicio del área \n";
            }
            if (cb_applicant.isSelected() && applicantID.getValue() == null) {
                al = al + "- Solicitante \n";
            }
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No has introducido correctamente algún dato");
            alert.setContentText(al);
            alert.showAndWait();
        }
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
