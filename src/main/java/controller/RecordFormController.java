package Controller;

import Model.*;
import Service.DAO;
import com.jfoenix.controls.*;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.shape.SVGPath;
import javafx.util.Callback;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class RecordFormController {

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
    private JFXTextField notesID;

    @FXML
    private SVGPath closeID;

    @FXML
    private JFXComboBox<Service> serviceID;

    @FXML
    private TreeTableColumn<EventProperty, Integer> subCodeID;

    @FXML
    private TreeTableColumn<EventProperty, String> transferID;

    @FXML
    private TreeTableColumn<EventProperty, String> startTimeAssistanceID;

    @FXML
    private TreeTableColumn<EventProperty, String> transferTimeAssistanceID;

    @FXML
    private TreeTableColumn<EventProperty, String> endTimeAssistanceID;

    private Record record;

    private void loadView() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/recordForm.fxml"));
        fxmlLoader.setController(this);
        Parent parent = null;
        try {
            parent = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(parent);
        SuperController.getInstance().setScene(scene);

        init();
    }

    public RecordFormController() {
        loadView();

        codeID.setText("#" + String.valueOf(LocalDate.now().getYear()).substring(2, 4) + "/" + "00000");
        dateID.setValue(LocalDate.now());
        startTimeID.setValue(LocalTime.parse(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))));
    }

    public RecordFormController(Record record) {
        loadView();

        LocalDate date = LocalDate.parse(record.getDate());
        codeID.setText("#" + String.valueOf(date.getYear()).substring(2, 4) + "/" +
                String.format("%05d", record.getCode()));
        dateID.setValue(date);
        startTimeID.setValue(LocalTime.parse(record.getStartTime()));
        endTimeID.setValue(LocalTime.parse(record.getEndTime()));
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

        addID.setText("Guardar");
    }

    private void init() {
        try {
            List<Assembly> assemblies = DAO.assemblyDao.queryBuilder().query();
            List<Applicant> applicants = DAO.applicantDao.queryBuilder().query();
            List<Resource> resources = DAO.resourceDao.queryBuilder().query();
            List<Area> areas = DAO.areaDao.queryBuilder().query();
            List<Service> services = DAO.servicesDao.queryBuilder().query();

            assemblyID.getItems().addAll(assemblies);
            applicantID.getItems().addAll(applicants);
            resourceID.getItems().addAll(resources);
            areaID.getItems().addAll(areas);
            serviceID.getItems().addAll(services);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        startTimeID.setIs24HourView(true);
        endTimeID.setIs24HourView(true);


        assistance_mID.textProperty().addListener(onlyNumbers(assistance_mID));
        assistance_hID.textProperty().addListener(onlyNumbers(assistance_hID));
        evacuated_mID.textProperty().addListener(onlyNumbers(evacuated_mID));
        evacuated_hID.textProperty().addListener(onlyNumbers(evacuated_hID));
        endTimeID.focusedProperty().addListener((ov, o, n) -> {
            if (n && endTimeID.getValue() == null)
                endTimeID.setValue(LocalTime.parse(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))));
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
                    notesID.getText(), 1);
            try {
                DAO.recordDao.create(record);
            } catch (SQLException e) {
                e.printStackTrace();
                record = null;
            }

            addID.setText("Guardar");
            LocalDate date = LocalDate.parse(record.getDate());
            codeID.setText("#" + String.valueOf(date.getYear()).substring(2, 4) + "/" +
                    String.format("%05d", record.getID_record()));
        } else {
            try {
                // TODO aplicar setters
                DAO.recordDao.update(record);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private ChangeListener<String> onlyNumbers(TextField node) {
        // TODO el valor minimo es 0
        return (observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                node.setText(newValue.replaceAll("[^\\d]", ""));
            }
        };
    }

}