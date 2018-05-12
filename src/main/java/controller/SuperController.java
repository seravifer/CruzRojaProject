package controller;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SuperController {
    private Stage stage;

    private static SuperController instance = null;

    protected SuperController() {
        Group root = new Group();
        Scene scene = new Scene(root, 1200, 720);

        stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Registro Cruz Roja");
        stage.show();
    }

    public static Stage getInstance() {
        if(instance == null) {
            instance = new SuperController();
        }
        return instance.stage;
    }
}
