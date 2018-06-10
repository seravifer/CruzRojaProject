package controller;

import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class SuperController {

    private static SuperController instance = null;

    private Stage stage;
    private Parent home;
    private RecordsController controller;

    protected SuperController() {
        Group root = new Group();
        Scene scene = new Scene(root, 1200, 720);
        scene.getStylesheets().setAll(getClass().getResource("/css/style.css").toExternalForm());

        stage = new Stage();
        /*stage.setOnCloseRequest(event -> {
            Alert closeConfirmation = new Alert(Alert.AlertType.CONFIRMATION, "¿Estas seguro de que quieres salir?");
            Button exitButton = (Button) closeConfirmation.getDialogPane().lookupButton(ButtonType.OK);
            exitButton.setText("Salir");
            closeConfirmation.setHeaderText("Confirmar salida");
            closeConfirmation.initModality(Modality.APPLICATION_MODAL);

            Optional<ButtonType> closeResponse = closeConfirmation.showAndWait();
            if (!ButtonType.OK.equals(closeResponse.get())) {
                event.consume();
            }
        };*/
        stage.setScene(scene);
        stage.setMinWidth(1280);
        stage.setMinHeight(720);
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/img/icon.png")));
        stage.show();
    }

    public static SuperController getInstance() {
        if (instance == null) {
            instance = new SuperController();
        }
        return instance;
    }

    public void setPage(Parent page, String title) {
        stage.setTitle(title);
        stage.getScene().setRoot(page);
    }

    public void setHome(Parent home, RecordsController controller) {
        stage.getScene().setRoot(home);
        this.home = home;
        this.controller = controller;
    }

    public void goBack() {
        stage.getScene().setRoot(home);
        controller.refresh();
    }
}
