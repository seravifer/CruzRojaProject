package model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.misc.BaseDaoEnabled;
import com.j256.ormlite.table.DatabaseTable;
import utils.LocalTimeType;

import java.time.LocalTime;

@DatabaseTable(tableName = "Event")
public class Event extends BaseDaoEnabled<Event, Integer> {

    @DatabaseField(generatedId = true)
    private int ID_Event;
    @DatabaseField(foreign = true)
    private Record record;
    @DatabaseField
    private int subcode;
    @DatabaseField
    private String key;
    @DatabaseField(persisterClass = LocalTimeType.class)
    private LocalTime startTime;
    @DatabaseField(persisterClass = LocalTimeType.class)
    private LocalTime startAssistance;
    @DatabaseField(persisterClass = LocalTimeType.class)
    private LocalTime transferTime;
    @DatabaseField(persisterClass = LocalTimeType.class)
    private LocalTime endTime;
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Hospital hospital;
    @DatabaseField
    private String pathology;
    @DatabaseField
    private String registry;
    @DatabaseField
    private Integer gender;

    public Event() {
    }

    public Event(Record record, int subcode, String key, LocalTime startTime, LocalTime startAssistance, LocalTime transferTime,
                 LocalTime endTime, Hospital hospital, String pathology, String registry, Integer gender) {
        this.record = record;
        this.subcode = subcode;
        this.key = key;
        this.startTime = startTime;
        this.startAssistance = startAssistance;
        this.transferTime = transferTime;
        this.endTime = endTime;
        this.hospital = hospital;
        this.pathology = pathology;
        this.registry = registry;
        this.gender = gender;
    }

    public int getID_Event() {
        return ID_Event;
    }

    public Record getRecord() {
        return record;
    }

    public void setRecord(Record record) {
        this.record = record;
    }

    public int getSubcode() {
        return subcode;
    }

    public void setSubcode(int subcode) {
        this.subcode = subcode;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getStartAssistance() {
        return startAssistance;
    }

    public void setStartAssistance(LocalTime startAssistance) {
        this.startAssistance = startAssistance;
    }

    public LocalTime getTransferTime() {
        return transferTime;
    }

    public void setTransferTime(LocalTime transferTime) {
        this.transferTime = transferTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public Hospital getHospital() {
        return hospital;
    }

    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }

    public String getPathology() {
        return pathology;
    }

    public void setPathology(String pathology) {
        this.pathology = pathology;
    }

    public String getRegistry() {
        return registry;
    }

    public void setRegistry(String registry) {
        this.registry = registry;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

}
