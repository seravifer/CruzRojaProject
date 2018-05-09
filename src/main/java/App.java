import controller.RecordFormController;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("Registro Cruz Roja");
        new RecordFormController(primaryStage);
    }

}
