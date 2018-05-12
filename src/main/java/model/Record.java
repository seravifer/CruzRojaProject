package model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.misc.BaseDaoEnabled;
import com.j256.ormlite.table.DatabaseTable;

import java.time.LocalTime;

@DatabaseTable(tableName = "Record")
public class Record extends BaseDaoEnabled<Record, Integer> {

    @DatabaseField(generatedId = true)
    private int ID_record;
    @DatabaseField
    private String date;
    @DatabaseField
    private int code;
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Resource resource;
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Assembly assembly;
    @DatabaseField
    private String startTime;
    @DatabaseField
    private String endTime;
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Area area;
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Applicant applicant;
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Service service;
    @DatabaseField
    private String address;
    @DatabaseField
    private int assistance_h;
    @DatabaseField
    private int assistance_m;
    @DatabaseField
    private int evacuated_h;
    @DatabaseField
    private int evacuated_m;
    @DatabaseField
    private String registry;
    @DatabaseField
    private String notes;
    @DatabaseField
    private int user_id;

    public Record() {}

    public Record(String date, int code, Resource resource, Assembly assembly, LocalTime startTime, LocalTime endTime,
                  Area area, Applicant applicant, Service service, String address, String assistance_h,
                  String assistance_m, String evacuated_h, String evacuated_m, String registry, String notes, int user_id) {
        this.date = date;
        this.code = code;
        this.resource = resource;
        this.assembly = assembly;
        this.startTime = startTime.toString();
        if (endTime == null) this.endTime = null;
        else this.endTime = endTime.toString();
        this.area = area;
        this.applicant = applicant;
        this.service = service;
        this.address = address;
        this.assistance_h = Integer.parseInt(assistance_h);
        this.assistance_m = Integer.parseInt(assistance_m);
        this.evacuated_h = Integer.parseInt(evacuated_h);
        this.evacuated_m = Integer.parseInt(evacuated_m);
        this.registry = registry;
        this.notes = notes;
        this.user_id = user_id;
    }

    public int getID_record() {
        return ID_record;
    }

    public String getDate() {
        return date;
    }

    public int getCode() {
        return code;
    }

    public Resource getResource() {
        return resource;
    }

    public Assembly getAssembly() {
        return assembly;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public Area getArea() {
        return area;
    }

    public Applicant getApplicant() {
        return applicant;
    }

    public Service getService() {
        return service;
    }

    public String getAddress() {
        return address;
    }

    public int getAssistance_h() {
        return assistance_h;
    }

    public int getAssistance_m() {
        return assistance_m;
    }

    public int getEvacuated_h() {
        return evacuated_h;
    }

    public int getEvacuated_m() {
        return evacuated_m;
    }

    public String getRegistry() {
        return registry;
    }

    public String getNotes() {
        return notes;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setID(int ID_record) {
        this.ID_record = ID_record;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public void setAssembly(Assembly assembly) {
        this.assembly = assembly;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalTime endTime) {
        if (endTime == null) this.endTime = null;
        else this.endTime = endTime.toString();
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public void setApplicant(Applicant applicant) {
        this.applicant = applicant;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setAssistance_h(String assistance_h) {
        this.assistance_h = Integer.parseInt(assistance_h);
    }

    public void setAssistance_m(String assistance_m) {
        this.assistance_m = Integer.parseInt(assistance_m);
    }

    public void setEvacuated_h(String evacuated_h) {
        this.evacuated_h = Integer.parseInt(evacuated_h);
    }

    public void setEvacuated_m(String evacuated_m) {
        this.evacuated_m = Integer.parseInt(evacuated_m);
    }

    public void setRegistry(String registry) {
        this.registry = registry;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Record record = (Record) o;

        return ID_record == record.ID_record;
    }

    @Override
    public int hashCode() {
        return ID_record;
    }
}
