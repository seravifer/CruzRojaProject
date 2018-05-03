package Model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

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
}
