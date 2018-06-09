package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.User;
import service.DAO;
import utils.Security;

import java.io.IOException;

public class LoginController extends AnchorPane {

    @FXML
    private JFXTextField user;

    @FXML
    private JFXPasswordField password;

    @FXML
    private JFXButton login;

    @FXML
    private Text textAlert;

    @FXML
    private VBox principalPane;

    public LoginController() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/login.fxml"));
            fxmlLoader.setController(this);
            Parent parent = fxmlLoader.load();
            SuperController.getInstance().setPage(parent, "Login");
        } catch (IOException e) {
            e.printStackTrace();
        }

        init();
    }

    private void init() {
        principalPane.setOnKeyPressed((event) -> {
            if (KeyCode.ENTER == event.getCode()) login();
        });
        // DEBUG
        user.setText("root");
        password.setText("root");
    }

    @FXML
    void loginAction(ActionEvent event) {
        login();
    }

    private void login() {
        new Thread(() -> {
            try {
                String usuario = user.getText();
                String pass = Security.encrypt(password.getText());
                User user = DAO.users.queryBuilder().where().eq("username", usuario).queryForFirst();
                if (user == null) {
                    textAlert.setText("Usuario incorrecto o no registrado.");
                } else if (!pass.equals(user.getPassword())) {
                    textAlert.setText("Contraseña incorrecta,vuelve a intentarlo.");
                } else {
                    Platform.runLater(() -> new RecordsController(user));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

}
