package model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Objects;

@DatabaseTable(tableName = "Operative")
public class Operative {

    @DatabaseField(generatedId = true)
    private Integer ID_operative;
    @DatabaseField
    private String name;
    @DatabaseField
    private String date;

    public Operative() {
    }

    public Operative(String name, String date) {
        this.name = name;
        this.date = date;
    }

    public Integer getID() {
        return ID_operative;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Operative operative = (Operative) o;
        return ID_operative == operative.ID_operative;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID_operative);
    }

    @Override
    public String toString() {
        return name + " - " + date;
    }
}
