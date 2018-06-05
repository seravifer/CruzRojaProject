import controller.LoginController;
import controller.RecordsController;
import javafx.application.Application;
import javafx.stage.Stage;
import javax.security.auth.login.LoginContext;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        new LoginController();
    }

}
