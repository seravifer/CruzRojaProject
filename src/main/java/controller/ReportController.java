package controller;

import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.print.PrinterJob;
import javafx.scene.Scene;
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
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.controlsfx.control.CheckComboBox;

public class ReportController {

    @FXML
    private JFXProgressBar loadingID;

    @FXML
    private JFXDatePicker startDateID;

    @FXML
    private JFXDatePicker endDateID;

    @FXML
    private JFXComboBox<Assembly> assemblyID;

    @FXML
    private CheckComboBox<Area> areaID;

    @FXML
    private CheckComboBox<Service> serviceID;

    @FXML
    private CheckComboBox<Resource> resourceID;

    @FXML
    private AnchorPane pageID;

    @FXML
    private JFXCheckBox cb_gender;

    @FXML
    private JFXCheckBox cb_service;

    @FXML
    private JFXCheckBox cb_areas;

    @FXML
    private JFXCheckBox cb_resource;

    @FXML
    private JFXCheckBox cb_applicant;

    @FXML
    private JFXCheckBox cb_hours;

    @FXML
    private VBox tabla_info;

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
            List<Resource> resources = DAO.resource.queryBuilder().query();
            List<Area> areas = DAO.area.queryBuilder().query();
            List<Service> services = DAO.services.queryBuilder().query();

            assemblyID.getItems().addAll(assemblies);
            resourceID.getItems().addAll(resources);
            areaID.getItems().addAll(areas);
            serviceID.getItems().addAll(services);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // DEBUG
        startDateID.setValue(LocalDate.of(2017, 12, 1));
        endDateID.setValue(LocalDate.now());
    }

    @FXML
    private void export(ActionEvent event) {
        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null) {
            System.out.println(job.jobStatusProperty().asString());

            boolean printed = job.printPage(tabla_info);
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
        tabla_info.getChildren().clear();
        LocalDate sd = startDateID.getValue();
        LocalDate ed = endDateID.getValue();

        QueryBuilder<Record, Integer> qb = DAO.record.queryBuilder();
        Where<Record, Integer> where = qb.where();
        where.between("date", sd, ed);

        int total = 1;

        ObservableList<Area> checkedArea = areaID.getCheckModel().getCheckedItems();
        ObservableList<Service> checkedService = serviceID.getCheckModel().getCheckedItems();
        ObservableList<Resource> checkedResource = resourceID.getCheckModel().getCheckedItems();


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

        for (Resource resource : checkedResource) {
            where.eq("resource_id", resource.getID());
        }

        if (!checkedResource.isEmpty()) {
            where.or(checkedResource.size());
            total++;
        }

        where.and(total).and().eq("assembly_id", assemblyID.getValue().getID());
        
        List<Record> query = where.query();
        
        tabla_info.setSpacing(10);
        tabla_info.getChildren().add(new Label("Informe de la Cruz Roja - Asamblea de " + assemblyID.getValue()));

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
            
            String info = "Desglose de los servicios por genero: \n";
            info += "       - Hombres atendidos: " + at_h + "\n";
            info += "       - Mujeres atendidas: " + at_m + "\n";
            info += "       - Hombres evacuados: " + ev_h + "\n";
            info += "       - Mujeres evacuadas: " + ev_m + "\n";
            info += "El total de gente tratada es de " + (at_h + at_m) + ", de los cuales " 
                    + (ev_h + ev_m) + " han sido evacuados. Se han realizado, \n en total, " + query.size() + " registros.";
            tabla_info.getChildren().add(new Label(info));
        }

        // Desglose por servicio
        if (cb_service.isSelected()) {
            List<Service> queryS = DAO.services.queryBuilder().query();
            List<String> lista_services = new ArrayList<String>();
            List<String> lista_info = new ArrayList<String>();
            for (Record record : query) {
                lista_services.add(record.getService().getName());
            }
            for (Service service : queryS) {
                int count = 0;
                for (String s : lista_services) {
                    if (service.getName().equals(s)) {
                        count++;
                    }
                }
                if (count != 0) {
                    String s = service.getName() + ": " + count;
                    lista_info.add(s);
                }
            }
            String info = "Desglose de los registros filtrados por servicio: \n";
            for (String s : lista_info) {
                info += "       - " + s + "\n";
            }
            tabla_info.getChildren().add(new Label(info));
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
                if (count != 0) {
                    String s = area.getName() + ": " + count;
                    lista_info.add(s);
                }
            }
            String info = "Desglose de los registros filtrados por area: \n";
            for (String s : lista_info) {
                info += "       - " + s + "\n";
            }
            tabla_info.getChildren().add(new Label(info));
        }

        // Desglose por recurso
        if (cb_resource.isSelected()) {
            List<Resource> queryR = DAO.resource.queryBuilder().query();
            List<String> lista_resource = new ArrayList<String>();
            List<String> lista_info = new ArrayList<String>();
            for (Record record : query) {
                if (record.getResource() != null) {
                    lista_resource.add(record.getResource().getCode());
                }
            }
            for (Resource resource : queryR) {
                int count = 0;
                for (String s : lista_resource) {
                    if (resource.getCode().equals(s)) {
                        count++;
                    }
                }
                if (count != 0) {
                    String s = resource.getCode() + ": " + count;
                    lista_info.add(s);
                }
            }
            String info = "Desglose de los registros filtrados por recurso: \n";
            for (String s : lista_info) {
                info += "       - " + s + "\n";
            }
            tabla_info.getChildren().add(new Label(info));
        }

        // Horas de servicio totales
        if (cb_hours.isSelected()) {
            int days = 0;
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
            while (minutes > 60) {
                minutes = minutes - 60;
                hours = hours + 1;
            }
            while (hours > 24) {
                hours = hours - 24;
                days = days + 1;
            }
            String info = "Se han trabajado en total " + days + " dias, " + hours + " horas y "
                    + minutes + " minutos.";
            tabla_info.getChildren().add(new Label(info));
        }
    }
}
