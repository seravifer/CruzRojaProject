package model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.misc.BaseDaoEnabled;
import com.j256.ormlite.table.DatabaseTable;
import javafx.beans.property.SimpleStringProperty;
import utils.StringPropertyType;

import java.time.LocalTime;

import static utils.Utils.nullToString;

@DatabaseTable(tableName = "Event")
public class Event extends BaseDaoEnabled<Event, Integer> {

    @DatabaseField(generatedId = true)
    private int ID_Event;
    @DatabaseField(foreign = true)
    private Record record;
    @DatabaseField
    private int subcode;
    @DatabaseField(persisterClass = StringPropertyType.class)
    private SimpleStringProperty startTimeAssistance;
    @DatabaseField(persisterClass = StringPropertyType.class,  defaultValue = "")
    private SimpleStringProperty transferTimeAssistance;
    @DatabaseField(persisterClass = StringPropertyType.class)
    private SimpleStringProperty endTimeAssistance;
    @DatabaseField(persisterClass = StringPropertyType.class)
    private SimpleStringProperty placeTransfer;
    @DatabaseField(persisterClass = StringPropertyType.class)
    private SimpleStringProperty pathology;
    @DatabaseField(persisterClass = StringPropertyType.class)
    private SimpleStringProperty registry;

    public Event() {}

    public Event(Record record, int subcode, LocalTime startTimeAssistance, LocalTime transferTimeAssistance, String placeTransfer,
                 LocalTime endTimeAssistance, String pathology, String registry) {
        this.record = record;
        this.subcode = subcode;
        this.startTimeAssistance = new SimpleStringProperty(nullToString(startTimeAssistance));
        this.transferTimeAssistance = new SimpleStringProperty(nullToString(transferTimeAssistance));
        this.placeTransfer = new SimpleStringProperty(nullToString(placeTransfer));
        this.endTimeAssistance = new SimpleStringProperty(nullToString(endTimeAssistance));
        this.pathology = new SimpleStringProperty(pathology);
        this.registry = new SimpleStringProperty(registry);
    }

    public int getID_Event() {
        return ID_Event;
    }

    public Record getRecord() {
        return record;
    }

    public String getSubcode() {
        return Integer.toString(subcode);
    }

    public String getStartTimeAssistance() {
        return startTimeAssistance.get();
    }

    public SimpleStringProperty startTimeAssistanceProperty() {
        return startTimeAssistance;
    }

    public String getTransferTimeAssistance() {
        return transferTimeAssistance.get();
    }

    public SimpleStringProperty transferTimeAssistanceProperty() {
        return transferTimeAssistance;
    }

    public String getEndTimeAssistance() {
        return endTimeAssistance.get();
    }

    public SimpleStringProperty endTimeAssistanceProperty() {
        return endTimeAssistance;
    }

    public String getPlaceTransfer() {
        return placeTransfer.get();
    }

    public SimpleStringProperty placeTransferProperty() {
        return placeTransfer;
    }

    public String getPathology() {
        return pathology.get();
    }

    public SimpleStringProperty pathologyProperty() {
        return pathology;
    }

    public String getRegistry() {
        return registry.get();
    }

    public SimpleStringProperty registryProperty() {
        return registry;
    }

    public void setID_Event(int ID_Event) {
        this.ID_Event = ID_Event;
    }

    public void setRecord(Record record) {
        this.record = record;
    }

    public void setSubcode(int subcode) {
        this.subcode = subcode;
    }

    public void setStartTimeAssistance(String startTimeAssistance) {
        this.startTimeAssistance.set(startTimeAssistance);
    }

    public void setTransferTimeAssistance(String transferTimeAssistance) {
        this.transferTimeAssistance.set(transferTimeAssistance);
    }

    public void setEndTimeAssistance(String endTimeAssistance) {
        this.endTimeAssistance.set(endTimeAssistance);
    }

    public void setPlaceTransfer(String placeTransfer) {
        this.placeTransfer.set(placeTransfer);
    }

    public void setPathology(String pathology) {
        this.pathology.set(pathology);
    }

    public void setRegistry(String registry) {
        this.registry.set(registry);
    }

}
