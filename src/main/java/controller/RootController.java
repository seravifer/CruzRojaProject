package controller;

import Model.User;
import javafx.fxml.Initializable;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlite3.SQLitePlugin;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class RootController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Jdbi jdbi = Jdbi.create("jdbc:sqlite:database").installPlugin(new SQLitePlugin());

        List<User> users = jdbi.withHandle(handle -> {
            handle.execute("CREATE TABLE user (id INTEGER PRIMARY KEY, name VARCHAR)");

            // Inline positional parameters
            handle.execute("INSERT INTO user(id, name) VALUES (?, ?)", 0, "Alice");

            // Positional parameters
            handle.createUpdate("INSERT INTO user(id, name) VALUES (?, ?)")
                    .bind(0, 1) // 0-based parameter indexes
                    .bind(1, "Bob")
                    .execute();

            // Named parameters
            handle.createUpdate("INSERT INTO user(id, name) VALUES (:id, :name)")
                    .bind("id", 2)
                    .bind("name", "Clarice")
                    .execute();

            // Named parameters from bean properties
            handle.createUpdate("INSERT INTO user(id, name) VALUES (:id, :name)")
                    .bindBean(new User(3, "David"))
                    .execute();

            // Easy mapping to any type
            return handle.createQuery("SELECT * FROM user ORDER BY name")
                    .mapToBean(User.class)
                    .list();
        });

    }
}
