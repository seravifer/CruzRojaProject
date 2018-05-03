package Model;

public class Service {
    private int ID_service;
    private String name;
    private String short_name;
    private int ID_area;

    public Service() {}

    public Service(int ID_service, String name, String short_name, int ID_area) {
        this.ID_service = ID_service;
        this.name = name;
        this.short_name = short_name;
        this.ID_area = ID_area;
    }

    public int getID_service() {
        return ID_service;
    }

    public String getName() {
        return name;
    }

    public String getShort_name() {
        return short_name;
    }

    public int getID_area() {
        return ID_area;
    }

    public void setID_service(int ID_service) {
        this.ID_service = ID_service;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setShort_name(String short_name) {
        this.short_name = short_name;
    }

    public void setID_area(int ID_area) {
        this.ID_area = ID_area;
    }
}
