package Model;

import javafx.beans.property.SimpleStringProperty;

public class EventProperty {

    private int ID_Event;
    private int parent_id;
    private int subcode;
    private SimpleStringProperty startTimeAssistance;
    private SimpleStringProperty transferTimeAssistance;
    private SimpleStringProperty placeTransfer;
    private SimpleStringProperty endTimeAssistance;



    public EventProperty(int subcode, String startTimeAssistance,
                         String transferTimeAssistance, String placeTransfer,
                         String endTimeAssistance) {
        this.subcode = subcode;
        this.startTimeAssistance = new SimpleStringProperty(startTimeAssistance);
        this.transferTimeAssistance = new SimpleStringProperty(transferTimeAssistance);
        this.placeTransfer = new SimpleStringProperty(placeTransfer);
        this.endTimeAssistance = new SimpleStringProperty(endTimeAssistance);
    }

    public int getSubcode() {
        return subcode;
    }

    public void setSubcode(int subcode) {
        this.subcode = subcode;
    }

    public String getStartTimeAssistance() {
        return startTimeAssistance.get();
    }

    public SimpleStringProperty startTimeAssistanceProperty() {
        return startTimeAssistance;
    }

    public void setStartTimeAssistance(String startTimeAssistance) {
        this.startTimeAssistance.set(startTimeAssistance);
    }

    public String getTransferTimeAssistance() {
        return transferTimeAssistance.get();
    }

    public SimpleStringProperty transferTimeAssistanceProperty() {
        return transferTimeAssistance;
    }

    public void setTransferTimeAssistance(String transferTimeAssistance) {
        this.transferTimeAssistance.set(transferTimeAssistance);
    }

    public String getPlaceTransfer() {
        return placeTransfer.get();
    }

    public SimpleStringProperty placeTransferProperty() {
        return placeTransfer;
    }

    public void setPlaceTransfer(String placeTransfer) {
        this.placeTransfer.set(placeTransfer);
    }

    public String getEndTimeAssistance() {
        return endTimeAssistance.get();
    }

    public SimpleStringProperty endTimeAssistanceProperty() {
        return endTimeAssistance;
    }

    public void setEndTimeAssistance(String endTimeAssistance) {
        this.endTimeAssistance.set(endTimeAssistance);
    }
}
