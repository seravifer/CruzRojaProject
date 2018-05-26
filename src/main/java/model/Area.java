package model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Objects;

@DatabaseTable(tableName = "Area")
public class Area {

    @DatabaseField(generatedId = true)
    private int ID_area;

    @DatabaseField(canBeNull = false, unique = true)
    private String name;

    @DatabaseField(canBeNull = false, unique = true)
    private String short_name;

    public Area() {}

    public Area(String name, String short_name) {
        this.name = name;
        this.short_name = short_name;
    }

    public int getID() {
        return ID_area;
    }

    public String getName() {
        return name;
    }

    public String getShortName() {
        return short_name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setShortName(String short_name) {
        this.short_name = short_name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Area area = (Area) o;
        return ID_area == area.ID_area;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID_area);
    }

    @Override
    public String toString() {
        return name + " (" + short_name + ")";
    }
}
