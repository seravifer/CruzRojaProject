/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Sergio
 */
public class AdminController implements Initializable {

    @FXML
    private JFXTextField nombreServicios;
    @FXML
    private JFXTextField abreviaturaServicios;
    @FXML
    private JFXComboBox<?> areaServicios;
    @FXML
    private JFXButton botonServicios;
    @FXML
    private TableView<?> tablaServicios;
    @FXML
    private TableColumn<?, ?> subCodeColumID;
    @FXML
    private TableColumn<?, ?> transferColumID;
    @FXML
    private TableColumn<?, ?> startTimeAssistanceColumID;
    @FXML
    private JFXTextField codigoAsambleas;
    @FXML
    private JFXTextField nombreAsambleas;
    @FXML
    private JFXButton botonAsambleas;
    @FXML
    private TableView<?> tablaAsambleas;
    @FXML
    private TableColumn<?, ?> subCodeColumID1;
    @FXML
    private TableColumn<?, ?> transferColumID1;
    @FXML
    private JFXTextField codigoRecursos;
    @FXML
    private JFXTextField nombreRecursos;
    @FXML
    private JFXButton botonRecursos;
    @FXML
    private TableView<?> tablaRecursos;
    @FXML
    private TableColumn<?, ?> subCodeColumID11;
    @FXML
    private TableColumn<?, ?> transferColumID11;
    @FXML
    private JFXTextField nombreAreas;
    @FXML
    private JFXButton botonAreas;
    @FXML
    private TableView<?> tablaAreas;
    @FXML
    private TableColumn<?, ?> subCodeColumID12;
    @FXML
    private JFXTextField nombreSolicitantes;
    @FXML
    private JFXButton botonSolicitantes;
    @FXML
    private TableView<?> tablaSolicitantes;
    @FXML
    private TableColumn<?, ?> subCodeColumID121;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public AdminController(){
     try {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/view/admin.fxml"));     
        fxmlLoader.setController(this);
         
        Scene scene = new Scene(fxmlLoader.load(), 1200, 720);
        Stage stage = new Stage();
        scene.getStylesheets().setAll(getClass().getResource("/css/style.css").toExternalForm());

        stage.setTitle("Panel Administrador de Cruz Roja");
        stage.setScene(scene);
        stage.show();
    } catch (IOException e) {
        Logger logger = Logger.getLogger(getClass().getName());
        logger.log(Level.SEVERE, "Failed to create new Window.", e);
    }
    }
    
}
