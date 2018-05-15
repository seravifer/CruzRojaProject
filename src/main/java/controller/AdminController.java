/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.*;
import service.DAO;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * FXML controller class
 *
 * @author Sergio
 */
public class AdminController implements Initializable {
    @FXML
    private JFXTextField nombreServicios;

    @FXML
    private JFXTextField abreviaturaServicios;

    @FXML
    private JFXComboBox<Area> areaServicios;

    @FXML
    private JFXButton botonServicios;

    @FXML
    private TableView<Service> tablaServicios;

    @FXML
    private TableColumn<Service, String> nombreColumnServicios;

    @FXML
    private TableColumn<Service, String> abreviaturaColumnServicios;

    @FXML
    private TableColumn<Service, Area> areaColumnServicios;

    @FXML
    private JFXTextField codigoAsambleas;

    @FXML
    private JFXTextField nombreAsambleas;

    @FXML
    private JFXButton botonAsambleas;

    @FXML
    private TableView<Assembly> tablaAsambleas;

    @FXML
    private TableColumn<Assembly, String> codigoColumnAsamblea;

    @FXML
    private TableColumn<Assembly, String> nombreColumnAsamblea;

    @FXML
    private JFXTextField codigoRecursos;

    @FXML
    private JFXTextField nombreRecursos;

    @FXML
    private JFXButton botonRecursos;

    @FXML
    private TableView<Resource> tablaRecursos;

    @FXML
    private TableColumn<Resource, String> codigoColumnRecursos;

    @FXML
    private TableColumn<Resource, String> nombreColumnRecursos;

    @FXML
    private JFXTextField nombreAreas;

    @FXML
    private JFXButton botonAreas;

    @FXML
    private TableView<Area> tablaAreas;

    @FXML
    private TableColumn<Area, String> nombreColumnAreas;

    @FXML
    private JFXTextField nombreSolicitantes;

    @FXML
    private JFXButton botonSolicitantes;

    @FXML
    private TableView<Applicant> tablaSolicitantes;

    @FXML
    private TableColumn<Applicant, String> nombreColumnSolicitante;
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
        init();
    } catch (IOException e) {
        Logger logger = Logger.getLogger(getClass().getName());
        logger.log(Level.SEVERE, "Failed to create new Window.", e);
    }
    }
    
    public void init(){
       try{
        List<Area> areas = DAO.areaDao.queryBuilder().query();
        List<Service> servicios = DAO.servicesDao.queryBuilder().query();
        List<Assembly> asambleas = DAO.assemblyDao.queryBuilder().query();
        List<Resource> recursos = DAO.resourceDao.queryBuilder().query();
        List<Applicant> solicitantes = DAO.applicantDao.queryBuilder().query();
        
        areaServicios.getItems().addAll(areas);
        tablaServicios.getItems().addAll(servicios);
        tablaAreas.getItems().addAll(areas);
        tablaAsambleas.getItems().addAll(asambleas);
        tablaSolicitantes.getItems().addAll(solicitantes);
       
        tablaRecursos.getItems().addAll(recursos);
        
        nombreColumnServicios.setCellValueFactory(new PropertyValueFactory<>("name"));
        abreviaturaColumnServicios.setCellValueFactory(new PropertyValueFactory<>("short_name"));
        areaColumnServicios.setCellValueFactory(new PropertyValueFactory<Service,Area>("area"));

        nombreColumnAreas.setCellValueFactory(new PropertyValueFactory<>("name"));
        
        nombreColumnAsamblea.setCellValueFactory(new PropertyValueFactory<>("name_assembly"));
        codigoColumnAsamblea.setCellValueFactory(new PropertyValueFactory<>("code"));
        
        nombreColumnSolicitante.setCellValueFactory(new PropertyValueFactory<>("name_applicant"));
        
        nombreColumnRecursos.setCellValueFactory(new PropertyValueFactory<>("name_resource"));
        codigoColumnRecursos.setCellValueFactory(new PropertyValueFactory<>("code_resource"));
        
        areaColumnServicios.setCellFactory(cell -> new TableCell<Service, Area>() {
                 @Override
            public void updateItem(Area item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) setText(null);
                else setText(item.getName() + "");
            }
        });
       
        botonServicios.setOnAction( (event) -> {
            try{
                    /*Service serv = new Service(nombreServicios.getText(), abreviaturaServicios.getText(), areaServicios.getValue());
                    DAO.servicesDao.create(serv);
                    tablaServicios.getItems().add(serv);*/
            }catch (Exception e){
                e.printStackTrace();
            }
        });
        botonAreas.setOnAction( (event) -> {
            try{
                    /*Area area = new Area(nombreAreas.getText());
                    DAO.areaDao.create(area);
                    tablaAreas.getItems().add(area);*/
            }catch (Exception e){
                e.printStackTrace();
            }
        });
        botonAsambleas.setOnAction( (event) -> {
            try{
                    Assembly asamblea = new Assembly(nombreAsambleas.getText(), codigoAsambleas.getText());
                    DAO.assemblyDao.create(asamblea);
                    tablaAsambleas.getItems().add(asamblea);
            }catch (Exception e){
                e.printStackTrace();
            }
        });
        botonRecursos.setOnAction( (event) -> {
            try{
                    Resource recurso = new Resource(nombreRecursos.getText(), codigoRecursos.getText());
                    DAO.resourceDao.create(recurso);
                    tablaRecursos.getItems().add(recurso);
            }catch (Exception e){
                e.printStackTrace();
            }
        });
        botonSolicitantes.setOnAction( (event) -> {
            try{
                    Applicant solicitante = new Applicant(nombreSolicitantes.getText());
                    DAO.applicantDao.create(solicitante);
                    tablaSolicitantes.getItems().add(solicitante);
            }catch (Exception e){
                e.printStackTrace();
            }
        });
        
       } catch (Exception e){
           e.printStackTrace();
       } 
    }
    
    
    
}
