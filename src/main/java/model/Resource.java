package model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Resource")
public class Resource {

    @DatabaseField(generatedId = true)
    private int ID_resource;

    @DatabaseField(canBeNull = false, unique = true)
    private String name;

    @DatabaseField(canBeNull = false, unique = true)
    private String code;

    public Resource() {}

    public Resource(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public int getID() {
        return ID_resource;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resource resource = (Resource) o;

        return ID_resource == resource.ID_resource;
    }

    @Override
    public int hashCode() {
        return ID_resource;
    }

    @Override
    public String toString() {
        return code;
    }
}
