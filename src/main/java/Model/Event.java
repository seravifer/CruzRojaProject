package Model;

import Utils.SimpleStringPropertyType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import javafx.beans.property.SimpleStringProperty;

@DatabaseTable(tableName = "Event")
public class Event {

    @DatabaseField(generatedId = true)
    private int ID_Event;
    private int subcode;
    @DatabaseField(persisterClass = SimpleStringPropertyType.class)
    private SimpleStringProperty startTimeAssistance;
    @DatabaseField(persisterClass = SimpleStringPropertyType.class)
    private SimpleStringProperty transferTimeAssistance;
    @DatabaseField(persisterClass = SimpleStringPropertyType.class)
    private SimpleStringProperty endTimeAssistance;
    @DatabaseField(persisterClass = SimpleStringPropertyType.class)
    private SimpleStringProperty placeTransfer;
    @DatabaseField(persisterClass = SimpleStringPropertyType.class)
    private SimpleStringProperty registry;


    public Event() {}
    public Event(int subcode, String startTimeAssistance, String transferTimeAssistance, String placeTransfer,
                 String endTimeAssistance, String registry) {
        this.subcode = subcode;
        this.startTimeAssistance = new SimpleStringProperty(startTimeAssistance);
        this.transferTimeAssistance = new SimpleStringProperty(transferTimeAssistance);
        this.placeTransfer = new SimpleStringProperty(placeTransfer);
        this.endTimeAssistance = new SimpleStringProperty(endTimeAssistance);
        this.registry = new SimpleStringProperty(registry);
    }

    public int getID_Event() {
        return ID_Event;
    }

    public int getSubcode() {
        return subcode;
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

    public String getRegistry() {
        return registry.get();
    }

    public SimpleStringProperty registryProperty() {
        return registry;
    }

    public void setID_Event(int ID_Event) {
        this.ID_Event = ID_Event;
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

    public void setRegistry(String registry) {
        this.registry.set(registry);
    }

}
