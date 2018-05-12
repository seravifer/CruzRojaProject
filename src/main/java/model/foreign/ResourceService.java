package model.foreign;

import com.j256.ormlite.field.DatabaseField;
import model.Resource;
import model.Service;

public class ResourceService {

    @DatabaseField(generatedId = true)
    private int ID_resource_service;

    @DatabaseField(foreign = true)
    private Resource resource;

    @DatabaseField(foreign = true)
    private Service service;

    public ResourceService() {}

    public ResourceService(Resource resource, Service service) {
        this.resource = resource;
        this.service = service;
    }

}
