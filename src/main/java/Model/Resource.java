package Model;

public class Resource {
    private int ID_resource;
    private String name_resource;
    private String code_resource;

    public Resource() {}

    public Resource(int ID_resource, String name_resource, String code_resource) {
        this.ID_resource = ID_resource;
        this.name_resource = name_resource;
        this.code_resource = code_resource;
    }

    public int getID_resource() {
        return ID_resource;
    }

    public String getName_resource() {
        return name_resource;
    }

    public String getCode_resource() {
        return code_resource;
    }

    public void setID_resource(int ID_resource) {
        this.ID_resource = ID_resource;
    }

    public void setName_resource(String name_resource) {
        this.name_resource = name_resource;
    }

    public void setCode_resource(String code_resource) {
        this.code_resource = code_resource;
    }
}
