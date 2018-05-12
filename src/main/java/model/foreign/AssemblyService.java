package model.foreign;

import com.j256.ormlite.field.DatabaseField;
import model.Assembly;
import model.Service;

public class AssemblyService {

    @DatabaseField(generatedId = true)
    private int ID_assembly_service;

    @DatabaseField(foreign = true)
    private Assembly assembly;

    @DatabaseField(foreign = true)
    private Service service;

    public AssemblyService() {}

    public AssemblyService(Assembly assembly, Service service) {
        this.assembly = assembly;
        this.service = service;
    }

}
