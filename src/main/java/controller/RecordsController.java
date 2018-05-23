package controller;

import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.jfoenix.controls.*;
import controller.component.RecordComponent;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
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
import model.Record;
import service.DAO;

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

    @FXML
    private JFXProgressBar loadID;

    @FXML
    private AnchorPane optionsID;

    @FXML
    private SVGPath refreshID;

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
        settingsID.setOnMouseClicked(e -> new AdminController());
        reportID.setOnMouseClicked(e -> new ReportController());
        refreshID.setOnMouseClicked(e -> refresh());

        //fromID.setValue(LocalDate.now());
        fromID.setValue(LocalDate.of(2018, 1, 1));
        toID.setValue(LocalDate.now());

        recordsID.getChildren().addListener((ListChangeListener<Node>) c -> {
            if (recordsID.getChildren().size() == 0) noItemsID.setVisible(true);
            else noItemsID.setVisible(false);
        });

        fromID.valueProperty().addListener((ob, o, n) -> {
            if (n == null) fromID.setValue(LocalDate.now());
            limitDays();
            filter();
        });

        toID.valueProperty().addListener((ob, o, n) -> {
            if (n == null || toID.getValue().isBefore(fromID.getValue()))
                toID.setValue(fromID.getValue());
            filter();
        });

        pendingID.selectedProperty().addListener((ob, o, n) -> filter());

        limitDays();
        filter();
    }

    private void filter() {
        final Task task = new Task<Void>() {
            @Override
            public Void call() throws SQLException {
                QueryBuilder<Record, Integer> queryBuilder = DAO.record.queryBuilder();

                Where<Record, Integer> where = queryBuilder
                        .where().between("date", fromID.getValue(), toID.getValue());

                if (pendingID.isSelected()) where.and().isNull("endTime");

                List<Record> records = queryBuilder.query();

                Platform.runLater(() -> recordsID.getChildren().clear());

                for (Record record : records) {
                    Platform.runLater(() -> recordsID.getChildren().add(0, new RecordComponent(record)));
                }

                return null;
            }
        };

        task.stateProperty().addListener((ob, o, nValue) -> {
            if(nValue == Worker.State.RUNNING) {
                optionsID.setDisable(true);
                loadID.setVisible(true);
            } else if (nValue == Worker.State.SUCCEEDED) {
                optionsID.setDisable(false);
                loadID.setVisible(false);
            }
        });

        new Thread(task).start();
    }

    private void refresh() { // TODO ejecutar en un thread
        loadID.setVisible(true);
        List<Node> records = recordsID.getChildren();

        try {
            for (Node node : records) {
                RecordComponent recordComponent = (RecordComponent) node;
                recordComponent.refresh();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        loadID.setVisible(false);
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