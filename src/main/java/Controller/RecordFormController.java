package Controller;

import Model.Record;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;

import java.io.IOException;

public class RecordFormController {

    @FXML
    private JFXComboBox<?> resourceID;

    @FXML
    private JFXComboBox<?> assemblyID;

    @FXML
    private JFXTimePicker startTimeID;

    @FXML
    private JFXTimePicker endTimeID;

    @FXML
    private JFXComboBox<?> areaID;

    @FXML
    private JFXTextField applicantID;

    @FXML
    private JFXComboBox<?> serviceID;

    @FXML
    private JFXTextField addressID;

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
    private JFXCheckBox cb_stayID;

    @FXML
    private Label codeID;

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
    }

    public RecordFormController(Record record) {
        // TODO Editar record
    }

    
}