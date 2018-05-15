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

    @DatabaseField(foreign = true,foreignAutoRefresh = true)
    private Area area;

    public Service() {}

    public Service(String name, Area area) {
        this.name = name;
        this.area = area;
    }

    public int getID_service() {
        return ID_service;
    }

    public String getName() {
        return name;
    }

    public Area getArea() {
        return area;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setArea(Area area) {
        this.area = area;
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
