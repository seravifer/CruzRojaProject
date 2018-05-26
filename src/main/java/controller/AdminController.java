package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Paint;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;
import model.*;
import service.DAO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    private TableView<Hospital> tablaHospitales;

    @FXML
    private TableColumn<Hospital, String> codigoColumnHospital;

    @FXML
    private TableColumn<Hospital, String> nombreColumnHospital;

    @FXML
    private TableColumn<Hospital, Hospital> iconosColumnHospital;

    private Service servicio_update;
    private Assembly asamblea_update;
    private Resource recurso_update;
    private Area area_update;
    private Applicant solicitante_update;
    private Hospital hospital_update;

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
            List<Hospital> hospitales = DAO.hospital.queryBuilder().query();

            areaServicios.getItems().addAll(areas);
            tablaServicios.getItems().addAll(servicios);
            tablaAreas.getItems().addAll(areas);
            tablaAsambleas.getItems().addAll(asambleas);
            tablaSolicitantes.getItems().addAll(solicitantes);
            tablaHospitales.getItems().addAll(hospitales);
            tablaRecursos.getItems().addAll(recursos);

            nombreColumnServicios.setCellValueFactory(new PropertyValueFactory<>("name"));
            areaColumnServicios.setCellValueFactory(new PropertyValueFactory<>("area"));
            iconosColumnServicios.setCellValueFactory(
                    param -> new ReadOnlyObjectWrapper<>(param.getValue())
            );
            iconosColumnAreas.setCellValueFactory(
                    param -> new ReadOnlyObjectWrapper<>(param.getValue())
            );
            iconosColumnAsamblea.setCellValueFactory(
                    param -> new ReadOnlyObjectWrapper<>(param.getValue())
            );
            iconosColumnRecursos.setCellValueFactory(
                    param -> new ReadOnlyObjectWrapper<>(param.getValue())
            );
            iconosColumnHospital.setCellValueFactory(
                    param -> new ReadOnlyObjectWrapper<>(param.getValue())
            );
            iconosColumnSolicitante.setCellValueFactory(
                    param -> new ReadOnlyObjectWrapper<>(param.getValue())
            );

            iconosColumnServicios.setCellFactory(param -> new TableCell<Service, Service>() {
                protected void updateItem(Service servicio, boolean empty) {
                    super.updateItem(servicio, empty);
                    SVGPath iconDelete = new SVGPath();
                    iconDelete.setContent("M19,4H15.5L14.5,3H9.5L8.5,4H5V6H19M6,19A2,2 0 0,0 8,21H16A2,2 0 0,0 18,19V7H6V19Z");
                    iconDelete.setCursor(Cursor.HAND);
                    iconDelete.setFill(Paint.valueOf("#545454"));
                    if (servicio == null) {
                        setGraphic(null);
                        return;
                    }
                    setGraphic(iconDelete);
                    iconDelete.setOnMouseClicked((event) -> {
                        getTableView().getItems().remove(servicio);

                        try {
                            DAO.services.delete(servicio);
                        } catch (SQLException ex) {
                            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });
                }
            });

            iconosColumnAreas.setCellFactory(param -> new TableCell<Area, Area>() {
                protected void updateItem(Area area, boolean empty) {
                    super.updateItem(area, empty);
                    SVGPath iconDelete = new SVGPath();
                    iconDelete.setContent("M19,4H15.5L14.5,3H9.5L8.5,4H5V6H19M6,19A2,2 0 0,0 8,21H16A2,2 0 0,0 18,19V7H6V19Z");
                    iconDelete.setCursor(Cursor.HAND);
                    iconDelete.setFill(Paint.valueOf("#545454"));
                    if (area == null) {
                        setGraphic(null);
                        return;
                    }
                    setGraphic(iconDelete);
                    iconDelete.setOnMouseClicked((event) -> {
                        getTableView().getItems().remove(area);
                        try {
                            DAO.area.delete(area);
                        } catch (SQLException ex) {
                            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });
                }
            });
            iconosColumnAsamblea.setCellFactory(param -> new TableCell<Assembly, Assembly>() {
                protected void updateItem(Assembly asamblea, boolean empty) {
                    super.updateItem(asamblea, empty);
                    SVGPath iconDelete = new SVGPath();
                    iconDelete.setContent("M19,4H15.5L14.5,3H9.5L8.5,4H5V6H19M6,19A2,2 0 0,0 8,21H16A2,2 0 0,0 18,19V7H6V19Z");
                    iconDelete.setCursor(Cursor.HAND);
                    iconDelete.setFill(Paint.valueOf("#545454"));
                    if (asamblea == null) {
                        setGraphic(null);
                        return;
                    }
                    setGraphic(iconDelete);
                    iconDelete.setOnMouseClicked((event) -> {
                        getTableView().getItems().remove(asamblea);
                        try {
                            DAO.assembly.delete(asamblea);
                        } catch (SQLException ex) {
                            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });
                }
            });

            iconosColumnRecursos.setCellFactory(param -> new TableCell<Resource, Resource>() {
                protected void updateItem(Resource recurso, boolean empty) {
                    super.updateItem(recurso, empty);
                    SVGPath iconDelete = new SVGPath();
                    iconDelete.setContent("M19,4H15.5L14.5,3H9.5L8.5,4H5V6H19M6,19A2,2 0 0,0 8,21H16A2,2 0 0,0 18,19V7H6V19Z");
                    iconDelete.setCursor(Cursor.HAND);
                    iconDelete.setFill(Paint.valueOf("#545454"));
                    if (recurso == null) {
                        setGraphic(null);
                        return;
                    }
                    setGraphic(iconDelete);
                    iconDelete.setOnMouseClicked((event) -> {
                        getTableView().getItems().remove(recurso);
                        try {
                            DAO.resource.delete(recurso);
                        } catch (SQLException ex) {
                            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });
                }
            });

            iconosColumnHospital.setCellFactory(param -> new TableCell<Hospital, Hospital>() {
                protected void updateItem(Hospital hospital, boolean empty) {
                    super.updateItem(hospital, empty);
                    SVGPath iconDelete = new SVGPath();
                    iconDelete.setContent("M19,4H15.5L14.5,3H9.5L8.5,4H5V6H19M6,19A2,2 0 0,0 8,21H16A2,2 0 0,0 18,19V7H6V19Z");
                    iconDelete.setCursor(Cursor.HAND);
                    iconDelete.setFill(Paint.valueOf("#545454"));
                    if (hospital == null) {
                        setGraphic(null);
                        return;
                    }
                    setGraphic(iconDelete);
                    iconDelete.setOnMouseClicked((event) -> {
                        getTableView().getItems().remove(hospital);
                        try {
                            DAO.hospital.delete(hospital);
                        } catch (SQLException ex) {
                            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });
                }
            });
            iconosColumnSolicitante.setCellFactory(param -> new TableCell<Applicant, Applicant>() {
                protected void updateItem(Applicant solicitante, boolean empty) {
                    super.updateItem(solicitante, empty);
                    SVGPath iconDelete = new SVGPath();
                    iconDelete.setContent("M19,4H15.5L14.5,3H9.5L8.5,4H5V6H19M6,19A2,2 0 0,0 8,21H16A2,2 0 0,0 18,19V7H6V19Z");
                    iconDelete.setCursor(Cursor.HAND);
                    iconDelete.setFill(Paint.valueOf("#545454"));
                    if (solicitante == null) {
                        setGraphic(null);
                        return;
                    }
                    setGraphic(iconDelete);
                    iconDelete.setOnMouseClicked((event) -> {
                        getTableView().getItems().remove(solicitante);
                        try {
                            DAO.applicant.delete(solicitante);
                        } catch (SQLException ex) {
                            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });
                }
            });

            nombreColumnAreas.setCellValueFactory(new PropertyValueFactory<>("name"));
            abreviaturaColumnAreas.setCellValueFactory(new PropertyValueFactory<>("shortName"));

            nombreColumnAsamblea.setCellValueFactory(new PropertyValueFactory<>("name"));
            codigoColumnAsamblea.setCellValueFactory(new PropertyValueFactory<>("code"));

            nombreColumnSolicitante.setCellValueFactory(new PropertyValueFactory<>("name"));

            nombreColumnRecursos.setCellValueFactory(new PropertyValueFactory<>("name"));
            codigoColumnRecursos.setCellValueFactory(new PropertyValueFactory<>("code"));

            nombreColumnHospital.setCellValueFactory(new PropertyValueFactory<>("name"));
            codigoColumnHospital.setCellValueFactory(new PropertyValueFactory<>("code"));

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
            botonHospital.setOnAction((event) -> {
                try {
                    if (hospital_update == null) {
                        Hospital hospital = new Hospital(codigoHospital.getText(), nombreHospital.getText());
                        System.out.print(codigoHospital.getText() + nombreHospital.getText());
                        DAO.hospital.create(hospital);
                        tablaHospitales.getItems().add(hospital);
                    } else {
                        hospital_update.setCode(codigoHospital.getText());
                        hospital_update.setName(nombreHospital.getText());
                        DAO.hospital.update(hospital_update);
                        tablaHospitales.refresh();
                        codigoHospital.clear();
                        nombreHospital.clear();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        tablaServicios.setRowFactory(tv -> {
            TableRow<Service> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    nombreServicios.setText(row.getItem().getName());
                    areaServicios.setValue(row.getItem().getArea());
                    servicio_update = row.getItem();
                    botonServicios.setText("Guardar");
                }
            });
            return row;
        });
        tablaAsambleas.setRowFactory(tv -> {
            TableRow<Assembly> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    nombreAsambleas.setText(row.getItem().getName());
                    codigoAsambleas.setText(row.getItem().getCode());
                    asamblea_update = row.getItem();
                    botonAsambleas.setText("Guardar");
                }
            });
            return row;
        });
        tablaRecursos.setRowFactory(tv -> {
            TableRow<Resource> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    nombreRecursos.setText(row.getItem().getName());
                    codigoRecursos.setText(row.getItem().getCode());
                    recurso_update = row.getItem();
                    botonRecursos.setText("Guardar");
                }
            });
            return row;
        });
        tablaAreas.setRowFactory(tv -> {
            TableRow<Area> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    nombreAreas.setText(row.getItem().getName());
                    abreviaturaAreas.setText(row.getItem().getShortName());
                    area_update = row.getItem();
                    botonAreas.setText("Guardar");
                }
            });
            return row;
        });
        tablaSolicitantes.setRowFactory(tv -> {
            TableRow<Applicant> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    nombreSolicitantes.setText(row.getItem().getName());
                    solicitante_update = row.getItem();
                    botonSolicitantes.setText("Guardar");
                }
            });
            return row;
        });
        tablaHospitales.setRowFactory(tv -> {
            TableRow<Hospital> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    nombreHospital.setText(row.getItem().getName());
                    codigoHospital.setText(row.getItem().getCode());
                    hospital_update = row.getItem();
                    botonHospital.setText("Guardar");
                }
            });
            return row;
        });

    }

}
