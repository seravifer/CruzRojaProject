package Controller;

import Model.*;
import Service.DAO;
import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.SVGPath;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
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
    private JFXTextField notesID;

    @FXML
    private SVGPath closeID;

    private Record record;

    public RecordFormController() {
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

    public RecordFormController(Record record) {
        this();

        LocalDate date = LocalDate.parse(record.getDate());
        codeID.setText("#" + String.valueOf(date.getYear()).substring(2,4) + "/" +
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
            List<Service> services = DAO.servicesDao.queryBuilder().query();
            List<Area> areas = DAO.areaDao.queryBuilder().query();

            assemblyID.getItems().addAll(assemblies);
            applicantID.getItems().addAll(applicants);
            resourceID.getItems().addAll(resources);
            serviceID.getItems().addAll(services);
            areaID.getItems().addAll(areas);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        startTimeID.setIs24HourView(true);
        endTimeID.setIs24HourView(true);
    }

    @FXML
    void onClose(MouseEvent event) {
        new RecordsController();
    }

    @FXML
    void onSave(ActionEvent event) {
        if (record == null) {
            Record record = new Record(dateID.getValue().toString(), 0, resourceID.getValue(), assemblyID.getValue(),
                    startTimeID.getValue().toString(), endTimeID.getValue().toString(), areaID.getValue(),
                    applicantID.getValue(), serviceID.getValue(), "", assistance_hID.getText(),
                    assistance_hID.getText(), evacuated_hID.getText(), evacuated_mID.getText(), registryID.getText(),
                    notesID.getText(), 1);
            // DAO.recordDao.create(record);
            addID.setText("Guardar");
        } else {
            // TODO actualizar objeto
        }
    }
    
}