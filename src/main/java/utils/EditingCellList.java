package utils;

import com.jfoenix.controls.JFXComboBox;
import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import model.Event;

import java.util.List;

public class EditingCellList extends TableCell<Event, String> {

    private JFXComboBox<String> comboBoxID;
    private List<String> stringList;

    public EditingCellList(List<String> stringList) {
        this.stringList = stringList;
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
        setText(getItem());
        setGraphic(null);
    }

    @Override
    public void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (item == null || empty) {
            setText(null);
            setGraphic(null);
        } else {
            if (isEditing()) {
                if (comboBoxID != null && getItem() != null) comboBoxID.setValue(getItem());
                setText(null);
                setGraphic(comboBoxID);
            } else {
                setText(item);
                setGraphic(null);
            }
        }
    }

    private void createComboBox() {
        comboBoxID = new JFXComboBox<>();
        comboBoxID.setFocusColor(Color.valueOf("#e53935"));
        comboBoxID.getItems().setAll(stringList);
        if (getItem() != null) comboBoxID.setValue(getItem());
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
