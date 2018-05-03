package Model;

public class Area {
    private int ID_area;
    private String name;

    public Area() {}

    public Area(int ID_area, String name) {
        this.ID_area = ID_area;
        this.name = name;
    }

    public int getID_area() {
        return ID_area;
    }

    public String getName() {
        return name;
    }

    public void setID_area(int ID_area) {
        this.ID_area = ID_area;
    }

    public void setName(String name) {
        this.name = name;
    }
}
