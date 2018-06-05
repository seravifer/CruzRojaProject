package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import model.Area;
import model.User;
import service.DAO;
import static service.DAO.users;

public class LoginController extends AnchorPane{

    @FXML
    private JFXTextField user;

    @FXML
    private JFXPasswordField password;

    @FXML
    private JFXButton login;
    
    public LoginController() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/login.fxml"));
            fxmlLoader.setController(this);
            Parent parent = fxmlLoader.load();
            SuperController.getInstance().setPage(parent,"Login");
        } catch (IOException e) {
            e.printStackTrace();
        }

        init();
    }
    
    private void init(){
      
        
    }
    @FXML
    void loginAction(ActionEvent event) {
       String usuario = user.getText();
       String pass = password.getText();
       
       try{
       DAO.users.queryBuilder().where().eq("username", usuario).query();

       }catch (SQLException e) {
            e.printStackTrace();
        }

    }
    
    

}
