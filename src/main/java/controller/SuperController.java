package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class SuperController {

    @FXML
    private BorderPane rootID;

    protected SuperController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/root.fxml"));
        fxmlLoader.setController(this);
        Parent parent = null;
        try {
            parent = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    private static SuperController instance = null;

    public static SuperController get() {
        if(instance == null) {
            instance = new SuperController();
        }
        return instance;
    }

    public void setContent(Node pane) {
        rootID.setCenter(pane);
    }

}
