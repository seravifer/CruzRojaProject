package controller;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.jfoenix.controls.*;

import org.controlsfx.control.CheckComboBox;

import model.*;
import service.DAO;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javax.imageio.ImageIO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.print.PrinterJob;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import utils.HeaderFooter;
import utils.ReportHelper;

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

    private static Font catFont = new Font(Font.FontFamily.COURIER, 18,
            Font.BOLD);
    private static Font dogFont = new Font(Font.FontFamily.COURIER, 12,
            Font.NORMAL);
    PieChart at_chart;
    PieChart ev_chart;
    List<String> textList;
    List<Node> nodeList;
    List<Record> query;
    
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
            textList = new ArrayList<String>();
            nodeList = new ArrayList<Node>();
            query = new ArrayList<Record>();
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
    private void export_pdf(ActionEvent event) throws FileNotFoundException, DocumentException, IOException {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(new Stage());
        if (file != null) {
            ReportHelper reportHelper = new ReportHelper(file, textList, nodeList);
        } else {
            
        }
    }
    
    @FXML
    private void export_xls(ActionEvent event) throws FileNotFoundException, DocumentException, IOException {
        System.out.println("-");
    }

    @FXML
    private void generate(ActionEvent event) throws SQLException, FileNotFoundException, DocumentException, IOException {

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
    }

    private Where<Record, Integer> whereReportBuilder(Where<Record, Integer> where) throws SQLException {

        LocalDate sd = startDateID.getValue();
        LocalDate ed = endDateID.getValue();
        int total = 1;
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

    private void showGenderInfo(List<Record> query) throws DocumentException, IOException {
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
        info += "El total de gente atendida es de " + (at_h + at_m)
                + "personas, de los cuales "
                + (ev_h + ev_m)
                + " han sido evacuadas. \nSe han realizado, en total, "
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
            ObservableList<PieChart.Data> ev_data
                    = FXCollections.observableArrayList(
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

    private void showServiceInfo(List<Record> query) throws SQLException, IOException, BadElementException, DocumentException {
        List<Service> queryS = DAO.services.queryBuilder().query();
        List<String> lista_services = new ArrayList<String>();
        List<String> lista_info = new ArrayList<String>();
        XYChart.Series serie = new XYChart.Series();
        final NumberAxis yAxis = new NumberAxis();
        final CategoryAxis xAxis = new CategoryAxis();
        final BarChart<String, Number> bc = new BarChart<String, Number>(xAxis, yAxis);
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
                serie.getData().add(new XYChart.Data(service.getName(), count));
                lista_info.add(s);
            }
        }
        String info = "Desglose de los registros filtrados por servicio: \n";
        for (String s : lista_info) {
            info += "       - " + s + "\n";
        }
        tabla_info.getChildren().add(new Label(info));
        textList.add(info);
        if (cb_graphs.isSelected()) {
            bc.getData().add(serie);
            bc.setTitle("Desglose de registros por servicio");
            bc.setPrefSize(250, 250);
            bc.setLegendVisible(false);
            tabla_info.getChildren().add(bc);
            nodeList.add(bc); 
        }
    }

    private void showAreaInfo(List<Record> query) throws SQLException, IOException, BadElementException, DocumentException {
        List<Area> queryA = DAO.area.queryBuilder().query();
        List<String> lista_areas = new ArrayList<String>();
        List<String> lista_info = new ArrayList<String>();
        XYChart.Series serie = new XYChart.Series();
        final NumberAxis yAxis = new NumberAxis();
        final CategoryAxis xAxis = new CategoryAxis();
        final BarChart<String, Number> bc
                = new BarChart<String, Number>(xAxis, yAxis);
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
                serie.getData().add(new XYChart.Data(area.getName(), count));
                lista_info.add(s);
            }
        }
        String info = "Desglose de los registros filtrados por area: \n";
        for (String s : lista_info) {
            info += "       - " + s + "\n";
        }
        tabla_info.getChildren().add(new Label(info));
        textList.add(info);
        if (cb_graphs.isSelected()) {
            bc.getData().addAll(serie);
            bc.setTitle("Desglose de los registros por área");
            bc.setPrefSize(250, 250);
            bc.setLegendVisible(false);
            tabla_info.getChildren().add(bc);
            nodeList.add(bc);
        }
    }

    private void showResourceInfo(List<Record> query) throws SQLException, IOException, BadElementException, DocumentException {
        List<Resource> queryR = DAO.resource.queryBuilder().query();
        List<String> lista_resource = new ArrayList<String>();
        List<String> lista_info = new ArrayList<String>();

                XYChart.Series serie = new XYChart.Series();
        final NumberAxis yAxis = new NumberAxis();
        final CategoryAxis xAxis = new CategoryAxis();
        final BarChart<String, Number> bc
                = new BarChart<String, Number>(xAxis, yAxis);
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
                serie.getData().add(new XYChart.Data(resource.getCode(), count));
                lista_info.add(s);
            }
        }

        String info = "Desglose de los registros filtrados por recurso: \n";
        for (String s : lista_info) {
            info += "       - " + s + "\n";
        }

        tabla_info.getChildren().add(new Label(info));
        textList.add(info);
        
        if (cb_graphs.isSelected()) {
            bc.getData().addAll(serie);
            bc.setTitle("Desglose de los registros por recurso");
            bc.setPrefSize(250, 250);
            bc.setLegendVisible(false);
            tabla_info.getChildren().add(bc);
            nodeList.add(bc);
        }
    }

    private void showHoursInfo(List<Record> query) throws DocumentException {
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
        textList.add(info);
    }

}
