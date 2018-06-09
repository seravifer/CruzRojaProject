package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import model.Area;
import model.User;
import service.DAO;
import static service.DAO.users;

import utils.Security;
import utils.Utils;

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
            if (KeyCode.ENTER == event.getCode()) {
                try {
                    login();
                } catch (Exception ex) {
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

    }

    @FXML
    void loginAction(ActionEvent event) throws NoSuchAlgorithmException, InvalidKeySpecException, Exception {
        login();
    }

    public void login() throws Exception {

        new Thread() {
            public void run() {

                try {
                    String usuario = user.getText();
                    String pass = Security.encrypt(password.getText());
                    User u = DAO.users.queryBuilder().where().eq("username", usuario).queryForFirst();
                    if (u == null) {
                        textAlert.setText("Usuario incorrecto o no registrado.");
                    } else if (!pass.equals(u.getPassword())) {
                        textAlert.setText("Contraseña incorrecta,vuelve a intentarlo.");
                    } else {
                        new RecordsController(u);
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (Exception ex) {
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }.start();
    }

}
