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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import model.Area;
import model.User;
import service.DAO;
import static service.DAO.users;
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

    }

    @FXML
    void loginAction(ActionEvent event) throws NoSuchAlgorithmException, InvalidKeySpecException, Exception {
        String usuario = user.getText();
        String pass = Utils.encrypt(password.getText());
        try {
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
        }  
    }

}
