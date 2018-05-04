package Model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Event")
public class Event {

    @DatabaseField(generatedId = true)
    private int ID_Event;
    @DatabaseField
    private int  ID_Parent;
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

    public Event(int subcode, String startTimeAssistance, String transferTimeAssistance, String placeTransfer,
                 String keyTransfer, String endTimeAssistance) {
        this.subcode = subcode;
        this.startTimeAssistance = startTimeAssistance;
        this.transferTimeAssistance = transferTimeAssistance;
        this.placeTransfer = placeTransfer;
        this.keyTransfer = keyTransfer;
        this.endTimeAssistance = endTimeAssistance;
    }

    public int getID_Event() {
        return ID_Event;
    }

    public int getID_Parent() {
        return ID_Parent;
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
}
