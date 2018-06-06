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
import utils.AutoComplete;
import utils.EditingCellString;
import utils.EditingCellHour;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
            SuperController.getInstance().setPage(parent,"Gestor de incidencias");
        } catch (IOException e) {
            e.printStackTrace();
        }

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
        areaID.getSelectionModel().select(record.getArea());
        serviceID.getSelectionModel().select(record.getService());
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

            assemblyID.getItems().addAll(assemblies);
            resourceID.getItems().addAll(resources);
            areaID.getItems().addAll(areas);
            operativeID.getItems().addAll(operatives);
        } catch (SQLException e) {
            e.printStackTrace();
        }

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
        startTimeAssistanceID.setConverter(timeConverter);
        transferTimeAssistanceID.setConverter(timeConverter);
        endTimeAssistanceID.setConverter(timeConverter);

        JFXTextField[] nodes = {assistance_mID, assistance_hID, evacuated_hID, evacuated_mID};
        for (JFXTextField node : nodes) {
            node.textProperty().addListener(onlyNumbers(node));
            node.focusedProperty().addListener(minZero(node));
        }

        AutoComplete.set(resourceID,
                (typedText, itemToCompare) -> itemToCompare.getCode().toLowerCase().contains(typedText.toLowerCase()));

        AutoComplete.set(assemblyID,
                (typedText, itemToCompare) -> itemToCompare.getName().toLowerCase().contains(typedText.toLowerCase()));

        AutoComplete.set(operativeID,
                (typedText, itemToCompare) -> itemToCompare.getName().toLowerCase().contains(typedText.toLowerCase()));

        endTimeID.getEditor().setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                if (endTimeID.getValue() == null)
                    endTimeID.setValue(LocalTime.parse(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))));
            }
        });

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
        startTimeAssistanceColumID.setCellValueFactory(new PropertyValueFactory<>("startTimeAssistance"));
        transferTimeAssistanceColumID.setCellValueFactory(new PropertyValueFactory<>("transferTimeAssistance"));
        endTimeAssistanceColumID.setCellValueFactory(new PropertyValueFactory<>("endTimeAssistance"));
        transferColumID.setCellValueFactory(new PropertyValueFactory<>("placeTransfer"));
        pathologyColumID.setCellValueFactory(new PropertyValueFactory<>("pathology"));
        registryColumID.setCellValueFactory(new PropertyValueFactory<>("registry"));
        iconColumnID.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));

        subCodeColumID.setCellFactory(cell -> new TableCell<Event, String>() {
            @Override
            public void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) setText(null);
                else setText(codeID.getText() + "/" + item);
            }
        });

        startTimeAssistanceColumID.setCellFactory((TableColumn<Event, String> p) -> new EditingCellHour());
        transferTimeAssistanceColumID.setCellFactory((TableColumn<Event, String> p) -> new EditingCellHour());
        endTimeAssistanceColumID.setCellFactory((TableColumn<Event, String> p) -> new EditingCellHour());
        transferColumID.setCellFactory((TableColumn<Event, String> p) -> new EditingCellString());
        pathologyColumID.setCellFactory((TableColumn<Event, String> p) -> new EditingCellString());
        registryColumID.setCellFactory((TableColumn<Event, String> p) -> new EditingCellString());

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
                DAO.event.create(event);

                startTimeAssistanceID.setValue(null);
                endTimeAssistanceID.setValue(null);
                transferTimeAssistanceID.setValue(null);
                patientID.clear();
                transferID.clear();
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
                                getTableView().getItems().remove(event);
                            } catch (SQLException e1) {
                                e1.printStackTrace();
                            }
                        } else alert.close();
                    });
                }
            }
        });
    }

    @FXML
    private void onClose() {
        SuperController.getInstance().goBack();
    }

    @FXML
    private void onSave() {
        if (validate()) {
            return;
        }

        if (record == null) {
            record = new Record(dateID.getValue().toString(), 0, AutoComplete.getValue(resourceID),
                    AutoComplete.getValue(assemblyID), startTimeID.getValue(), endTimeID.getValue(), areaID.getValue(),
                    serviceID.getValue(), "", assistance_hID.getText(), assistance_hID.getText(),
                    evacuated_hID.getText(), evacuated_mID.getText(), registryID.getText(), notesID.getText(),
                    AutoComplete.getValue(operativeID));
            try {
                DAO.record.create(record);
                record.refresh();

                LocalDate date = LocalDate.parse(record.getDate());
                codeID.setText("#" + String.valueOf(date.getYear()).substring(2, 4) + "/" +
                        String.format("%05d", record.getCode()));

                snackbar.show("El registro " + codeID.getText() + " se ha guardado correctamente.", 4000);

                eventFormID.setDisable(false); // TODO no deberia ser necesario guardar el registro
            } catch (SQLException e) {
                snackbar.show("Se ha producido un error al guardar el registro. Por favor, intentelo de nuevo.", 6000);
                record = null;
            }
        } else {
            record.setResource(AutoComplete.getValue(resourceID));
            record.setAssembly(AutoComplete.getValue(assemblyID));
            record.setStartTime(startTimeID.getValue().toString());
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
    }

    private boolean validate() {
        return AutoComplete.getValue(assemblyID) == null || areaID.getValue() == null || serviceID.getValue() == null
                || dateID.getValue() == null || startTimeID.getValue() == null;
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
            Platform.runLater(() -> {
                if (node.isFocused()) node.selectAll();
            });

            if (node.getText().equals("")) node.setText("0");
        };
    }
}