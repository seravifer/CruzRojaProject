package model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Users")
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

    public int getID() {
        return ID_user;
    }

    public String getName() {
        return name_user;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
    public String getHide(){
        return "*****";
    }

    public void setName(String name_user) {
        this.name_user = name_user;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return ID_user == user.ID_user;
    }

    @Override
    public int hashCode() {
        return ID_user;
    }
}