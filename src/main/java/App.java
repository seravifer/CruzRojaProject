import controller.LoginController;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.File;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        if (!new File("./db").exists()) {
            System.err.println("No existe la base de datos.");
        }
        new LoginController();
        // new RecordsController(null);
    }

}
