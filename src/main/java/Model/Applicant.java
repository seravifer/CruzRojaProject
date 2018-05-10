package Model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Applicant applicant = (Applicant) o;
        return ID_applicant == applicant.ID_applicant;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID_applicant);
    }

    @Override
    public String toString() {
        return name_applicant;
    }
}
