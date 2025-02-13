package controller;

import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.jfoenix.controls.*;
import controller.component.OperativeModal;
import controller.component.RecordComponent;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.collections.ListChangeListener;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.DateCell;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.shape.SVGPath;
import model.Record;
import model.User;
import service.DAO;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class RecordsController extends BorderPane {

    @FXML
    private JFXButton addID;

    @FXML
    private SVGPath settingsID;

    @FXML
    private SVGPath logoutID;

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

    @FXML
    private JFXComboBox<Integer> limitID;

    @FXML
    private JFXButton operativeID;

    @FXML
    private StackPane rootID;

    @FXML
    private Label userID;

    private User user;
    private Task task;

    public RecordsController() {
    }

    RecordsController(User user) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/records.fxml"));
            fxmlLoader.setController(this);
            Parent parent = fxmlLoader.load();
            SuperController.getInstance().setHome(parent, this);
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.user = user;
        init();
    }

    private void init() {
        userID.setText(user.getName());
        logoutID.setOnMouseClicked(e -> new LoginController());
        addID.setOnAction((e) -> new RecordFormController());
        settingsID.setOnMouseClicked(e -> new AdminController());
        reportID.setOnMouseClicked(e -> new ReportController());
        refreshID.setOnMouseClicked(e -> filter());

        limitID.getItems().addAll(10, 20, 50, 100, 300, 1000);
        limitID.setValue(50);

        fromID.setValue(LocalDate.now());
        //fromID.setValue(LocalDate.of(2018, 1, 1));
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

        JFXDialog dialog = new JFXDialog();
        OperativeModal modal = new OperativeModal();
        modal.setActionOnClose(e -> dialog.close());
        dialog.setContent(modal);
        dialog.setDialogContainer(rootID);
        operativeID.setOnAction(e -> dialog.show());

        limitDays();
        filter();
    }

    private void filter() {
        task = new Task<Void>() {
            @Override
            public Void call() throws SQLException, InterruptedException {
                QueryBuilder<Record, Integer> queryBuilder = DAO.record.queryBuilder();

                Where<Record, Integer> where = queryBuilder.limit((long) limitID.getValue())
                        .where().between("date", fromID.getValue(), toID.getValue());

                if (pendingID.isSelected()) where.and().isNull("endTime");

                List<Record> records = queryBuilder.query();

                Platform.runLater(() -> recordsID.getChildren().clear());

                for (Record record : records) {
                    Platform.runLater(() -> recordsID.getChildren().add(0, new RecordComponent(record)));
                    Thread.sleep(100);
                }

                return null;
            }
        };

        task.stateProperty().addListener(onUpdating());
        new Thread(task).start();
    }

    public void refresh() {
        task = new Task<Void>() {
            @Override
            public Void call() throws SQLException {
                List<Node> records = recordsID.getChildren();

                for (Node node : records) {
                    RecordComponent recordComponent = (RecordComponent) node;
                    Platform.runLater(() -> recordComponent.refresh(!pendingID.isSelected()));
                }

                Record lastRecord = DAO.record.queryBuilder()
                        .orderBy("ID_record", false)
                        .queryForFirst();
                RecordComponent lastRecordAdded = (RecordComponent) records.get(0);
                if (!lastRecord.equals(lastRecordAdded.getRecord()) && lastRecord.getEndTime() == null) {
                    Platform.runLater(() -> recordsID.getChildren().add(0, new RecordComponent(lastRecord)));
                }

                return null;
            }
        };

        task.stateProperty().addListener(onUpdating());
        new Thread(task).start();
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

    private ChangeListener onUpdating() {
        return (ob, o, status) -> {
            if (status == Worker.State.RUNNING) {
                optionsID.setDisable(true);
                loadID.setVisible(true);
            } else if (status == Worker.State.SUCCEEDED) {
                optionsID.setDisable(false);
                loadID.setVisible(false);
            }
        };
    }

}