package controller;

import com.itextpdf.text.DocumentException;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.jfoenix.controls.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.*;
import org.controlsfx.control.CheckComboBox;
import service.DAO;
import utils.ReportPDFHelper;
import utils.ReportXLSHelper;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
    private JFXCheckBox cb_hours;

    @FXML
    private VBox tabla_info;

    @FXML
    private JFXCheckBox cb_graphs;

    @FXML
    private JFXButton pdfButtonID;

    @FXML
    private JFXButton xlsButtonID;

    private PieChart at_chart, ev_chart;
    private List<String> textList;
    private List<Node> nodeList;
    private List<Record> query;

    ReportController() {
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
            pdfButtonID.setDisable(true);
            xlsButtonID.setDisable(true);
            textList = new ArrayList<>();
            nodeList = new ArrayList<>();
            query = new ArrayList<>();

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
    private void export_pdf(ActionEvent event) throws DocumentException, IOException {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Archivos PDF (*.pdf)", "*.pdf");
        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.setInitialFileName("Reporte - " + assemblyID.getValue().getName());
        File file = fileChooser.showSaveDialog(new Stage());
        if (file != null) {
            new ReportPDFHelper(file, textList, nodeList);
        }
    }

    @FXML
    private void export_xls(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Archivos XLS (*.xls)", "*.xls");
        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.setInitialFileName("Reporte - " + assemblyID.getValue().getName());
        File file = fileChooser.showSaveDialog(new Stage());
        if (file != null) {
            new ReportXLSHelper(file, query);
        }
    }

    @FXML
    private void generate(ActionEvent event) throws SQLException {
        query.clear();
        tabla_info.getChildren().clear();
        textList.clear();
        nodeList.clear();

        QueryBuilder<Record, Integer> qb = DAO.record.queryBuilder();
        Where<Record, Integer> where = whereReportBuilder(qb.where());
        query = where.query();

        tabla_info.setSpacing(10);
        String title = "Asamblea de " + assemblyID.getValue();
        tabla_info.getChildren().add(new Label(title));
        textList.add(title);
        if (query.isEmpty()) {
            String err = "No se ha realizado ningún registro.";
            tabla_info.getChildren().add(new Label(err));
            textList.add(err);
            pdfButtonID.setDisable(true);
            xlsButtonID.setDisable(true);
            return;
        }

        if (cb_gender.isSelected()) {
            showGenderInfo(query);
        }
        if (cb_service.isSelected()) {
            showServiceInfo(query);
        }
        if (cb_areas.isSelected()) {
            showAreaInfo(query);
        }
        if (cb_resource.isSelected()) {
            showResourceInfo(query);
        }
        if (cb_hours.isSelected()) {
            showHoursInfo(query);
        }

        pdfButtonID.setDisable(false);
        xlsButtonID.setDisable(false);
    }

    private Where<Record, Integer> whereReportBuilder(Where<Record, Integer> where) throws SQLException {
        int total = 1;

        LocalDate sd = startDateID.getValue();
        LocalDate ed = endDateID.getValue();

        where.between("date", sd, ed);
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

        for (Service service : checkedService) {
            where.eq("service_id", service.getID());
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

        return where;
    }

    private void showGenderInfo(List<Record> query) {
        int at_h = 0, at_m = 0, ev_h = 0, ev_m = 0;

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
        info += "El total de gente atendida es de " + (at_h + at_m)
                + " personas, de los cuales "
                + (ev_h + ev_m)
                + " han \nsido evacuadas. Se han realizado, en total, "
                + query.size() + " registros.";
        tabla_info.getChildren().add(new Label(info));
        textList.add(info);

        if (cb_graphs.isSelected()) {
            HBox graphs = new HBox();
            ObservableList<PieChart.Data> at_data
                    = FXCollections.observableArrayList(
                            new PieChart.Data("Hombres atendidos", at_h),
                            new PieChart.Data("Mujeres atendidas", at_m));
            at_chart = new PieChart(at_data) {
                @Override
                protected void layoutChartChildren(double top, double left, double contentWidth, double contentHeight) {
                    if (getLabelsVisible()) {
                        getData().forEach(d -> {
                            Optional<Node> opTextNode = at_chart.lookupAll(".chart-pie-label").stream().filter(n -> n instanceof Text && ((Text) n).getText().contains(d.getName())).findAny();
                            if (opTextNode.isPresent()) {
                                ((Text) opTextNode.get()).setText((int) d.getPieValue() + "");
                            }
                        });
                    }
                    super.layoutChartChildren(top, left, contentWidth, contentHeight);
                }
            };

            at_chart.setLabelsVisible(true);
            at_chart.setPrefSize(250, 250);
            at_chart.setLegendVisible(true);
            ObservableList<PieChart.Data> ev_data = FXCollections.observableArrayList(
                    new PieChart.Data("Hombres evacuados", ev_h),
                    new PieChart.Data("Mujeres evacuadas", ev_m));

            ev_chart = new PieChart(ev_data) {
                @Override
                protected void layoutChartChildren(double top, double left, double contentWidth, double contentHeight) {
                    if (getLabelsVisible()) {
                        getData().forEach(d -> {
                            Optional<Node> opTextNode = ev_chart.lookupAll(".chart-pie-label").stream().filter(n -> n instanceof Text && ((Text) n).getText().contains(d.getName())).findAny();
                            if (opTextNode.isPresent()) {
                                ((Text) opTextNode.get()).setText((int) d.getPieValue() + "");
                            }
                        });
                    }
                    super.layoutChartChildren(top, left, contentWidth, contentHeight);
                }
            };

            ev_chart.setTitle("Desglose por género");
            ev_chart.setPrefSize(250, 350);
            ev_chart.setLabelsVisible(true);
            graphs.getChildren().add(at_chart);
            graphs.getChildren().add(ev_chart);
            tabla_info.getChildren().add(graphs);
            nodeList.add(graphs);
        }
    }

    private void showServiceInfo(List<Record> query) throws SQLException {
        List<Service> queryS = DAO.services.queryBuilder().query();
        List<String> lista_services = new ArrayList<>();
        List<String> lista_info = new ArrayList<>();
        XYChart.Series serie = new XYChart.Series();
        final NumberAxis yAxis = new NumberAxis();
        final CategoryAxis xAxis = new CategoryAxis();
        final BarChart<String, Number> bc = new BarChart<>(xAxis, yAxis);
        bc.setCenterShape(true);
        bc.setBarGap(0);

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
                if (service.getName().length() < 8) {
                    serie.getData().add(new XYChart.Data(service.getName(), count));
                } else {
                    String aux = service.getName().substring(0, 8).concat("...");
                    serie.getData().add(new XYChart.Data(aux, count));
                }
                lista_info.add(s);
            }
        }

        StringBuilder info = new StringBuilder("Desglose de los registros filtrados por servicio: \n");
        for (String s : lista_info) {
            info.append("       - ").append(s).append("\n");
        }

        tabla_info.getChildren().add(new Label(info.toString()));
        textList.add(info.toString());

        if (cb_graphs.isSelected()) {
            bc.getData().addAll(serie);
            bc.setTitle("Desglose de los registros por servicio:");
            bc.setPrefSize(600, 400);
            bc.setLegendVisible(false);
            tabla_info.getChildren().add(bc);
            nodeList.add(bc);

        }
    }

    private void showAreaInfo(List<Record> query) throws SQLException {
        List<Area> queryA = DAO.area.queryBuilder().query();
        List<String> lista_areas = new ArrayList<>();
        List<String> lista_info = new ArrayList<>();
        XYChart.Series serie = new XYChart.Series();
        final NumberAxis yAxis = new NumberAxis();
        final CategoryAxis xAxis = new CategoryAxis();
        final BarChart<String, Number> bc = new BarChart<>(xAxis, yAxis);
        bc.setCenterShape(true);
        bc.setBarGap(0);

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
                if (area.getName().length() < 10) {
                    serie.getData().add(new XYChart.Data(area.getName(), count));
                } else {
                    String aux = area.getName().substring(0, 8).concat("...");
                    serie.getData().add(new XYChart.Data(aux, count));
                }
                lista_info.add(s);
            }
        }

        StringBuilder info = new StringBuilder("Desglose de los registros filtrados por area: \n");
        for (String s : lista_info) {
            info.append("       - ").append(s).append("\n");
        }

        tabla_info.getChildren().add(new Label(info.toString()));
        textList.add(info.toString());

        if (cb_graphs.isSelected()) {

            bc.getData().addAll(serie);
            bc.setTitle("Desglose de los registros por área:");
            bc.setPrefSize(600, 400);
            bc.setLegendVisible(false);
            tabla_info.getChildren().add(bc);
            nodeList.add(bc);

        }
    }

    private void showResourceInfo(List<Record> query) throws SQLException {
        List<Resource> queryR = DAO.resource.queryBuilder().query();
        List<String> lista_resource = new ArrayList<>();
        List<String> lista_info = new ArrayList<>();

        XYChart.Series serie = new XYChart.Series();
        final NumberAxis yAxis = new NumberAxis();
        final CategoryAxis xAxis = new CategoryAxis();
        final BarChart<String, Number> bc = new BarChart<>(xAxis, yAxis);
        bc.setCenterShape(true);
        bc.setBarGap(0);

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
                if (resource.getCode().length() < 8) {
                    serie.getData().add(new XYChart.Data(resource.getCode(), count));
                } else {
                    String aux = resource.getCode().substring(0, 8).concat("...");
                    serie.getData().add(new XYChart.Data(aux, count));
                }
                lista_info.add(s);
            }
        }

        StringBuilder info = new StringBuilder("Desglose de los registros filtrados por recurso: \n");
        for (String s : lista_info) {
            info.append("       - ").append(s).append("\n");
        }

        tabla_info.getChildren().add(new Label(info.toString()));
        textList.add(info.toString());

        if (cb_graphs.isSelected()) {
            bc.getData().addAll(serie);
            bc.setTitle("Desglose de los registros por recurso:");
            bc.setPrefSize(600, 400);
            bc.setLegendVisible(false);
            tabla_info.getChildren().add(bc);
            nodeList.add(bc);

        }
    }

    private void showHoursInfo(List<Record> query) {
        long days = 0, hours = 0, minutes = 0;
        for (Record record : query) {
            try {
                LocalTime start = record.getStartTime();
                LocalTime finish = record.getEndTime();
                minutes = minutes + ChronoUnit.MINUTES.between(start, finish);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        while (minutes > 60) {
            minutes = minutes - 60;
            hours = hours + 1;
        }

        String info = "Se han trabajado en total " + hours + " horas y " + minutes + " minutos.";

        tabla_info.getChildren().add(new Label(info));
        textList.add(info);
    }

}
