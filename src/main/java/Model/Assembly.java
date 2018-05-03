package Model;

public class Assembly {

    private int ID_assembly;
    private String name_assembly;

    public int getID_assembly() {
        return ID_assembly;
    }
    public String getName_assembly() {
        return name_assembly;
    }

    public void setID_assembly(int ID_assembly) {
        this.ID_assembly = ID_assembly;
    }
    public void setName_assembly(String name_assembly) {
        this.name_assembly = name_assembly;
    }

    public Assembly() {}
    public Assembly(int ID_assembly, String name_assembly) {
        this.ID_assembly = ID_assembly;
        this.name_assembly = name_assembly;
    }
}
