package controller;

import com.j256.ormlite.stmt.QueryBuilder;
import com.jfoenix.controls.*;
import com.sun.javafx.scene.control.skin.TableHeaderRow;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.SVGPath;
import model.*;
import service.DAO;
import utils.*;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static utils.Utils.timeConverter;

public class RecordFormController {

    @FXML
    private BorderPane rootID;

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
    private JFXComboBox<Area> areaID;

    @FXML
    private JFXComboBox<Service> serviceID;

    @FXML
    private JFXComboBox<Operative> operativeID;

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
    private JFXTextField addressID;

    @FXML
    private JFXTextField notesID;

    @FXML
    private JFXComboBox<Hospital> hospitalID;

    @FXML
    private JFXTimePicker startTimeEventID;

    @FXML
    private JFXTimePicker startAssitanceEventID;

    @FXML
    private JFXTimePicker endTimeEventID;

    @FXML
    private JFXTimePicker transferTimeEventID;

    @FXML
    private JFXTextField pathologyID;

    @FXML
    private JFXComboBox<String> keyID;

    @FXML
    private JFXRadioButton maleID;

    @FXML
    private ToggleGroup gender;

    @FXML
    private JFXRadioButton femaleID;

    @FXML
    private JFXButton addEventID;

    @FXML
    private SVGPath closeID;

    @FXML
    private TableView<Event> eventsTableID;

    @FXML
    private TableColumn<Event, Integer> subCodeColumID;

    @FXML
    private TableColumn<Event, Hospital> transferColumID;

    @FXML
    private TableColumn<Event, LocalTime> startTimeColumID;

    @FXML
    private TableColumn<Event, LocalTime> startAssitanceColumID;

    @FXML
    private TableColumn<Event, LocalTime> transferTimeColumID;

    @FXML
    private TableColumn<Event, LocalTime> endTimeColumID;

    @FXML
    private TableColumn<Event, Integer> keyColumID;

    @FXML
    private TableColumn<Event, String> pathologyColumID;

    @FXML
    private TableColumn<Event, Integer> genderColumID;

    @FXML
    private TableColumn<Event, String> registryColumID;

    @FXML
    private TableColumn<Event, Event> iconColumnID;

    @FXML
    private AnchorPane eventFormID;

    private JFXSnackbar snackbar;

    private Record record;

    private void loadView() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/recordForm.fxml"));
            fxmlLoader.setController(this);
            Parent parent = fxmlLoader.load();
            SuperController.getInstance().setPage(parent, "Gestor de incidencias");
        } catch (IOException e) {
            e.printStackTrace();
        }

        init();
    }

    public RecordFormController() {
        loadView();

        dateID.setValue(LocalDate.now());
        startTimeID.setValue(LocalTime.now());
        eventFormID.setDisable(true);
    }

    public RecordFormController(Record record) {
        loadView();
        this.record = record;

        codeID.setText(Utils.generateCode(record));
        dateID.setValue(record.getDate());
        startTimeID.setValue(record.getStartTime());
        if (record.getEndTime() != null) endTimeID.setValue(record.getEndTime());
        assemblyID.getSelectionModel().select(record.getAssembly());
        resourceID.getSelectionModel().select(record.getResource());
        areaID.getSelectionModel().select(record.getArea());
        serviceID.getSelectionModel().select(record.getService());
        addressID.setText(record.getAddress());
        evacuated_hID.setText(record.getEvacuated_h() + "");
        evacuated_mID.setText(record.getEvacuated_m() + "");
        assistance_hID.setText(record.getAssistance_h() + "");
        assistance_mID.setText(record.getAssistance_m() + "");
        registryID.setText(record.getRegistry());
        notesID.setText(record.getNotes());
        operativeID.setValue(record.getOperative());

        try {
            QueryBuilder<Event, Integer> queryBuilder = DAO.event.queryBuilder();
            List<Event> records = queryBuilder.where().eq("record_id", record.getID_record()).query();
            eventsTableID.getItems().addAll(records);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void init() {
        try {
            List<Assembly> assemblies = DAO.assembly.queryBuilder().query();
            List<Resource> resources = DAO.resource.queryBuilder().query();
            List<Area> areas = DAO.area.queryBuilder().query();
            List<Operative> operatives = DAO.operatives.queryBuilder().query();
            List<Hospital> hospitals = DAO.hospital.queryBuilder().query();

            assemblyID.getItems().addAll(assemblies);
            resourceID.getItems().addAll(resources);
            areaID.getItems().addAll(areas);
            operativeID.getItems().addAll(operatives);
            hospitalID.getItems().addAll(hospitals);

            transferColumID.setCellFactory((TableColumn<Event, Hospital> p) -> new EditingCellList(hospitals));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        keyID.getItems().addAll("CLAVE 0", "CLAVE 1.1", "CLAVE 1.2", "CLAVE 1.3", "CLAVE 2", "CLAVE 3", "CLAVE 5", "CLAVE 6");

        snackbar = new JFXSnackbar(rootID) {
            @Override
            public void refreshPopup() {
                Bounds contentBound = this.getLayoutBounds();
                double offsetX = getPopupContainer().getWidth() - contentBound.getWidth() - 40;
                double offsetY = getPopupContainer().getHeight() - contentBound.getHeight() - 40;
                snackbar.setLayoutX(offsetX);
                snackbar.setLayoutY(offsetY);
            }
        };

        startTimeID.setIs24HourView(true);
        endTimeID.setIs24HourView(true);

        startTimeID.setConverter(timeConverter);
        endTimeID.setConverter(timeConverter);

        startTimeEventID.setConverter(timeConverter);
        startAssitanceEventID.setConverter(timeConverter);
        transferTimeEventID.setConverter(timeConverter);
        endTimeEventID.setConverter(timeConverter);

        Arrays.asList(assistance_mID, assistance_hID, evacuated_hID, evacuated_mID).forEach(node -> {
            node.textProperty().addListener(onlyNumbers(node));
            node.focusedProperty().addListener(minZero(node));
        });

        Arrays.asList(endTimeID, startTimeEventID, endTimeEventID, transferTimeEventID, startAssitanceEventID)
                .forEach(this::onDoubleClick);

        AutoComplete.set(resourceID,
                (typedText, itemToCompare) -> itemToCompare.getCode().toLowerCase().contains(typedText.toLowerCase()));

        AutoComplete.set(assemblyID,
                (typedText, itemToCompare) -> itemToCompare.getName().toLowerCase().contains(typedText.toLowerCase()));

        AutoComplete.set(operativeID,
                (typedText, itemToCompare) -> itemToCompare.getName().toLowerCase().contains(typedText.toLowerCase()));

        AutoComplete.set(hospitalID,
                (typedText, itemToCompare) -> itemToCompare.getName().toLowerCase().contains(typedText.toLowerCase()));

        areaID.valueProperty().addListener((ob, o, n) -> {
            try {
                List<Service> services = DAO.services.queryBuilder()
                        .where().eq("area_id", areaID.getValue().getID()).query();
                serviceID.getItems().clear();
                serviceID.getItems().addAll(services);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        subCodeColumID.setCellValueFactory(new PropertyValueFactory<>("subcode"));
        keyColumID.setCellValueFactory(new PropertyValueFactory<>("key"));
        startTimeColumID.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        startAssitanceColumID.setCellValueFactory(new PropertyValueFactory<>("startAssistance"));
        transferTimeColumID.setCellValueFactory(new PropertyValueFactory<>("transferTime"));
        endTimeColumID.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        transferColumID.setCellValueFactory(new PropertyValueFactory<>("hospital"));
        pathologyColumID.setCellValueFactory(new PropertyValueFactory<>("pathology"));
        registryColumID.setCellValueFactory(new PropertyValueFactory<>("registry"));
        genderColumID.setCellValueFactory(new PropertyValueFactory<>("gender"));
        iconColumnID.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));

        startTimeColumID.setCellFactory((TableColumn<Event, LocalTime> p) -> new EditingCellHour());
        startAssitanceColumID.setCellFactory((TableColumn<Event, LocalTime> p) -> new EditingCellHour());
        transferTimeColumID.setCellFactory((TableColumn<Event, LocalTime> p) -> new EditingCellHour());
        endTimeColumID.setCellFactory((TableColumn<Event, LocalTime> p) -> new EditingCellHour());
        pathologyColumID.setCellFactory((TableColumn<Event, String> p) -> new EditingCellString());
        registryColumID.setCellFactory((TableColumn<Event, String> p) -> new EditingCellString());

        subCodeColumID.setCellFactory(cell -> new TableCell<Event, Integer>() {
            @Override
            public void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) setText(null);
                else setText(codeID.getText() + "/" + item);
            }
        });

        iconColumnID.setCellFactory(param -> new TableCell<Event, Event>() {
            protected void updateItem(Event event, boolean empty) {
                super.updateItem(event, empty);
                SVGPath icon = new SVGPath();
                icon.setContent("M19,4H15.5L14.5,3H9.5L8.5,4H5V6H19M6,19A2,2 0 0,0 8,21H16A2,2 0 0,0 18,19V7H6V19Z");
                icon.setCursor(Cursor.HAND);
                icon.setFill(Paint.valueOf("#545454"));

                if (event == null || empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setGraphic(icon);
                    icon.setOnMouseClicked(e -> {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Dialogo de confirmación");
                        alert.setHeaderText(null);
                        alert.setContentText("¿Estas seguro de que quieres borrar el evento? " +
                                "No se podra revertir la acción.");

                        ButtonType yesButton = new ButtonType("Sí");
                        ButtonType noButton = new ButtonType("No");
                        alert.getButtonTypes().setAll(yesButton, noButton);

                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.get() == yesButton) {
                            try {
                                event.delete();

                                if (event.getGender() == 1) evacuated_hID.setText((Integer.parseInt(evacuated_hID.getText()) - 1) + "");
                                if (event.getGender() == 2) evacuated_mID.setText((Integer.parseInt(evacuated_mID.getText()) - 1) + "");

                                getTableView().getItems().remove(event);
                            } catch (SQLException e1) {
                                e1.printStackTrace();
                            }
                        } else alert.close();
                    });
                }
            }
        });

        genderColumID.setCellFactory(cell -> new TableCell<Event, Integer>() {
            @Override
            public void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);

                SVGPath icon = new SVGPath();
                if (item == null || empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(null);
                    switch (item) {
                        case 0:
                            setGraphic(null);
                            break;
                        case 1:
                            icon.setContent("M9,9C10.29,9 11.5,9.41 12.47,10.11L17.58,5H13V3H21V11H19V6.41L13.89,11.5C14.59,12.5 15,13.7 15,15A6,6 0 0,1 9,21A6,6 0 0,1 3,15A6,6 0 0,1 9,9M9,11A4,4 0 0,0 5,15A4,4 0 0,0 9,19A4,4 0 0,0 13,15A4,4 0 0,0 9,11Z");
                            icon.setFill(Paint.valueOf("#2711e8"));
                            break;
                        case 2:
                            icon.setContent("M12,4A6,6 0 0,1 18,10C18,12.97 15.84,15.44 13,15.92V18H15V20H13V22H11V20H9V18H11V15.92C8.16,15.44 6,12.97 6,10A6,6 0 0,1 12,4M12,6A4,4 0 0,0 8,10A4,4 0 0,0 12,14A4,4 0 0,0 16,10A4,4 0 0,0 12,6Z");
                            icon.setFill(Paint.valueOf("#ce20da"));
                            break;
                    }
                    setGraphic(icon);
                }
            }
        });

        startTimeColumID.setOnEditCommit(
                (TableColumn.CellEditEvent<Event, LocalTime> t) ->
                        t.getTableView().getItems().get(t.getTablePosition().getRow())
                                .setStartTime(t.getNewValue()));

        startAssitanceColumID.setOnEditCommit(
                (TableColumn.CellEditEvent<Event, LocalTime> t) ->
                        t.getTableView().getItems().get(t.getTablePosition().getRow())
                                .setStartAssistance(t.getNewValue()));

        transferTimeColumID.setOnEditCommit(
                (TableColumn.CellEditEvent<Event, LocalTime> t) ->
                        t.getTableView().getItems().get(t.getTablePosition().getRow())
                                .setTransferTime(t.getNewValue()));

        endTimeColumID.setOnEditCommit(
                (TableColumn.CellEditEvent<Event, LocalTime> t) ->
                        t.getTableView().getItems().get(t.getTablePosition().getRow())
                                .setEndTime(t.getNewValue()));

        pathologyColumID.setOnEditCommit(
                (TableColumn.CellEditEvent<Event, String> t) ->
                        t.getTableView().getItems().get(t.getTablePosition().getRow())
                                .setPathology(t.getNewValue()));

        registryColumID.setOnEditCommit(
                (TableColumn.CellEditEvent<Event, String> t) ->
                        t.getTableView().getItems().get(t.getTablePosition().getRow())
                                .setRegistry(t.getNewValue()));

        codeID.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2 && record == null) {
                record = new Record();
                record.setDate(dateID.getValue());
                try {
                    DAO.record.create(record);
                    record.refresh();

                    codeID.setText(Utils.generateCode(record));
                    eventFormID.setDisable(false);
                    codeID.setCursor(Cursor.DEFAULT);
                } catch (SQLException e) {
                    snackbar.show("Se ha producido un error al generar el registro. " +
                            "Por favor, intentelo de nuevo.", 6000);
                }
            }
        });

        addEventID.setOnAction(e -> {
            Event event = new Event(record, eventsTableID.getItems().size() + 1, keyID.getValue(),
                    startTimeEventID.getValue(), startAssitanceEventID.getValue(),
                    transferTimeEventID.getValue(), endTimeEventID.getValue(), AutoComplete.getValue(hospitalID), pathologyID.getText(),
                    patientID.getText(), getGender());

            eventsTableID.getItems().add(event);
            if (maleID.isSelected()) evacuated_hID.setText((Integer.parseInt(evacuated_hID.getText()) + 1) + "");
            if (femaleID.isSelected()) evacuated_mID.setText((Integer.parseInt(evacuated_mID.getText()) + 1) + "");

            try {
                DAO.event.create(event);

                startTimeEventID.setValue(null);
                endTimeEventID.setValue(null);
                transferTimeEventID.setValue(null);
                startAssitanceEventID.setValue(null);
                patientID.clear();
                hospitalID.setValue(null);
                pathologyID.clear();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        eventsTableID.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
            if (event.isControlDown()) return;
            eventsTableID.getSelectionModel().clearSelection();
        });

        eventsTableID.widthProperty().addListener((ob, o, n) -> {
            TableHeaderRow header = (TableHeaderRow) eventsTableID.lookup("TableHeaderRow");
            header.reorderingProperty().addListener((ob2, o2, n2) -> header.setReordering(false));
        });
    }

    @FXML
    private void onClose() {
        SuperController.getInstance().goBack();
    }

    @FXML
    private void onSave() {
        if (validate()) {
            snackbar.show("Los siguientes campos son obligatorios:\n " +
                    "Asamblea, Area, Servicio, Fecha y Hora de inicio.", 8000);
            return;
        }

        if (record == null) record = new Record();
        if (dateID.getValue() == null) dateID.setValue(LocalDate.now());

        record.setDate(dateID.getValue());
        record.setResource(AutoComplete.getValue(resourceID));
        record.setAssembly(AutoComplete.getValue(assemblyID));
        record.setStartTime(startTimeID.getValue());
        record.setEndTime(endTimeID.getValue());
        record.setArea(areaID.getValue());
        record.setService(serviceID.getValue());
        record.setAssistanceH(assistance_hID.getText());
        record.setAssistanceM(assistance_mID.getText());
        record.setEvacuatedH(evacuated_hID.getText());
        record.setEvacuatedM(evacuated_mID.getText());
        record.setAddress(addressID.getText());
        record.setRegistry(registryID.getText());
        record.setNotes(notesID.getText());
        record.setOperative(AutoComplete.getValue(operativeID));

        try {
            record.update();

            for (Event event : eventsTableID.getItems()) {
                event.update();
            }

            snackbar.show("El registro " + codeID.getText() + " se ha guardado correctamente.", 4000);
        } catch (SQLException e) {
            snackbar.show("Se ha producido un error al guardar el registro. Por favor, intentelo de nuevo.", 6000);
        }
    }

    private boolean validate() {
        return AutoComplete.getValue(assemblyID) == null || areaID.getValue() == null || serviceID.getValue() == null
                || dateID.getValue() == null || startTimeID.getValue() == null;
    }

    private int getGender() {
        if (maleID.isSelected()) return 1;
        else if (femaleID.isSelected()) return 2;
        else return 0;
    }

    /* Listener */
    private ChangeListener<String> onlyNumbers(TextField node) {
        return (observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) node.setText(newValue.replaceAll("[^\\d]", ""));
        };
    }

    private ChangeListener<? super Boolean> minZero(TextField node) {
        return (observable, oldValue, newValue) -> {
            Platform.runLater(() -> {
                if (node.isFocused()) node.selectAll();
            });

            if (node.getText().equals("")) node.setText("0");
        };
    }

    private void onDoubleClick(JFXTimePicker node) {
        node.getEditor().setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                if (node.getValue() == null)
                    node.setValue(LocalTime.now());
            }
        });
    }
}