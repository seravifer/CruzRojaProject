package Model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Assembly")
public class Assembly {

    @DatabaseField(generatedId = true)
    private int ID_assembly;
    @DatabaseField(canBeNull = false, unique = true)
    private String name_assembly;

    public Assembly() {}

    public Assembly(String name_assembly) {
        this.name_assembly = name_assembly;
    }

    public int getID_assembly() {
        return ID_assembly;
    }

    public String getName_assembly() {
        return name_assembly;
    }

    public void setName_assembly(String name_assembly) {
        this.name_assembly = name_assembly;
    }
}
