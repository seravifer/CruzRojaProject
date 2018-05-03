package Service;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlite3.SQLitePlugin;

public class DataBase {
    private static DataBase instance = null;

    private Jdbi jdbi;

    private DataBase() {
       jdbi = Jdbi.create("jdbc:sqlite:db").installPlugin(new SQLitePlugin());
    }

    public Jdbi getJdbi() {
        return jdbi;
    }

    public static DataBase getDB() {
        if(instance == null) {
            instance = new DataBase();
        }
        return instance;
    }
}
