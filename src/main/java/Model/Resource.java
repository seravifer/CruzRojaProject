package Model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Resource")
public class Resource {

    @DatabaseField(generatedId = true)
    private int ID_resource;

    @DatabaseField(canBeNull = false, unique = true)
    private String name_resource;

    @DatabaseField(canBeNull = false, unique = true)
    private String code_resource;

    public Resource() {}

    public Resource(String name_resource, String code_resource) {
        this.name_resource = name_resource;
        this.code_resource = code_resource;
    }

    public int getID_resource() {
        return ID_resource;
    }

    public String getName_resource() {
        return name_resource;
    }

    public String getCode_resource() {
        return code_resource;
    }

    public void setName_resource(String name_resource) {
        this.name_resource = name_resource;
    }

    public void setCode_resource(String code_resource) {
        this.code_resource = code_resource;
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
        return name_resource;
    }
}
