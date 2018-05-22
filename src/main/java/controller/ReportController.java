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

public class ReportController {

    @FXML
    private JFXButton generate_button;
    @FXML
    private JFXComboBox<Assembly> assemblyID;
    @FXML
    private JFXDatePicker init_dateID;
    @FXML
    private JFXDatePicker finish_dateID;
    @FXML
    private JFXCheckBox total_recordsID;
    @FXML
    private JFXCheckBox total_resourcesID;
    @FXML
    private JFXCheckBox total_servicesID;
    @FXML
    private JFXCheckBox total_areaID;
    @FXML
    private JFXCheckBox total_hoursID;
    @FXML
    private JFXProgressBar progressBar;
    @FXML
    private AnchorPane reportID;
    @FXML
    private Label title_ID;
    @FXML
    private Label text_ID;
    
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

            assemblyID.getItems().addAll(assemblies);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void generate_report(ActionEvent event) {
        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null) {
            System.out.println(job.jobStatusProperty().asString());

            boolean printed = job.printPage(reportID);
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
        Assembly a = assemblyID.getValue();
        LocalDate sd = init_dateID.getValue();
        LocalDate fd = finish_dateID.getValue();
        QueryBuilder<Record, Integer> qb = DAO.record.queryBuilder();
        Where<Record, Integer> where = qb.where().between("date", sd, fd);
        where.and().eq("assembly_id", a.getID_assembly());
        List<Record> query = where.query();
        text_ID.setText("");
        title_ID.setText("Informe de la Cruz Roja - Asamblea de " + a.getName_assembly());
        if (total_recordsID.isSelected()) {
            // PieChart list_by_gender = showByGender(query);
            // reportID.getChildren().add(list_by_gender);
            String s = showByGenderS(query);
            text_ID.setText(text_ID.getText() + "/n" + s);
            
        }
        if (total_resourcesID.isSelected()) {
            // BarChart list_by_resources = showByResources(query);
            // reportID.getChildren().add(list_by_resources);
            String s = showByResourcesS(query);
            text_ID.setText(text_ID.getText() + "/n" + s);
        }
        if (total_servicesID.isSelected()) {
//            BarChart list_by_services = showByServices(query);
        }
    }

    private String showByResourcesS(List<Record> list) {
        String s  = "";
        int u_ambu = 0, u_tango = 0, u_embar = 0, u_quad = 0, u_moto = 0;
        for (Record r : list) {
            switch (r.getResource().getName_resource()) {
                case "Ambulancia":
                    u_ambu = u_ambu + 1;
                    break;
                case "Tango":
                    u_tango = u_tango + 1;
                    break;
                case "Embarcación":
                    u_embar = u_embar + 1;
                    break;
                case "Quad":
                    u_quad = u_quad + 1;
                    break;
                case "Moto acuática":
                    u_moto = u_moto + 1;
                    break;
            }
        }
        s = "Uso de recursos y desglose: \n";
        s = s + "- Ambulancias: " + u_ambu + "\n";
        s = s + "- Tangos: " + u_tango + "\n";
        s = s + "- Embarcaciones: " + u_embar + "\n";
        s = s + "- Quads: " + u_quad + "\n";
        s = s + "- Motos acuáticas: " + u_moto + "\n";
        return s;
    }
    
    private BarChart showByResources(List<Record> list) {
        int u_ambu = 0, u_tango = 0, u_embar = 0, u_quad = 0, u_moto = 0;
        for (Record r : list) {
            switch (r.getResource().getName_resource()) {
                case "Ambulancia":
                    u_ambu = u_ambu + 1;
                    break;
                case "Tango":
                    u_tango = u_tango + 1;
                    break;
                case "Embarcación":
                    u_embar = u_embar + 1;
                    break;
                case "Quad":
                    u_quad = u_quad + 1;
                    break;
                case "Moto acuática":
                    u_moto = u_moto + 1;
                    break;
            }
        }

        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String, Number> bc
                = new BarChart<String, Number>(xAxis, yAxis);
        bc.setTitle("Uso de recursos y desglose");
        xAxis.setLabel("Recursos");
        yAxis.setLabel("Utilizados");

        XYChart.Series serie = new XYChart.Series();
        serie.getData().add(new XYChart.Data("Ambulancias", u_ambu));
        serie.getData().add(new XYChart.Data("Tangos", u_tango));
        serie.getData().add(new XYChart.Data("Embarcaciones", u_embar));
        serie.getData().add(new XYChart.Data("Quads", u_quad));
        serie.getData().add(new XYChart.Data("Motos acuáticas", u_moto));

        bc.getData().add(serie);
        return bc;

    }

    private PieChart showByGender(List<Record> list) {
        int as_h = 0, as_m = 0, ev_h = 0, ev_m = 0;
        for (Record r : list) {
            as_h = as_h + r.getAssistance_h();
            as_m = as_m + r.getAssistance_m();
            ev_h = ev_h + r.getEvacuated_h();
            ev_m = ev_m + r.getEvacuated_m();
        }
        int t = as_h + as_m + ev_h + ev_m;
        ObservableList<PieChart.Data> pieChartData
                = FXCollections.observableArrayList(
                        new PieChart.Data("Hombres asistidos: " + as_h, as_h),
                        new PieChart.Data("Mujeres asistidas: " + as_m, as_m),
                        new PieChart.Data("Hombres evacuados: " + ev_h, ev_h),
                        new PieChart.Data("Mujeres evacuadas: " + ev_m, ev_m));
        final PieChart chart = new PieChart(pieChartData);
        chart.setTitle("Registros totales (" + t + ") y desglose por género");
        return chart;
    }

    public String showByGenderS(List<Record> list) {
        String s = "";
        int as_h = 0, as_m = 0, ev_h = 0, ev_m = 0;
        for (Record r : list) {
            as_h = as_h + r.getAssistance_h();
            as_m = as_m + r.getAssistance_m();
            ev_h = ev_h + r.getEvacuated_h();
            ev_m = ev_m + r.getEvacuated_m();
        }
        int t = as_h + as_m + ev_h + ev_m;
        s = "Registros totales (" + t + ") y desglose por género: \n";
        s = s + "- Hombres asistidos: " + as_h + "\n";
        s = s + "- Mujeres asistidas: " + as_h + "\n";
        s = s + "- Hombres evacuados: " + as_h + "\n";
        s = s + "- Mujeres evacuadas: " + as_h + "\n";
        return s;
    }
}
