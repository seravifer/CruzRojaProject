package utils;

import com.jfoenix.controls.JFXTimePicker;
import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import javafx.scene.input.KeyCode;
import javafx.util.StringConverter;
import javafx.util.converter.LocalTimeStringConverter;
import model.Event;

import java.time.LocalTime;
import java.time.format.FormatStyle;
import java.util.Locale;

import static utils.Utils.timeConverter;

public class EditingCellHour extends TableCell<Event, String> {

    private JFXTimePicker textField;

    public EditingCellHour() {}

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
        setText(getItem());
        setGraphic(null);
    }

    @Override
    public void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            if (isEditing()) {
                if (textField != null) {
                    if (!getString().isEmpty() || getString() != null) textField.setValue(LocalTime.parse(getString()));
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
        if (!getString().isEmpty() && getString() != null) textField.setValue(LocalTime.parse(getString()));
        textField.setMinWidth(this.getWidth());
        textField.getEditor().setAlignment(Pos.CENTER);
        textField.setConverter(timeConverter);
        textField.focusedProperty().addListener((ob, o, n) -> {
            if (!n) {
                if (textField.getValue() != null) commitEdit(textField.getValue().toString());
            }
        });

        textField.setOnKeyPressed(t -> {
            if (t.getCode() == KeyCode.ENTER) {
                commitEdit(textField.getValue().toString());
            } else if (t.getCode() == KeyCode.ESCAPE) {
                cancelEdit();
            }
        });
    }

    private String getString() {
        return getItem() == null ? "" : getItem();
    }
}
