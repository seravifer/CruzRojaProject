package controller;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Optional;

public class SuperController {

    private static SuperController instance = null;

    private Stage stage;

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
        stage.setMinWidth(1040);
        stage.setMinHeight(640);
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
