package Model;

import java.text.SimpleDateFormat;

public class Record {

    private int ID_record;
    private int ID_parent;
    private SimpleDateFormat date;
    private int code;
    private int subcode;
    private int resource_id;
    private String assembly;
    private SimpleDateFormat startTime;
    private SimpleDateFormat endTime;
    private int area;
    private int applicant;
    private int service;
    private String notes;
    private String registry;
    private boolean gender;
    private String placeTransfer;
    private SimpleDateFormat startTimeAssistance;
    private SimpleDateFormat transferTimeAssistance;
    private SimpleDateFormat endTimeAssistance;

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
    public String getAssembly() {
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
    public void setAssembly(String assembly) {
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

    public Record() {}

    public Record(int ID_record, int ID_parent, SimpleDateFormat date, int code, int subcode,
                  int resource_id, String assembly, SimpleDateFormat startTime, SimpleDateFormat endTime,
                  int area, int applicant, int service, String notes, String registry, boolean gender,
                  String placeTransfer, SimpleDateFormat startTimeAssistance, SimpleDateFormat transferTimeAssistance,
                  SimpleDateFormat endTimeAssistance) {
        this.ID_record = ID_record;
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
}
