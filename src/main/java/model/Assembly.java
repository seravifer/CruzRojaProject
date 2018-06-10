package model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Objects;

@DatabaseTable(tableName = "Assembly")
public class Assembly {

    @DatabaseField(generatedId = true)
    private int ID_assembly;
    @DatabaseField(canBeNull = false, unique = true)
    private String code;
    @DatabaseField(canBeNull = false, unique = true)
    private String name;

    public Assembly() {
    }

    public Assembly(String name, String code) {
        this.code = code;
        this.name = name;
    }

    public int getID() {
        return ID_assembly;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Assembly assembly = (Assembly) o;
        return ID_assembly == assembly.ID_assembly;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID_assembly);
    }

    @Override
    public String toString() {
        return name + " (" + code + ")";
    }
}
