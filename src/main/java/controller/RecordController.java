package controller;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RecordController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public RecordController(Stage stage, int ID) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/record.fxml"));
        fxmlLoader.setController(this);
        Parent parent = fxmlLoader.load();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
    }
}