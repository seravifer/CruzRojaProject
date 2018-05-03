package Model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.text.SimpleDateFormat;

@DatabaseTable(tableName = "Record")
public class Record {

    @DatabaseField(generatedId = true)
    private int ID_record;
    @DatabaseField()
    private int ID_parent;
    @DatabaseField()
    private SimpleDateFormat date;
    @DatabaseField()
    private int code;
    @DatabaseField()
    private int subcode;
    @DatabaseField()
    private int resource_id;
    @DatabaseField(canBeNull = false, foreign = true)
    private Assembly assembly;
    @DatabaseField()
    private SimpleDateFormat startTime;
    @DatabaseField()
    private SimpleDateFormat endTime;
    @DatabaseField()
    private int area;
    @DatabaseField()
    private int applicant;
    @DatabaseField()
    private int service;
    @DatabaseField()
    private String notes;
    @DatabaseField()
    private String registry;
    @DatabaseField()
    private boolean gender;
    @DatabaseField()
    private String placeTransfer;
    @DatabaseField()
    private SimpleDateFormat startTimeAssistance;
    @DatabaseField()
    private SimpleDateFormat transferTimeAssistance;
    @DatabaseField()
    private SimpleDateFormat endTimeAssistance;

    public Record() {}

    public Record(int ID_parent, SimpleDateFormat date, int code, int subcode, int resource_id, Assembly assembly,
                  SimpleDateFormat startTime, SimpleDateFormat endTime, int area, int applicant, int service,
                  String notes, String registry, boolean gender, String placeTransfer,
                  SimpleDateFormat startTimeAssistance, SimpleDateFormat transferTimeAssistance,
                  SimpleDateFormat endTimeAssistance) {
        this.ID_parent = ID_parent;
        this.date = date;
        this.code = code;
        this.subcode = subcode;
        this.resource_id = resource_id;
        this.assembly = assembly;
        this.startTime = startTime;
        this.endTime = endTime;
        this.area = area;
        this.applicant = applicant;
        this.service = service;
        this.notes = notes;
        this.registry = registry;
        this.gender = gender;
        this.placeTransfer = placeTransfer;
        this.startTimeAssistance = startTimeAssistance;
        this.transferTimeAssistance = transferTimeAssistance;
        this.endTimeAssistance = endTimeAssistance;
    }

    public int getID_record() {
        return ID_record;
    }

    public int getID_parent() {
        return ID_parent;
    }

    public SimpleDateFormat getDate() {
        return date;
    }

    public int getCode() {
        return code;
    }

    public int getSubcode() {
        return subcode;
    }

    public int getResource_id() {
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

    public String getNotes() {
        return notes;
    }

    public String getRegistry() {
        return registry;
    }

    public boolean isGender() {
        return gender;
    }

    public String getPlaceTransfer() {
        return placeTransfer;
    }

    public SimpleDateFormat getStartTimeAssistance() {
        return startTimeAssistance;
    }

    public SimpleDateFormat getTransferTimeAssistance() {
        return transferTimeAssistance;
    }

    public SimpleDateFormat getEndTimeAssistance() {
        return endTimeAssistance;
    }

    public void setID_record(int ID_record) {
        this.ID_record = ID_record;
    }

    public void setID_parent(int ID_parent) {
        this.ID_parent = ID_parent;
    }

    public void setDate(SimpleDateFormat date) {
        this.date = date;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setSubcode(int subcode) {
        this.subcode = subcode;
    }

    public void setResource_id(int resource_id) {
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

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setRegistry(String registry) {
        this.registry = registry;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public void setPlaceTransfer(String placeTransfer) {
        this.placeTransfer = placeTransfer;
    }

    public void setStartTimeAssistance(SimpleDateFormat startTimeAssistance) {
        this.startTimeAssistance = startTimeAssistance;
    }

    public void setTransferTimeAssistance(SimpleDateFormat transferTimeAssistance) {
        this.transferTimeAssistance = transferTimeAssistance;
    }

    public void setEndTimeAssistance(SimpleDateFormat endTimeAssistance) {
        this.endTimeAssistance = endTimeAssistance;
    }
}
