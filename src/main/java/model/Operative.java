package model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Operative")
public class Operative {

    @DatabaseField(generatedId = true)
    private String ID_operative;
    @DatabaseField
    private String name;
    @DatabaseField
    private String date;

    public Operative() {}

    public Operative(String name, String date) {
        this.name = name;
        this.date = date;
    }

    public String getID_operative() {
        return ID_operative;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
