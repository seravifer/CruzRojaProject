package Model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Record")
public class User {

    @DatabaseField(generatedId = true)
    private int ID_user;
    @DatabaseField
    private String name_user;
    @DatabaseField
    private String username;
    @DatabaseField
    private String password;

    public User() {}

    public User(String name_user, String username, String password) {
        this.name_user = name_user;
        this.username = username;
        this.password = password;
    }

    public int getID_user() {
        return ID_user;
    }

    public String getName_user() {
        return name_user;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setID_user(int ID_user) {
        this.ID_user = ID_user;
    }

    public void setName_user(String name_user) {
        this.name_user = name_user;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}