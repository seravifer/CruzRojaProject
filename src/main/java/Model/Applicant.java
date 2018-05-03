package Model;

public class Applicant {
    private int ID_applicant;
    private String name_applicant;

    public Applicant() {}

    public Applicant(int ID_applicant, String name_applicant) {
        this.ID_applicant = ID_applicant;
        this.name_applicant = name_applicant;
    }

    public int getID_applicant() {
        return ID_applicant;
    }

    public String getName_applicant() {
        return name_applicant;
    }

    public void setID_applicant(int ID_applicant) {
        this.ID_applicant = ID_applicant;
    }

    public void setName_applicant(String name_applicant) {
        this.name_applicant = name_applicant;
    }
}
