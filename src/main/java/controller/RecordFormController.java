package Controller;

import Model.*;
import Service.DAO;
import com.jfoenix.controls.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class RecordFormController {

    @FXML
    private Label codeID;

    @FXML
    private JFXComboBox<Resource> resourceID;

    @FXML
    private JFXComboBox<Assembly> assemblyID;

    @FXML
    private JFXTimePicker startTimeID;

    @FXML
    private JFXTimePicker endTimeID;

    @FXML
    private JFXComboBox<Area> areaID;

    @FXML
    private JFXComboBox<Applicant> applicantID;

    @FXML
    private JFXComboBox<Service> serviceID;

    @FXML
    private JFXTextField assistance_hID;

    @FXML
    private JFXTextField assistance_mID;

    @FXML
    private JFXTextField evacuated_hID;

    @FXML
    private JFXTextField evacuated_mID;

    @FXML
    private JFXTextField registryID;

    @FXML
    private JFXTextField notesID;

    @FXML
    private JFXDatePicker dateID;

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
        // TODO Editar record
        init();
    }

    private void init() {
        try {
            List<Assembly> assemblies = DAO.assemblyDao.queryBuilder().query();
            List<Applicant> applicants = DAO.applicantDao.queryBuilder().query();

            assemblyID.getItems().addAll(assemblies);
            applicantID.getItems().addAll(applicants);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        assemblyID.setCellFactory(cell -> new ListCell<Assembly>() {
            @Override
            protected void updateItem(Assembly item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) setGraphic(null);
                else setText(item.getName_assembly());
            }
        });

        applicantID.setCellFactory(cell -> new ListCell<Applicant>() {
            @Override
            protected void updateItem(Applicant item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) setGraphic(null);
                else setText(item.getName_applicant());
            }
        });

        // TODO para cada componente
    }

    
}