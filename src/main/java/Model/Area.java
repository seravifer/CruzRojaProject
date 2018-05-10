package Model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Objects;

@DatabaseTable(tableName = "Area")
public class Area {

    @DatabaseField(generatedId = true)
    private int ID_area;

    @DatabaseField(canBeNull = false, unique = true)
    private String name;

    public Area() {}

    public Area(String name) {
        this.name = name;
    }

    public int getID_area() {
        return ID_area;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        return name;
    }
}
