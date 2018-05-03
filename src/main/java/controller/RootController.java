package controller;

import javafx.fxml.Initializable;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlite3.SQLitePlugin;

import java.net.URL;
import java.util.ResourceBundle;

public class RootController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Jdbi jdbi = Jdbi.create("jdbc:sqlite:database").installPlugin(new SQLitePlugin());
    }
}
