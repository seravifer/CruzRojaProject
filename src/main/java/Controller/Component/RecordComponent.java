package Controller.Component;

import Controller.RecordFormController;
import Model.Record;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.SVGPath;

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

    private Record record;

    public RecordComponent(Record record) {
        this.record = record;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/component/record.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            System.err.println("Error al cargar la vista: " + this.getClass().getSimpleName());
        }

        init();
    }

    private void init() {
        codeID.setText("#18/" + String.format("%05d", record.getCode()));
        assemblyID.setText(record.getAssembly().getName_assembly());
        resourceID.setText(record.getResource().getName_resource());
        dateID.setText(record.getDate());
        startTimeID.setText(record.getDate());
        endTimeID.setText(record.getEndTime());
        areaID.setText(record.getArea().getName());
        serviceID.setText(record.getService().getName());
        assistance_hID.setText(record.getAssistance_h() + "");
        evacuated_hID.setText(record.getEvacuated_h() + "");
        assistance_mID.setText(record.getAssistance_m() + "");
        evacuated_mID.setText(record.getEvacuated_m() + "");
        registryID.setText(record.getRegistry());
        notesID.setText(record.getNotes());
        editID.setOnMouseClicked((event) -> new RecordFormController());
    }
}
