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
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

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
    private TableColumn<Resource, Assembly> asambleaColumnRecursos;
    @FXML
    private JFXComboBox<Assembly> asambleaRecursos;

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

    @FXML
    private JFXTextField nombreUsuario;

    @FXML
    private JFXTextField userUsuario;

    @FXML
    private JFXTextField passUsuario;

    @FXML
    private JFXButton botonUsuario;

    @FXML
    private TableView<User> tablaUsuarios;

    @FXML
    private TableColumn<User, String> nombreColumnUsuario;

    @FXML
    private TableColumn<User, String> userColumnUsuario;

    @FXML
    private TableColumn<User, String> passwordColumnUsuario;

    @FXML
    private TableColumn<User, User> iconosColumnUsuarios;

    private Service servicio_update;
    private Assembly asamblea_update;
    private Resource recurso_update;
    private Area area_update;
    private Hospital hospital_update;
    private User user_update;

    public AdminController() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/view/admin.fxml"));
            fxmlLoader.setController(this);

            Scene scene = new Scene(fxmlLoader.load(), 1200, 720);
            Stage stage = new Stage();
            stage.setMaxWidth(800);
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
            List<Hospital> hospitales = DAO.hospital.queryBuilder().query();
            List<User> usuarios = DAO.users.queryBuilder().query();

            areaServicios.getItems().addAll(areas);
            asambleaRecursos.getItems().addAll(asambleas);
            tablaServicios.getItems().addAll(servicios);
            tablaAreas.getItems().addAll(areas);
            tablaAsambleas.getItems().addAll(asambleas);
            tablaHospitales.getItems().addAll(hospitales);
            tablaRecursos.getItems().addAll(recursos);
            tablaUsuarios.getItems().addAll(usuarios);

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
            iconosColumnUsuarios.setCellValueFactory(
                    param -> new ReadOnlyObjectWrapper<>(param.getValue())
            );
            iconosColumnServicios.setCellFactory(param -> new TableCell<Service, Service>() {
                protected void updateItem(Service servicio, boolean empty) {
                    super.updateItem(servicio, empty);
                    SVGPath iconDelete = getIconDelete();
                    if (servicio == null) {
                        setGraphic(null);
                        return;
                    }
                    setGraphic(iconDelete);
                    iconDelete.setOnMouseClicked((event) -> {
                        Alert alert = getAlertDelete();
                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.get() == alert.getButtonTypes().get(0)) {
                            getTableView().getItems().remove(servicio);

                            try {
                                DAO.services.delete(servicio);
                            } catch (SQLException ex) {
                                Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } else {
                            alert.close();
                        }

                    });
                }
            });

            iconosColumnAreas.setCellFactory(param -> new TableCell<Area, Area>() {
                protected void updateItem(Area area, boolean empty) {
                    super.updateItem(area, empty);
                    SVGPath iconDelete = getIconDelete();
                    if (area == null) {
                        setGraphic(null);
                        return;
                    }
                    setGraphic(iconDelete);
                    iconDelete.setOnMouseClicked((event) -> {
                        Alert alert = getAlertDelete();
                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.get() == alert.getButtonTypes().get(0)) {
                            getTableView().getItems().remove(area);
                            try {
                                DAO.area.delete(area);
                            } catch (SQLException ex) {
                                Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } else {
                            alert.close();
                        }

                    });
                }
            });
            iconosColumnAsamblea.setCellFactory(param -> new TableCell<Assembly, Assembly>() {
                protected void updateItem(Assembly asamblea, boolean empty) {
                    super.updateItem(asamblea, empty);
                    SVGPath iconDelete = getIconDelete();
                    if (asamblea == null) {
                        setGraphic(null);
                        return;
                    }
                    setGraphic(iconDelete);
                    iconDelete.setOnMouseClicked((event) -> {
                        Alert alert = getAlertDelete();
                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.get() == alert.getButtonTypes().get(0)) {
                            getTableView().getItems().remove(asamblea);
                            try {
                                DAO.assembly.delete(asamblea);
                            } catch (SQLException ex) {
                                Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } else {
                            alert.close();
                        }

                    });
                }
            });

            iconosColumnRecursos.setCellFactory(param -> new TableCell<Resource, Resource>() {
                protected void updateItem(Resource recurso, boolean empty) {
                    super.updateItem(recurso, empty);
                    SVGPath iconDelete = getIconDelete();
                    if (recurso == null) {
                        setGraphic(null);
                        return;
                    }
                    setGraphic(iconDelete);
                    iconDelete.setOnMouseClicked((event) -> {

                        Alert alert = getAlertDelete();
                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.get() == alert.getButtonTypes().get(0)) {

                            getTableView().getItems().remove(recurso);
                            try {
                                DAO.resource.delete(recurso);
                            } catch (SQLException ex) {
                                Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } else {
                            alert.close();
                        }

                    });
                }
            });

            iconosColumnHospital.setCellFactory(param -> new TableCell<Hospital, Hospital>() {
                protected void updateItem(Hospital hospital, boolean empty) {
                    super.updateItem(hospital, empty);
                    SVGPath iconDelete = getIconDelete();
                    if (hospital == null) {
                        setGraphic(null);
                        return;
                    }
                    setGraphic(iconDelete);
                    iconDelete.setOnMouseClicked((event) -> {

                        Alert alert = getAlertDelete();
                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.get() == alert.getButtonTypes().get(0)) {
                            getTableView().getItems().remove(hospital);
                            try {
                                DAO.hospital.delete(hospital);
                            } catch (SQLException ex) {
                                Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } else {
                            alert.close();
                        }

                    });
                }
            });
            iconosColumnUsuarios.setCellFactory(param -> new TableCell<User, User>() {
                protected void updateItem(User user, boolean empty) {
                    super.updateItem(user, empty);
                    SVGPath iconDelete = getIconDelete();
                    if (user == null) {
                        setGraphic(null);
                        return;
                    }
                    setGraphic(iconDelete);
                    iconDelete.setOnMouseClicked((event) -> {
                        Alert alert = getAlertDelete();
                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.get() == alert.getButtonTypes().get(0)) {
                            getTableView().getItems().remove(user);
                            try {
                                DAO.users.delete(user);
                            } catch (SQLException ex) {
                                Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } else {
                            alert.close();
                        }

                    });
                }
            });

            nombreColumnServicios.setCellValueFactory(new PropertyValueFactory<>("name"));
            areaColumnServicios.setCellValueFactory(new PropertyValueFactory<>("area"));

            nombreColumnAreas.setCellValueFactory(new PropertyValueFactory<>("name"));
            abreviaturaColumnAreas.setCellValueFactory(new PropertyValueFactory<>("shortName"));

            nombreColumnAsamblea.setCellValueFactory(new PropertyValueFactory<>("name"));
            codigoColumnAsamblea.setCellValueFactory(new PropertyValueFactory<>("code"));

            nombreColumnRecursos.setCellValueFactory(new PropertyValueFactory<>("name"));
            codigoColumnRecursos.setCellValueFactory(new PropertyValueFactory<>("code"));
            asambleaColumnRecursos.setCellValueFactory(new PropertyValueFactory<>("assembly"));
            
            nombreColumnHospital.setCellValueFactory(new PropertyValueFactory<>("name"));
            codigoColumnHospital.setCellValueFactory(new PropertyValueFactory<>("code"));

            nombreColumnUsuario.setCellValueFactory(new PropertyValueFactory<>("name_user"));
            userColumnUsuario.setCellValueFactory(new PropertyValueFactory<>("username"));
            passwordColumnUsuario.setCellValueFactory(new PropertyValueFactory<>("password"));

            areaColumnServicios.setCellFactory(cell -> new TableCell<Service, Area>() {
                @Override
                public void updateItem(Area item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setText(null);
                    } else {
                        setText(item.getName() + "");
                    }
                }
            });

            asambleaColumnRecursos.setCellFactory(cell -> new TableCell<Resource, Assembly>() {
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

            botonServicios.setOnAction((event) -> {
                try {
                    if (servicio_update == null) {
                        Service serv = new Service(nombreServicios.getText(), areaServicios.getValue());
                        DAO.services.create(serv);
                        tablaServicios.getItems().add(serv);
                        nombreServicios.clear();
                        areaServicios.setValue(null);
                    } else {
                        servicio_update.setName(nombreServicios.getText());
                        servicio_update.setArea(areaServicios.getValue());
                        DAO.services.update(servicio_update);
                        tablaServicios.refresh();
                        servicio_update = null;
                        botonServicios.setText("Añadir");
                        nombreServicios.clear();
                        areaServicios.setValue(null);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            botonAreas.setOnAction((event) -> {
                try {
                    if (area_update == null) {
                        Area area = new Area(nombreAreas.getText(), abreviaturaAreas.getText());
                        DAO.area.create(area);
                        tablaAreas.getItems().add(area);
                        nombreAreas.clear();
                        abreviaturaAreas.clear();
                    } else {
                        area_update.setName(nombreAreas.getText());
                        area_update.setShortName(abreviaturaAreas.getText());
                        DAO.area.update(area_update);
                        tablaAreas.refresh();
                        area_update = null;
                        botonAreas.setText("Añadir");
                        nombreAreas.clear();
                        abreviaturaAreas.clear();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            botonAsambleas.setOnAction((event) -> {
                try {

                    if (asamblea_update == null) {
                        Assembly asamblea = new Assembly(nombreAsambleas.getText(), codigoAsambleas.getText());
                        DAO.assembly.create(asamblea);
                        tablaAsambleas.getItems().add(asamblea);
                        nombreAsambleas.clear();
                        codigoAsambleas.clear();
                    } else {
                        asamblea_update.setName(nombreAsambleas.getText());
                        asamblea_update.setCode(codigoAsambleas.getText());
                        DAO.assembly.update(asamblea_update);
                        tablaAsambleas.refresh();
                        asamblea_update = null;
                        botonAsambleas.setText("Añadir");
                        nombreAsambleas.clear();
                        codigoAsambleas.clear();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            botonRecursos.setOnAction((event) -> {
                try {
                    if (recurso_update == null) {
                        Resource recurso = new Resource(nombreRecursos.getText(), codigoRecursos.getText(), asambleaRecursos.getValue());
                        DAO.resource.create(recurso);
                        tablaRecursos.getItems().add(recurso);
                        nombreRecursos.clear();
                        codigoRecursos.clear();
                        asambleaRecursos.setValue(null);
                    } else {
                        recurso_update.setName(nombreRecursos.getText());
                        recurso_update.setCode(codigoRecursos.getText());
                        recurso_update.setAssembly(asambleaRecursos.getValue());
                        DAO.resource.update(recurso_update);
                        tablaRecursos.refresh();
                        recurso_update = null;
                        botonRecursos.setText("Añadir");
                        nombreRecursos.clear();
                        codigoRecursos.clear();
                        asambleaRecursos.setValue(null);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            botonHospital.setOnAction((event) -> {
                try {
                    if (hospital_update == null) {
                        Hospital hospital = new Hospital(codigoHospital.getText(), nombreHospital.getText());
                        DAO.hospital.create(hospital);
                        tablaHospitales.getItems().add(hospital);
                        codigoHospital.clear();
                        nombreHospital.clear();
                    } else {
                        hospital_update.setCode(codigoHospital.getText());
                        hospital_update.setName(nombreHospital.getText());
                        DAO.hospital.update(hospital_update);
                        tablaHospitales.refresh();
                        hospital_update = null;
                        botonHospital.setText("Añadir");
                        codigoHospital.clear();
                        nombreHospital.clear();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            botonUsuario.setOnAction((event) -> {
                try {
                    if (user_update == null) {
                        User user = new User(nombreUsuario.getText(), userUsuario.getText(), passUsuario.getText());
                        DAO.users.create(user);
                        tablaUsuarios.getItems().add(user);
                        nombreUsuario.clear();
                        userUsuario.clear();
                        passUsuario.clear();
                    } else {
                        user_update.setName_user(nombreUsuario.getText());
                        user_update.setUsername(userUsuario.getText());
                        user_update.setPassword(passUsuario.getText());
                        DAO.users.update(user_update);
                        tablaUsuarios.refresh();
                        user_update = null;
                        botonUsuario.setText("Añadir");
                        nombreUsuario.clear();
                        userUsuario.clear();
                        passUsuario.clear();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        tablaServicios.setRowFactory(tv -> {
            TableRow<Service> row = getRow();

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
            TableRow<Assembly> row = getRow();

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
            TableRow<Resource> row = getRow();

            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    nombreRecursos.setText(row.getItem().getName());
                    codigoRecursos.setText(row.getItem().getCode());
                    asambleaRecursos.setValue(row.getItem().getAssembly());
                    recurso_update = row.getItem();
                    botonRecursos.setText("Guardar");
                }
            });
            return row;
        });
        tablaAreas.setRowFactory(tv -> {
            TableRow<Area> row = getRow();

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

        tablaHospitales.setRowFactory(tv -> {
            TableRow<Hospital> row = getRow();

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
        tablaUsuarios.setRowFactory(tv -> {
            TableRow<User> row = getRow();

            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    nombreUsuario.setText(row.getItem().getName_user());
                    userUsuario.setText(row.getItem().getUsername());
                    passUsuario.setText(row.getItem().getPassword());
                    user_update = row.getItem();
                    botonUsuario.setText("Guardar");
                }
            });
            return row;
        });

    }

    public SVGPath getIconDelete() {
        SVGPath iconDelete = new SVGPath();
        iconDelete.setContent("M19,4H15.5L14.5,3H9.5L8.5,4H5V6H19M6,19A2,2 0 0,0 8,21H16A2,2 0 0,0 18,19V7H6V19Z");
        iconDelete.setCursor(Cursor.HAND);
        iconDelete.setFill(Paint.valueOf("#545454"));
        return iconDelete;
    }

    public Alert getAlertDelete() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Dialogo de confirmación");
        alert.setHeaderText(null);
        alert.setContentText("¿Estas seguro de que quieres borrar el evento? "
                + "No se podra revertir la acción.");

        ButtonType yesButton = new ButtonType("Sí");
        ButtonType noButton = new ButtonType("No");
        alert.getButtonTypes().setAll(yesButton, noButton);
        return alert;
    }

    public <T> TableRow getRow() {
        TableRow<T> row = new TableRow<>();
        row.setCursor(Cursor.HAND);
        return row;
    }
}
