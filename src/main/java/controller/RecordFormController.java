package controller;

import com.j256.ormlite.stmt.QueryBuilder;
import com.jfoenix.controls.*;
import com.sun.javafx.scene.control.skin.TableHeaderRow;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.SVGPath;
import javafx.util.Callback;
import model.*;
import service.DAO;
import utils.EditingCell;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class RecordFormController {

    @FXML
    private BorderPane rootID;

    @FXML
    private JFXButton addID;

    @FXML
    private SVGPath settingsID;

    @FXML
    private Label codeID;

    @FXML
    private JFXDatePicker dateID;

    @FXML
    private JFXTimePicker startTimeID;

    @FXML
    private JFXTimePicker endTimeID;

    @FXML
    private JFXComboBox<Assembly> assemblyID;

    @FXML
    private JFXComboBox<Resource> resourceID;

    @FXML
    private JFXComboBox<Applicant> applicantID;

    @FXML
    private JFXComboBox<Area> areaID;

    @FXML
    private JFXComboBox<Service> serviceID;

    @FXML
    private JFXTextField evacuated_mID;

    @FXML
    private JFXTextField evacuated_hID;

    @FXML
    private JFXTextField assistance_hID;

    @FXML
    private JFXTextField assistance_mID;

    @FXML
    private JFXTextField registryID;

    @FXML
    private JFXTextField patientID;

    @FXML
    private JFXTextField notesID;

    @FXML
    private JFXTextField transferID;

    @FXML
    private JFXTimePicker startTimeAssistanceID;

    @FXML
    private JFXTimePicker transferTimeAssistanceID;

    @FXML
    private JFXTimePicker endTimeAssistanceID;

    @FXML
    private JFXTextField pathologyID;

    @FXML
    private JFXButton addEventID;

    @FXML
    private SVGPath closeID;

    @FXML
    private TableView<Event> eventsTableID;

    @FXML
    private TableColumn<Event, String> subCodeColumID;

    @FXML
    private TableColumn<Event, String> transferColumID;

    @FXML
    private TableColumn<Event, String> startTimeAssistanceColumID;

    @FXML
    private TableColumn<Event, String> transferTimeAssistanceColumID;

    @FXML
    private TableColumn<Event, String> endTimeAssistanceColumID;

    @FXML
    private TableColumn<Event, String> pathologyColumID;

    @FXML
    private TableColumn<Event, String> registryColumID;

    @FXML
    private AnchorPane eventFormID;

    private Record record;
    JFXSnackbar snackbar;

    private void loadView() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/recordForm.fxml"));
            fxmlLoader.setController(this);
            Parent parent = fxmlLoader.load();
            SuperController.getInstance().getScene().setRoot(parent);
        } catch (IOException e) {
            e.printStackTrace();
        }

        snackbar = new JFXSnackbar(rootID);
        snackbar.layoutBoundsProperty().addListener((ob, o, n) -> {
            Bounds contentBound = snackbar.getLayoutBounds();
            double offsetX = rootID.getWidth() - contentBound.getWidth() - 20;
            double offsetY = rootID.getHeight() - contentBound.getHeight();
            snackbar.setLayoutX(offsetX);
            snackbar.setLayoutY(offsetY - 20);
        });
        init();
    }

    public RecordFormController() {
        loadView();

        dateID.setValue(LocalDate.now());
        startTimeID.setValue(LocalTime.parse(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))));
        eventFormID.setDisable(true);
    }

    public RecordFormController(Record record) {
        loadView();
        this.record = record;

        LocalDate date = LocalDate.parse(record.getDate());
        codeID.setText("#" + String.valueOf(date.getYear()).substring(2, 4) + "/" +
                String.format("%05d", record.getCode()));
        dateID.setValue(date);
        startTimeID.setValue(LocalTime.parse(record.getStartTime()));
        if (record.getEndTime() != null) endTimeID.setValue(LocalTime.parse(record.getEndTime()));
        assemblyID.getSelectionModel().select(record.getAssembly());
        resourceID.getSelectionModel().select(record.getResource());
        applicantID.getSelectionModel().select(record.getApplicant());
        areaID.getSelectionModel().select(record.getArea());
        serviceID.getSelectionModel().select(record.getService());
        evacuated_hID.setText(record.getEvacuated_h() + "");
        evacuated_mID.setText(record.getEvacuated_m() + "");
        assistance_hID.setText(record.getAssistance_h() + "");
        assistance_mID.setText(record.getAssistance_m() + "");
        registryID.setText(record.getRegistry());
        notesID.setText(record.getNotes());

        try {
            QueryBuilder<Event, Integer> queryBuilder = DAO.eventDao.queryBuilder();
            List<Event> records = queryBuilder.where().eq("record_id", record.getID_record()).query();
            eventsTableID.getItems().addAll(records);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        addID.setText("Guardar");
    }

    private void init() {
        try {
            List<Assembly> assemblies = DAO.assemblyDao.queryBuilder().query();
            List<Applicant> applicants = DAO.applicantDao.queryBuilder().query();
            List<Resource> resources = DAO.resourceDao.queryBuilder().query();
            List<Area> areas = DAO.areaDao.queryBuilder().query();

            assemblyID.getItems().addAll(assemblies);
            applicantID.getItems().addAll(applicants);
            resourceID.getItems().addAll(resources);
            areaID.getItems().addAll(areas);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        startTimeID.setIs24HourView(true);
        endTimeID.setIs24HourView(true);

        assistance_mID.textProperty().addListener(onlyNumbers(assistance_mID));
        assistance_mID.focusedProperty().addListener(minZero(assistance_mID));

        assistance_hID.textProperty().addListener(onlyNumbers(assistance_hID));
        assistance_hID.focusedProperty().addListener(minZero(assistance_hID));

        evacuated_mID.textProperty().addListener(onlyNumbers(evacuated_mID));
        evacuated_mID.focusedProperty().addListener(minZero(evacuated_mID));

        evacuated_hID.textProperty().addListener(onlyNumbers(evacuated_hID));
        evacuated_hID.focusedProperty().addListener(minZero(evacuated_hID));

        endTimeID.getEditor().setOnMouseClicked(event -> {
            if(event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2){
                if (endTimeID.getValue() == null)
                    endTimeID.setValue(LocalTime.parse(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))));
            }
        });

        areaID.valueProperty().addListener((ob, o, n) -> {
            try {
                List<Service> services = DAO.servicesDao.queryBuilder()
                        .where().eq("area_id", areaID.getValue().getID_area()).query();
                serviceID.getItems().clear();
                serviceID.getItems().addAll(services);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        });

        Callback<TableColumn<Event, String>, TableCell<Event, String>> cellFactoryEvent
                = (TableColumn<Event, String> p) -> new EditingCell();

        subCodeColumID.setCellValueFactory(new PropertyValueFactory<>("subcode"));
        startTimeAssistanceColumID.setCellValueFactory(new PropertyValueFactory<>("startTimeAssistance"));
        transferTimeAssistanceColumID.setCellValueFactory(new PropertyValueFactory<>("transferTimeAssistance"));
        endTimeAssistanceColumID.setCellValueFactory(new PropertyValueFactory<>("endTimeAssistance"));
        transferColumID.setCellValueFactory(new PropertyValueFactory<>("placeTransfer"));
        pathologyColumID.setCellValueFactory(new PropertyValueFactory<>("pathology"));
        registryColumID.setCellValueFactory(new PropertyValueFactory<>("registry"));

        subCodeColumID.setCellFactory(cell -> new TableCell<Event, String>() {
            @Override
            public void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) setText(null);
                else setText(codeID.getText() + "/" + item);
            }
        });

        startTimeAssistanceColumID.setCellFactory(cellFactoryEvent);
        transferTimeAssistanceColumID.setCellFactory(cellFactoryEvent);
        endTimeAssistanceColumID.setCellFactory(cellFactoryEvent);
        transferColumID.setCellFactory(cellFactoryEvent);
        pathologyColumID.setCellFactory(cellFactoryEvent);
        registryColumID.setCellFactory(cellFactoryEvent);

        startTimeAssistanceColumID.setOnEditCommit(
                (TableColumn.CellEditEvent<Event, String> t) ->
                        t.getTableView().getItems().get(t.getTablePosition().getRow())
                                .setStartTimeAssistance(t.getNewValue()));
        transferTimeAssistanceColumID.setOnEditCommit(
                (TableColumn.CellEditEvent<Event, String> t) ->
                        t.getTableView().getItems().get(t.getTablePosition().getRow())
                                .setTransferTimeAssistance(t.getNewValue()));
        endTimeAssistanceColumID.setOnEditCommit(
                (TableColumn.CellEditEvent<Event, String> t) ->
                        t.getTableView().getItems().get(t.getTablePosition().getRow())
                                .setEndTimeAssistance(t.getNewValue()));
        transferColumID.setOnEditCommit(
                (TableColumn.CellEditEvent<Event, String> t) ->
                        t.getTableView().getItems().get(t.getTablePosition().getRow())
                                .setPlaceTransfer(t.getNewValue()));

        pathologyColumID.setOnEditCommit(
                (TableColumn.CellEditEvent<Event, String> t) ->
                        t.getTableView().getItems().get(t.getTablePosition().getRow())
                                .setPathology(t.getNewValue()));

        registryColumID.setOnEditCommit(
                (TableColumn.CellEditEvent<Event, String> t) ->
                        t.getTableView().getItems().get(t.getTablePosition().getRow())
                                .setRegistry(t.getNewValue()));

        addEventID.setOnAction(e -> {
            Event event = new Event(record, eventsTableID.getItems().size() + 1, startTimeAssistanceID.getValue(),
                    transferTimeAssistanceID.getValue(), transferID.getText(), endTimeAssistanceID.getValue(),
                    patientID.getText(), patientID.getText());

            eventsTableID.getItems().add(event);

            try {
                DAO.eventDao.create(event);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            startTimeAssistanceID.setValue(null);
            endTimeAssistanceID.setValue(null);
            transferTimeAssistanceID.setValue(null);
            patientID.clear();
            transferID.clear();
        });

        eventsTableID.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
            if (event.isControlDown()) return;
            if (eventsTableID.getEditingCell() == null) {
                eventsTableID.getSelectionModel().clearSelection();
            }
        });

        eventsTableID.widthProperty().addListener((ob, o, n) -> {
            TableHeaderRow header = (TableHeaderRow) eventsTableID.lookup("TableHeaderRow");
            header.reorderingProperty().addListener((ob2, o2, n2) -> header.setReordering(false));
        });
    }

    @FXML
    void onClose() {
        new RecordsController();
    }

    @FXML
    void onSave() {
        if (record == null) {
             record = new Record(dateID.getValue().toString(), 0, resourceID.getValue(), assemblyID.getValue(),
                    startTimeID.getValue(), endTimeID.getValue(), areaID.getValue(),
                    applicantID.getValue(), serviceID.getValue(), "", assistance_hID.getText(),
                    assistance_hID.getText(), evacuated_hID.getText(), evacuated_mID.getText(), registryID.getText(),
                    notesID.getText());
            try {
                DAO.recordDao.create(record);
                snackbar.show("El registro " + codeID.getText() + " se ha guardado correctamente.", 4000);
            } catch (SQLException e) {
                snackbar.show("Se ha producido un error al guardar el registro. Por favor, intentelo de nuevo.", 6000);
                record = null;
            }

            addID.setText("Guardar");
            LocalDate date = LocalDate.parse(record.getDate());
            codeID.setText("#" + String.valueOf(date.getYear()).substring(2, 4) + "/" +
                    String.format("%05d", record.getID_record()));
            eventFormID.setDisable(false);
        } else {
            record.setResource(resourceID.getValue());
            record.setAssembly(assemblyID.getValue());
            record.setStartTime(startTimeID.getValue().toString());
            record.setEndTime(endTimeID.getValue());
            record.setArea(areaID.getValue());
            record.setApplicant(applicantID.getValue());
            record.setService(serviceID.getValue());
            record.setAssistance_h(assistance_hID.getText());
            record.setAssistance_m(assistance_mID.getText());
            record.setEvacuated_h(evacuated_hID.getText());
            record.setEvacuated_m(evacuated_mID.getText());
            record.setRegistry(registryID.getText());
            record.setNotes(notesID.getText());

            try {
                record.update();

                for (Event event: eventsTableID.getItems()) {
                    event.update();
                }

                snackbar.show("El registro " + codeID.getText() + " se ha guardado correctamente.", 4000);
            } catch (SQLException e) {
                snackbar.show("Se ha producido un error al guardar el registro. Por favor, intentelo de nuevo.", 6000);
            }
        }
    }

    private ChangeListener<String> onlyNumbers(TextField node) {
        return (observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                node.setText(newValue.replaceAll("[^\\d]", ""));
            }
        };
    }

    private ChangeListener<? super Boolean> minZero(TextField node) {
        return (observable, oldValue, newValue) -> {
            if (node.getText().equals("")) node.setText("0");
        };
    }

}