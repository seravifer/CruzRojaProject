package utils;

import com.jfoenix.controls.JFXTimePicker;
import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import model.Event;

import java.time.LocalTime;

import static utils.Utils.timeConverter;

public class EditingCellHour extends TableCell<Event, LocalTime> {

    private JFXTimePicker textField;

    public EditingCellHour() {
    }

    @Override
    public void startEdit() {
        if (!isEmpty()) {
            super.startEdit();
            createTextField();
            setText(null);
            setGraphic(textField);
            textField.requestFocus();
            textField.getEditor().deselect();
        }
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        //setText(getItem());
        setGraphic(null);
    }

    @Override
    public void updateItem(LocalTime item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            if (isEditing()) {
                if (textField != null) {
                    textField.setValue(getItem());
                }
                setText(null);
                setGraphic(textField);
            } else {
                setText(getString());
                setGraphic(null);
            }
        }
    }

    private void createTextField() {
        textField = new JFXTimePicker();
        textField.setDefaultColor(Color.valueOf("#e53935"));
        textField.setValue(getItem());
        textField.setMinWidth(this.getWidth());
        textField.getEditor().setAlignment(Pos.CENTER);
        textField.setConverter(timeConverter);
        textField.focusedProperty().addListener((ob, o, focused) -> {
            if (!focused) commitEdit(textField.getValue());
        });

        textField.setOnKeyPressed(t -> {
            if (t.getCode() == KeyCode.ENTER) {
                commitEdit(textField.getValue());
            } else if (t.getCode() == KeyCode.ESCAPE) {
                cancelEdit();
            }
        });
    }

    private String getString() {
        return getItem() == null ? "" : getItem().toString();
    }
}
