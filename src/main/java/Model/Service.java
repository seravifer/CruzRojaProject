package Model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Service")
public class Service {

    @DatabaseField(generatedId = true)
    private int ID_service;

    @DatabaseField(canBeNull = false, unique = true)
    private String name;

    @DatabaseField(canBeNull = false, unique = true)
    private String short_name;

    @DatabaseField(canBeNull = false, unique = true)
    private int ID_area;

    public Service() {}

    public Service(String name, String short_name, int ID_area) {
        this.name = name;
        this.short_name = short_name;
        this.ID_area = ID_area;
    }

    public int getID_service() {
        return ID_service;
    }

    public String getName() {
        return name;
    }

    public String getShort_name() {
        return short_name;
    }

    public int getID_area() {
        return ID_area;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setShort_name(String short_name) {
        this.short_name = short_name;
    }

    public void setID_area(int ID_area) {
        this.ID_area = ID_area;
    }
}
