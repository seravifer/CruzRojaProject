package model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.misc.BaseDaoEnabled;
import com.j256.ormlite.table.DatabaseTable;
import utils.LocalTimeType;
import utils.Utils;

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
    private LocalTime startTimeAssistance;
    @DatabaseField(persisterClass = LocalTimeType.class)
    private LocalTime transferTimeAssistance;
    @DatabaseField(persisterClass = LocalTimeType.class)
    private LocalTime endTimeAssistance;
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Hospital hospital;
    @DatabaseField
    private String pathology;
    @DatabaseField
    private String registry;
    @DatabaseField
    private Integer gender;

    public Event() {}

    public Event(Record record, int subcode, String key, LocalTime startTimeAssistance, LocalTime transferTimeAssistance,
                 LocalTime endTimeAssistance, Hospital hospital, String pathology, String registry, Integer gender) {
        this.record = record;
        this.subcode = subcode;
        this.key = key;
        this.startTimeAssistance = startTimeAssistance;
        this.transferTimeAssistance = transferTimeAssistance;
        this.endTimeAssistance = endTimeAssistance;
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

    public int getSubcode() {
        return subcode;
    }

    public String getKey() {
        return key;
    }

    public LocalTime getStartTimeAssistance() {
        return startTimeAssistance;
    }

    public LocalTime getTransferTimeAssistance() {
        return transferTimeAssistance;
    }

    public LocalTime getEndTimeAssistance() {
        return endTimeAssistance;
    }

    public Hospital getHospital() {
        return hospital;
    }

    public String getPathology() {
        return pathology;
    }

    public String getRegistry() {
        return registry;
    }

    public Integer getGender() {
        return gender;
    }

    public void setRecord(Record record) {
        this.record = record;
    }

    public void setSubcode(int subcode) {
        this.subcode = subcode;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setStartTimeAssistance(LocalTime startTimeAssistance) {
        this.startTimeAssistance = startTimeAssistance;
    }

    public void setTransferTimeAssistance(LocalTime transferTimeAssistance) {
        this.transferTimeAssistance = transferTimeAssistance;
    }

    public void setEndTimeAssistance(LocalTime endTimeAssistance) {
        this.endTimeAssistance = endTimeAssistance;
    }

    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }

    public void setPathology(String pathology) {
        this.pathology = pathology;
    }

    public void setRegistry(String registry) {
        this.registry = registry;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

}
