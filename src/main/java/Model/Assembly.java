package Model;

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
    private String name_assembly;

    public Assembly() {}

    public Assembly(String name_assembly, String code) {
        this.code = code;
        this.name_assembly = name_assembly;
    }

    public int getID_assembly() {
        return ID_assembly;
    }

    public String getName_assembly() {
        return name_assembly;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setName_assembly(String name_assembly) {
        this.name_assembly = name_assembly;
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
        return name_assembly;
    }
}
