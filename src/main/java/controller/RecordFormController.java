/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author Elio Brennan
 */
public class RecordFormController implements Initializable {

    @FXML
    private JFXComboBox<?> resource;
    @FXML
    private JFXComboBox<?> assembly;
    @FXML
    private JFXTimePicker startTime;
    @FXML
    private JFXTimePicker endTime;
    @FXML
    private JFXComboBox<?> area;
    @FXML
    private JFXTextField applicant;
    @FXML
    private JFXComboBox<?> service;
    @FXML
    private JFXTextField address;
    @FXML
    private JFXTextField assistance_h;
    @FXML
    private JFXTextField assistance_m;
    @FXML
    private JFXTextField evacuated_h;
    @FXML
    private JFXTextField evacuated_m;
    @FXML
    private JFXTextField registry;
    @FXML
    private JFXTextField notes;
    @FXML
    private JFXCheckBox cb_stay;
    @FXML
    private Label code;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void save_registry(ActionEvent event) {
    }

    @FXML
    private void stay(ActionEvent event) {
    }
    
}
