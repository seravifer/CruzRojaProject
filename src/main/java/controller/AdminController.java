package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.*;
import service.DAO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.scene.shape.SVGPath;

public class AdminController {

    @FXML
    private JFXTextField nombreServicios;

    @FXML
    private JFXTextField abreviaturaServicios;

    @FXML
    private JFXComboBox<Area> areaServicios;

    @FXML
    private JFXButton botonServicios;

    @FXML
    private TableView<Service> tablaServicios;

    @FXML
    private TableColumn<Service, String> nombreColumnServicios;

    @FXML
    private TableColumn<Service, Area> areaColumnServicios;

    @FXML
    private TableColumn<Service, Service> iconosColumnServicios;
    
    @FXML
    private JFXTextField codigoAsambleas;

    @FXML
    private JFXTextField nombreAsambleas;

    @FXML
    private JFXButton botonAsambleas;

    @FXML
    private TableView<Assembly> tablaAsambleas;

    @FXML
    private TableColumn<Assembly, String> codigoColumnAsamblea;

    @FXML
    private TableColumn<Assembly, String> nombreColumnAsamblea;

    @FXML
    private TableColumn<Assembly, Assembly> iconosColumnAsamblea;
    
    @FXML
    private JFXTextField codigoRecursos;

    @FXML
    private JFXTextField nombreRecursos;

    @FXML
    private JFXButton botonRecursos;

    @FXML
    private TableView<Resource> tablaRecursos;

    @FXML
    private TableColumn<Resource, String> codigoColumnRecursos;

    @FXML
    private TableColumn<Resource, String> nombreColumnRecursos;
    
    @FXML
    private TableColumn<Resource, Resource> iconosColumnRecursos;

    @FXML
    private JFXTextField nombreAreas;

    @FXML
    private JFXTextField abreviaturaAreas;

    @FXML
    private JFXButton botonAreas;

    @FXML
    private TableView<Area> tablaAreas;

    @FXML
    private TableColumn<Area, String> nombreColumnAreas;

    @FXML
    private TableColumn<Area, String> abreviaturaColumnAreas;
    
    @FXML
    private TableColumn<Area, Area> iconosColumnAreas;

    @FXML
    private JFXTextField nombreSolicitantes;

    @FXML
    private JFXButton botonSolicitantes;

    @FXML
    private TableView<Applicant> tablaSolicitantes;

    @FXML
    private TableColumn<Applicant, String> nombreColumnSolicitante;
    
    @FXML
    private TableColumn<Applicant, Applicant> iconosColumnSolicitante;
    
    @FXML
    private JFXTextField codigoHospital;

    @FXML
    private JFXTextField nombreHospital;

    @FXML
    private JFXButton botonHospital;

    @FXML
    private TableView<Applicant> tablaHospitales;

    @FXML
    private TableColumn<Hospital, String> codigoColumnHospital;

    @FXML
    private TableColumn<Hospital, String> nombreColumnHospital;
    
    @FXML
    private TableColumn<Hospital, Hospital> iconosColumnHospital;
    
    public AdminController() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/view/admin.fxml"));
            fxmlLoader.setController(this);

            Scene scene = new Scene(fxmlLoader.load(), 1200, 720);
            Stage stage = new Stage();
            stage.setMaxWidth(600);
            stage.setMinWidth(600);
            stage.setHeight(480);
            stage.setMinHeight(480);
            scene.getStylesheets().setAll(getClass().getResource("/css/style.css").toExternalForm());

            stage.setTitle("Panel Administrador de Cruz Roja");
            stage.setScene(scene);
            stage.show();

            init();
        } catch (IOException e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create new Window.", e);
        }
    }

    public void init() {
        try {
            List<Area> areas = DAO.area.queryBuilder().query();
            List<Service> servicios = DAO.services.queryBuilder().query();
            List<Assembly> asambleas = DAO.assembly.queryBuilder().query();
            List<Resource> recursos = DAO.resource.queryBuilder().query();
            List<Applicant> solicitantes = DAO.applicant.queryBuilder().query();

            areaServicios.getItems().addAll(areas);
            tablaServicios.getItems().addAll(servicios);
            tablaAreas.getItems().addAll(areas);
            tablaAsambleas.getItems().addAll(asambleas);
            tablaSolicitantes.getItems().addAll(solicitantes);

            tablaRecursos.getItems().addAll(recursos);

            nombreColumnServicios.setCellValueFactory(new PropertyValueFactory<>("name"));
            areaColumnServicios.setCellValueFactory(new PropertyValueFactory<>("area"));
            iconosColumnServicios.setCellValueFactory(
                    param -> new ReadOnlyObjectWrapper<>(param.getValue())
            );
            SVGPath iconDelete = new SVGPath();
            iconDelete.setContent("M19,4H15.5L14.5,3H9.5L8.5,4H5V6H19M6,19A2,2 0 0,0 8,21H16A2,2 0 0,0 18,19V7H6V19Z");
                
            iconosColumnServicios.setCellFactory(param -> new TableCell<Service,Service>(){              
                protected void updateItem(Service servicio, boolean empty) {
                super.updateItem(servicio, empty);
                if (servicio == null) {
                    setGraphic(null);
                    return;
                }
                setGraphic(iconDelete);
                iconDelete.setOnMouseClicked((event) -> {
                    getTableView().getItems().remove(servicio);
                });
    }
            });

            nombreColumnAreas.setCellValueFactory(new PropertyValueFactory<>("name"));
            abreviaturaColumnAreas.setCellValueFactory(new PropertyValueFactory<>("short_name"));

            nombreColumnAsamblea.setCellValueFactory(new PropertyValueFactory<>("name_assembly"));
            codigoColumnAsamblea.setCellValueFactory(new PropertyValueFactory<>("code"));

            nombreColumnSolicitante.setCellValueFactory(new PropertyValueFactory<>("name_applicant"));

            nombreColumnRecursos.setCellValueFactory(new PropertyValueFactory<>("name_resource"));
            codigoColumnRecursos.setCellValueFactory(new PropertyValueFactory<>("code_resource"));

            areaColumnServicios.setCellFactory(cell -> new TableCell<Service, Area>() {
                @Override
                public void updateItem(Area item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) setText(null);
                    else setText(item.getName() + "");
                }
            });

            botonServicios.setOnAction((event) -> {
                try {
                    Service serv = new Service(nombreServicios.getText(), areaServicios.getValue());
                    DAO.services.create(serv);
                    tablaServicios.getItems().add(serv);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            botonAreas.setOnAction((event) -> {
                try {
                    Area area = new Area(nombreAreas.getText(), abreviaturaAreas.getText());
                    DAO.area.create(area);
                    tablaAreas.getItems().add(area);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            botonAsambleas.setOnAction((event) -> {
                try {
                    Assembly asamblea = new Assembly(nombreAsambleas.getText(), codigoAsambleas.getText());
                    DAO.assembly.create(asamblea);
                    tablaAsambleas.getItems().add(asamblea);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            botonRecursos.setOnAction((event) -> {
                try {
                    Resource recurso = new Resource(nombreRecursos.getText(), codigoRecursos.getText());
                    DAO.resource.create(recurso);
                    tablaRecursos.getItems().add(recurso);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            botonSolicitantes.setOnAction((event) -> {
                try {
                    Applicant solicitante = new Applicant(nombreSolicitantes.getText());
                    DAO.applicant.create(solicitante);
                    tablaSolicitantes.getItems().add(solicitante);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


}
