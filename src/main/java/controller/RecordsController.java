package controller;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RecordsController implements Initializable {

    @FXML
    private VBox recordsID;

    @FXML
    private JFXButton addID;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public RecordsController(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/records.fxml"));
        fxmlLoader.setController(this);
        Parent parent = fxmlLoader.load();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();

        recordsID.getChildren().add(0, new RecordComponent());
        addID.setOnAction((e) -> {
            new RecordFormController(stage);
        });
    }
}