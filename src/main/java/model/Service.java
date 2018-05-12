package model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Objects;

@DatabaseTable(tableName = "service")
public class Service {

    @DatabaseField(generatedId = true)
    private int ID_service;

    @DatabaseField(canBeNull = false, unique = true)
    private String name;

    @DatabaseField(canBeNull = false, unique = true)
    private String short_name;

    @DatabaseField(canBeNull = false, unique = true)
    private int area_id;

    public Service() {}

    public Service(String name, String short_name, int area_id) {
        this.name = name;
        this.short_name = short_name;
        this.area_id = area_id;
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
        return area_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setShort_name(String short_name) {
        this.short_name = short_name;
    }

    public void setID_area(int area_id) {
        this.area_id = area_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Service service = (Service) o;
        return ID_service == service.ID_service;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID_service);
    }

    @Override
    public String toString() {
        return name;
    }
}
