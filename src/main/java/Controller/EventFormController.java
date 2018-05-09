/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Elio
 */
public class EventFormController implements Initializable {

    @FXML
    private JFXTextField id_parent;
    @FXML
    private JFXTextField subcode;
    @FXML
    private JFXTimePicker startTimeAssistance;
    @FXML
    private JFXTimePicker transferTimeAssistance;
    @FXML
    private JFXTextField placeTransfer;
    @FXML
    private JFXTextField keyTransfer;
    @FXML
    private JFXTimePicker endTimeAssistance;
    
    private Stage stage;
    
    public EventFormController(Stage stage) {
        this.stage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/eventForm.fxml"));
        fxmlLoader.setController(this);
        Parent parent = null;
        try {
            parent = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(parent);
        stage.setScene(scene);
    }
    /**
     * Initializes the Controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void save_event(ActionEvent event) {
    }
    
}