package controller.component;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class OperativeModal extends AnchorPane {

    public OperativeModal() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/component/operative.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        init();
    }

    private void init() {

    }
}
