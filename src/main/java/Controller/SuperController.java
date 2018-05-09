package Controller;

import javafx.stage.Stage;

public class SuperController {
    private Stage stage;

    private static SuperController instance = null;

    protected SuperController() {
        stage = new Stage();
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
