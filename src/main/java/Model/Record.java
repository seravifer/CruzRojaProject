package Model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.text.SimpleDateFormat;

@DatabaseTable(tableName = "Record")
public class Record {

    @DatabaseField(generatedId = true)
    private int ID_record;
    @DatabaseField
    private SimpleDateFormat date;
    @DatabaseField
    private int code;
    @DatabaseField(foreign = true)
    private Resource resource_id;
    @DatabaseField(canBeNull = false, foreign = true)
    private Assembly assembly;
    @DatabaseField
    private SimpleDateFormat startTime;
    @DatabaseField
    private SimpleDateFormat endTime;
    @DatabaseField
    private int area;
    @DatabaseField
    private int applicant;
    @DatabaseField
    private int service;
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

    public Record() {}

    public Record(SimpleDateFormat date, int code, Resource resource_id, Assembly assembly, SimpleDateFormat startTime,
                  SimpleDateFormat endTime, int area, int applicant, int service, String address, int assistance_h,
                  int assistance_m, int evacuated_h, int evacuated_m, String registry, String notes) {
        this.date = date;
        this.code = code;
        this.resource_id = resource_id;
        this.assembly = assembly;
        this.startTime = startTime;
        this.endTime = endTime;
        this.area = area;
        this.applicant = applicant;
        this.service = service;
        this.address = address;
        this.assistance_h = assistance_h;
        this.assistance_m = assistance_m;
        this.evacuated_h = evacuated_h;
        this.evacuated_m = evacuated_m;
        this.registry = registry;
        this.notes = notes;
    }

    public int getID_record() {
        return ID_record;
    }

    public SimpleDateFormat getDate() {
        return date;
    }

    public int getCode() {
        return code;
    }

    public Resource getResource_id() {
        return resource_id;
    }

    public Assembly getAssembly() {
        return assembly;
    }

    public SimpleDateFormat getStartTime() {
        return startTime;
    }

    public SimpleDateFormat getEndTime() {
        return endTime;
    }

    public int getArea() {
        return area;
    }

    public int getApplicant() {
        return applicant;
    }

    public int getService() {
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

    public void setDate(SimpleDateFormat date) {
        this.date = date;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setResource_id(Resource resource_id) {
        this.resource_id = resource_id;
    }

    public void setAssembly(Assembly assembly) {
        this.assembly = assembly;
    }

    public void setStartTime(SimpleDateFormat startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(SimpleDateFormat endTime) {
        this.endTime = endTime;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public void setApplicant(int applicant) {
        this.applicant = applicant;
    }

    public void setService(int service) {
        this.service = service;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setAssistance_h(int assistance_h) {
        this.assistance_h = assistance_h;
    }

    public void setAssistance_m(int assistance_m) {
        this.assistance_m = assistance_m;
    }

    public void setEvacuated_h(int evacuated_h) {
        this.evacuated_h = evacuated_h;
    }

    public void setEvacuated_m(int evacuated_m) {
        this.evacuated_m = evacuated_m;
    }

    public void setRegistry(String registry) {
        this.registry = registry;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
