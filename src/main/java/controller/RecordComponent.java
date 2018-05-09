package controller;

import Model.Record;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class RecordComponent extends AnchorPane {

    @FXML
    private Label codeID;

    @FXML
    private Label dateID;

    @FXML
    private Label resourceID;

    @FXML
    private Label assemblyID;

    @FXML
    private Label startTimeID;

    @FXML
    private Label endTimeID;

    @FXML
    private Label areaID;

    @FXML
    private Label serviceID;

    @FXML
    private Label assistance_hID;

    @FXML
    private Label evacuated_hID;

    @FXML
    private Label assistance_mID;

    @FXML
    private Label evacuated_mID;

    @FXML
    private Label registryID;

    @FXML
    private Label notesID;

    @FXML
    private SVGPath editID;

    public RecordComponent(Record record) throws SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/record.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            System.err.println("Error al cargar la vista: " + this.getClass().getSimpleName());
        }

        codeID.setText(String.valueOf(record.getCode()));
        assemblyID.setText(record.getAssembly().getName_assembly());
        // TODO Completar con todas

        //editID.setOnMouseClicked(() -> new RecordFormController());
    }
}
