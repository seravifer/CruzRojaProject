package controller;

import controller.component.RecordComponent;
import model.Record;
import service.DAO;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDatePicker;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.DateCell;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class RecordsController extends AnchorPane {

    @FXML
    private JFXButton addID;

    @FXML
    private SVGPath settingsID;

    @FXML
    private SVGPath reportID;

    @FXML
    private JFXCheckBox allID;

    @FXML
    private JFXCheckBox pendingID;

    @FXML
    private JFXDatePicker toID;

    @FXML
    private JFXDatePicker fromID;

    @FXML
    private ScrollPane itemsID;

    @FXML
    private VBox recordsID;

    @FXML
    private HBox noItemsID;

    public RecordsController() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/records.fxml"));
            fxmlLoader.setController(this);
            Parent parent = fxmlLoader.load();
            SuperController.getInstance().getScene().setRoot(parent);
        } catch (IOException e) {
            e.printStackTrace();
        }

        init();
    }

    private void init() {
        addID.setOnAction((e) -> new RecordFormController());
        // settingsID.setOnMouseClicked(e -> new AdminController());
        // reportID.setOnMouseClicked(e -> new ReportController());

        //fromID.setValue(LocalDate.now());
        fromID.setValue(LocalDate.of(2018, 1,1));
        toID.setValue(LocalDate.now());

        recordsID.getChildren().addListener((ListChangeListener<Node>) c -> {
            if (recordsID.getChildren().size() == 0) noItemsID.setVisible(true);
            else noItemsID.setVisible(false);
        });

        fromID.valueProperty().addListener((ob, o, n) -> {
            filter();
            limitDays();
        });

        toID.valueProperty().addListener((ob, o, n) -> filter());

        pendingID.selectedProperty().addListener((ob, o,  n) -> filter());

        limitDays();
        filter();
    }

    private void filter() {
        QueryBuilder<Record, Integer> queryBuilder = DAO.recordDao.queryBuilder();
        List<Record> records = null;
        try {
            Where<Record, Integer> where = queryBuilder.where();
            where.between("date", fromID.getValue(), toID.getValue());
            if (pendingID.isSelected()) where.and().isNull("endTime");

            records = queryBuilder.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        recordsID.getChildren().clear();
        for (Record record : records) {
            recordsID.getChildren().add(0, new RecordComponent(record));
        }
    }

    private void limitDays() {
        toID.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.isBefore(fromID.getValue()));
            }
        });
    }
}