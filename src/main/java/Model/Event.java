package Model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Objects;

@DatabaseTable(tableName = "Event")
public class Event {

    @DatabaseField(generatedId = true)
    private int ID_Event;
    @DatabaseField
    private int parent_id;
    @DatabaseField
    private int subcode;
    @DatabaseField
    private String startTimeAssistance;
    @DatabaseField
    private String transferTimeAssistance;
    @DatabaseField
    private String placeTransfer;
    @DatabaseField
    private String keyTransfer;
    @DatabaseField
    private String endTimeAssistance;

    public Event() { }

    public Event(int subcode, int parent_id, String startTimeAssistance, String transferTimeAssistance, String placeTransfer,
                 String keyTransfer, String endTimeAssistance) {
        this.subcode = subcode;
        this.parent_id = parent_id;
        this.startTimeAssistance = startTimeAssistance;
        this.transferTimeAssistance = transferTimeAssistance;
        this.placeTransfer = placeTransfer;
        this.keyTransfer = keyTransfer;
        this.endTimeAssistance = endTimeAssistance;
    }

    public int getID_Event() {
        return ID_Event;
    }

    public int getParent_id() {
        return parent_id;
    }

    public int getSubcode() {
        return subcode;
    }

    public String getStartTimeAssistance() {
        return startTimeAssistance;
    }

    public String getTransferTimeAssistance() {
        return transferTimeAssistance;
    }

    public String getPlaceTransfer() {
        return placeTransfer;
    }

    public String getKeyTransfer() {
        return keyTransfer;
    }

    public String getEndTimeAssistance() {
        return endTimeAssistance;
    }

    public void setSubcode(int subcode) {
        this.subcode = subcode;
    }

    public void setStartTimeAssistance(String startTimeAssistance) {
        this.startTimeAssistance = startTimeAssistance;
    }

    public void setTransferTimeAssistance(String transferTimeAssistance) {
        this.transferTimeAssistance = transferTimeAssistance;
    }

    public void setPlaceTransfer(String placeTransfer) {
        this.placeTransfer = placeTransfer;
    }

    public void setKeyTransfer(String keyTransfer) {
        this.keyTransfer = keyTransfer;
    }

    public void setEndTimeAssistance(String endTimeAssistance) {
        this.endTimeAssistance = endTimeAssistance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return ID_Event == event.ID_Event;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID_Event);
    }
}
