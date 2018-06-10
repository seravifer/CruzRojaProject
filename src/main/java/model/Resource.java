package model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Objects;

@DatabaseTable(tableName = "Resource")
public class Resource {

    @DatabaseField(generatedId = true)
    private int ID_resource;

    @DatabaseField(canBeNull = false, unique = true)
    private String name;

    @DatabaseField(canBeNull = false, unique = true)
    private String code;
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Assembly assembly;

    public Resource() {
    }

    public Resource(String name, String code, Assembly assembly) {
        this.name = name;
        this.code = code;
        this.assembly = assembly;
    }

    public int getID() {
        return ID_resource;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Assembly getAssembly() {
        return assembly;
    }

    public void setAssembly(Assembly assembly) {
        this.assembly = assembly;
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
        return Objects.hash(ID_resource);
    }

    @Override
    public String toString() {
        return code;
    }
}
