package controller.component;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.SVGPath;
import model.Operative;
import service.DAO;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class OperativeModal extends AnchorPane {

    @FXML
    private JFXTextField nameID;

    @FXML
    private JFXDatePicker dateID;

    @FXML
    private ListView<Operative> listID;

    @FXML
    private SVGPath closeID;

    public OperativeModal() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/component/operative.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        init();
    }

    private void init() {
        try {
            List<Operative> operatives = DAO.operatives.queryBuilder()
                    .where().between("date", LocalDate.now().minusMonths(1), LocalDate.now()).query();
            ObservableList<Operative> operativesob = FXCollections.observableArrayList(operatives);
            listID.setItems(operativesob);

            listID.setCellFactory(param -> new ListCell<Operative>() {
                @Override
                protected void updateItem(Operative item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty || item == null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        setText("#" + item.getID() + " - " + item.getName() + " - " + item.getDate());
                        /*SVGPath iconDelete = new SVGPath();
                        iconDelete.setContent("M19,4H15.5L14.5,3H9.5L8.5,4H5V6H19M6,19A2,2 0 0,0 8,21H16A2,2 0 0,0 18,19V7H6V19Z");
                        iconDelete.setCursor(Cursor.HAND);
                        iconDelete.setFill(Paint.valueOf("#545454"));
                        iconDelete.setPickOnBounds(true);
                        setGraphic(iconDelete);*/
                    }
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }

        listID.addEventFilter(MouseEvent.MOUSE_PRESSED, Event::consume);
        dateID.setValue(LocalDate.now());
    }

    @FXML
    void add(ActionEvent event) {
        try {
            Operative operative = new Operative(nameID.getText(), dateID.getValue().toString());
            DAO.operatives.create(operative);
            listID.getItems().add(operative);
            nameID.clear();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setActionOnClose( EventHandler<? super MouseEvent> event) {
        closeID.setOnMouseClicked(event);
    }

}
