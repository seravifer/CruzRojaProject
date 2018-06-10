package service;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

import java.io.IOException;
import java.sql.SQLException;

public class DB {

    private static DB instance = null;

    private ConnectionSource db;

    private DB() {
        try {
            db = new JdbcConnectionSource("jdbc:sqlite:db");
        } catch (SQLException e) {
            System.err.println("Se ha producido un error al acceder a la base de datos.");
            e.printStackTrace();
        }
    }

    public static ConnectionSource get() {
        if (instance == null) {
            instance = new DB();
        }
        return instance.getJdbi();
    }

    public static void close() {
        if (instance != null) {
            instance.stopJdbi();
        }
    }

    private ConnectionSource getJdbi() {
        return db;
    }

    private void stopJdbi() {
        try {
            db.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
