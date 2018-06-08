package utils;

import com.jfoenix.controls.JFXComboBox;
import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import javafx.scene.input.KeyCode;
import model.Event;
import model.Hospital;

import java.util.List;

public class EditingCellList extends TableCell<Event, Hospital> {

    private JFXComboBox<Hospital> comboBoxID;
    private List<Hospital> hospitals;

    public EditingCellList(List<Hospital> hospitals) {
        this.hospitals = hospitals;
    }

    @Override
    public void startEdit() {
        if (!isEmpty()) {
            super.startEdit();
            createComboBox();
            setText(null);
            setGraphic(comboBoxID);
        }
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        //setText(getItem());
        setGraphic(null);
    }

    @Override
    public void updateItem(Hospital item, boolean empty) {
        super.updateItem(item, empty);
        if (item == null || empty) {
            setText(null);
            setGraphic(null);
        } else {
            if (isEditing()) {
                if (comboBoxID != null && getItem() != null) comboBoxID.getSelectionModel().select(getItem());
                setText(null);
                setGraphic(comboBoxID);
            } else {
                setText(item.getName());
                setGraphic(null);
            }
        }
    }

    private void createComboBox() {
        comboBoxID = new JFXComboBox<>();
        comboBoxID.getItems().setAll(hospitals);
        comboBoxID.setMinWidth(this.getWidth());
        comboBoxID.getEditor().setAlignment(Pos.CENTER);
        comboBoxID.focusedProperty().addListener((ob, o, focused) -> {
            if (!focused) commitEdit(comboBoxID.getValue());
        });
        comboBoxID.setOnKeyPressed(t -> {
            if (t.getCode() == KeyCode.ENTER) {
                commitEdit(comboBoxID.getValue());
            } else if (t.getCode() == KeyCode.ESCAPE) {
                cancelEdit();
            }
        });
    }

}