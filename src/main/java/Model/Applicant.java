package Model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Applicant")
public class Applicant {

    @DatabaseField(generatedId = true)
    private int ID_applicant;

    @DatabaseField(canBeNull = false, unique = true)
    private String name_applicant;

    public Applicant() {}

    public Applicant(String name_applicant) {
        this.name_applicant = name_applicant;
    }

    public int getID_applicant() {
        return ID_applicant;
    }

    public String getName_applicant() {
        return name_applicant;
    }

    public void setName_applicant(String name_applicant) {
        this.name_applicant = name_applicant;
    }
}
