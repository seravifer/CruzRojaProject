package model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Hospital")
public class Hospital {

    @DatabaseField(generatedId = true)
    private int ID_hospital;
    @DatabaseField(unique = true, canBeNull = false)
    private String code;
    @DatabaseField(unique = true, canBeNull = false)
    private String name;
    @DatabaseField
    private String assembly;

    public Hospital() {}

    public Hospital(String code, String name, String assembly) {
        this.code = code;
        this.name = name;
        this.assembly = assembly;
    }

    public int getID() {
        return ID_hospital;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getAssembly() {
        return assembly;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAssembly(String assembly) {
        this.assembly = assembly;
    }

}
