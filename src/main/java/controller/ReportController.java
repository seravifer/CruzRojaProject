package controller;

import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.print.PrinterJob;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.*;
import service.DAO;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import org.controlsfx.control.CheckComboBox;

public class ReportController {

    @FXML
    private JFXProgressBar loadingID;

    @FXML
    private JFXDatePicker startDateID;

    @FXML
    private JFXDatePicker endDateID;

    @FXML
    private CheckComboBox<Assembly> assemblyID;

    @FXML
    private CheckComboBox<Area> areaID;

    @FXML
    private CheckComboBox<Service> serviceID;

    @FXML
    private CheckComboBox<Resource> resourceID;

    @FXML
    private CheckComboBox<Applicant> applicantID;

    @FXML
    private TableView<Record> recordTableID;

    @FXML
    private TableColumn<Record, Integer> codeColumID;

    @FXML
    private TableColumn<Record, String> dateColumID;

    @FXML
    private TableColumn<Record, Assembly> assemblyColumID;

    @FXML
    private AnchorPane pageID;

    @FXML
    private JFXCheckBox cb_gender;

    @FXML
    private JFXCheckBox cb_areas;

    @FXML
    private JFXCheckBox cb_hours;

    public ReportController() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/report.fxml"));
            fxmlLoader.setController(this);

            Scene scene = new Scene(fxmlLoader.load(), 1200, 720);
            scene.getStylesheets().setAll(getClass().getResource("/css/style.css").toExternalForm());
            Stage stage = new Stage();

            stage.setTitle("Generador de informes");
            stage.setScene(scene);
            stage.show();

            init();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void init() {
        try {
            List<Assembly> assemblies = DAO.assembly.queryBuilder().query();
            List<Applicant> applicants = DAO.applicant.queryBuilder().query();
            List<Resource> resources = DAO.resource.queryBuilder().query();
            List<Area> areas = DAO.area.queryBuilder().query();
            List<Service> services = DAO.services.queryBuilder().query();

            assemblyID.getItems().addAll(assemblies);
            applicantID.getItems().addAll(applicants);
            resourceID.getItems().addAll(resources);
            areaID.getItems().addAll(areas);
            serviceID.getItems().addAll(services);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        codeColumID.setCellValueFactory(new PropertyValueFactory<>("code"));
        dateColumID.setCellValueFactory(new PropertyValueFactory<>("date"));
        assemblyColumID.setCellValueFactory(new PropertyValueFactory<>("assembly"));

        assemblyColumID.setCellFactory(cell -> new TableCell<Record, Assembly>() {
            @Override
            public void updateItem(Assembly item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                } else {
                    setText(item.getName() + "");
                }
            }
        });

        // DEBUG
        startDateID.setValue(LocalDate.of(2017, 12, 1));
        endDateID.setValue(LocalDate.now());
    }

    @FXML
    private void export(ActionEvent event) {
        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null) {
            System.out.println(job.jobStatusProperty().asString());

            boolean printed = job.printPage(pageID);
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
    private void generate(ActionEvent event) throws SQLException {
        LocalDate sd = startDateID.getValue();
        LocalDate ed = endDateID.getValue();

        QueryBuilder<Record, Integer> qb = DAO.record.queryBuilder();
        Where<Record, Integer> where = qb.where();
        where.between("date", sd, ed);
        int total = 1;

        ObservableList<Assembly> checkedAssembly = assemblyID.getCheckModel().getCheckedItems();
        ObservableList<Area> checkedArea = areaID.getCheckModel().getCheckedItems();
        ObservableList<Service> checkedService = serviceID.getCheckModel().getCheckedItems();
        ObservableList<Applicant> checkedApplicant = applicantID.getCheckModel().getCheckedItems();
        ObservableList<Resource> checkedResource = resourceID.getCheckModel().getCheckedItems();

        for (Assembly assembly : checkedAssembly) {
            where.eq("assembly_id", assembly.getID());
        }

        if (!checkedAssembly.isEmpty()) {
            where.or(checkedAssembly.size());
            total++;
        }

        for (Area area : checkedArea) {
            where.eq("area_id", area.getID());
        }

        if (!checkedArea.isEmpty()) {
            where.or(checkedArea.size());
            total++;
        }

        if (!checkedService.isEmpty()) {
            where.or(checkedService.size());
            total++;
        }

        for (Applicant applicant : checkedApplicant) {
            where.eq("applicant_id", applicant.getID());
        }

        if (!checkedApplicant.isEmpty()) {
            where.or(checkedApplicant.size());
            total++;
        }

        for (Resource resource : checkedResource) {
            where.eq("resource_id", resource.getID());
        }

        if (!checkedResource.isEmpty()) {
            where.or(checkedResource.size());
            total++;
        }

        where.and(total);

        List<Record> query = where.query();

        // Desglose por genero  
        if (cb_gender.isSelected()) {
            int at_h = 0;
            int at_m = 0;
            int ev_h = 0;
            int ev_m = 0;
            for (Record record : query) {
                at_h += record.getAssistance_h();
                at_m += record.getAssistance_m();
                ev_h += record.getEvacuated_h();
                ev_m += record.getEvacuated_m();
            }
            int total_r = query.size();
            String s = (at_h + " " + at_m + " " + ev_h + " " + ev_m);
            System.out.println(s);
        }

        // Horas de servicio totales
        if (cb_hours.isSelected()) {
            int hours = 0;
            int minutes = 0;
            for (Record record : query) {
                try {
                    LocalTime start = LocalTime.parse(record.getStartTime());
                    LocalTime finish = LocalTime.parse(record.getEndTime());
                    hours = hours + Math.abs(finish.getHour() - start.getHour());
                    minutes = minutes + Math.abs(finish.getMinute() - start.getMinute());
                } catch (Exception e) {
                    // Podrán haber Record que no hayan acabado (por error o porque aún están operativos)
                }
            }
        }

        // Desglose por area
        if (cb_areas.isSelected()) {
            List<Area> queryA = DAO.area.queryBuilder().query();
            List<String> lista_areas = new ArrayList<String>();
            List<String> lista_info = new ArrayList<String>();
            for (Record record : query) {
                lista_areas.add(record.getArea().getName());
            }
            for (Area area : queryA) {
                int count = 0;
                for (String s : lista_areas) {
                    if (area.getName().equals(s)) {
                        count++;
                    }
                }
                String s = area.getName() + ": " + count;
                lista_info.add(s);
            }
            for (String s : lista_info) {
                System.out.println(s);
            }
        }

        recordTableID.getItems().clear();
        recordTableID.getItems().addAll(query);
    }

}
