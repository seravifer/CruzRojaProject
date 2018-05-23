package controller.component;

import com.j256.ormlite.stmt.QueryBuilder;
import controller.RecordFormController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;
import model.Event;
import model.Record;
import service.DAO;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;

public class RecordComponent extends AnchorPane {

    @FXML
    private Label codeID;

    @FXML
    private Label dateID;

    @FXML
    private Label resourceID;

    @FXML
    private Label assemblyID;

    @FXML
    private Label startTimeID;

    @FXML
    private Label endTimeID;

    @FXML
    private Label areaID;

    @FXML
    private Label serviceID;

    @FXML
    private Label assistance_hID;

    @FXML
    private Label evacuated_hID;

    @FXML
    private Label assistance_mID;

    @FXML
    private Label evacuated_mID;

    @FXML
    private Label registryID;

    @FXML
    private Label notesID;

    @FXML
    private SVGPath editID;

    @FXML
    private SVGPath deleteID;

    @FXML
    private Label statusID;

    @FXML
    private AnchorPane badgeID;

    private Record record;

    public RecordComponent(Record record) {
        this.record = record;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/component/record.fxml"));
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
        codeID.setText("#" + String.valueOf(LocalDate.parse(record.getDate()).getYear()).substring(2, 4) + "/" +
                String.format("%05d", record.getCode()));
        assemblyID.setText(record.getAssembly().getName_assembly());
        resourceID.setText(record.getResource().getCode_resource());
        dateID.setText(record.getDate());
        startTimeID.setText(record.getStartTime());
        areaID.setText(record.getArea().getName());
        serviceID.setText(record.getService().getName());
        assistance_hID.setText(record.getAssistance_h() + "");
        evacuated_hID.setText(record.getEvacuated_h() + "");
        assistance_mID.setText(record.getAssistance_m() + "");
        evacuated_mID.setText(record.getEvacuated_m() + "");

        if (record.getRegistry().equals("")) registryID.setText("---");
        else registryID.setText(record.getRegistry());

        if (record.getNotes().equals("")) notesID.setText("---");
        else notesID.setText(record.getNotes());

        if (record.getEndTime() == null) {
            Event event = null;
            try {
                QueryBuilder<Event, Integer> queryBuilder = DAO.event.queryBuilder();
                event = queryBuilder.where().eq("record_id", record.getID_record())
                        .and().isNull("endTimeAssistance").queryForFirst();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            if (event == null) {
                statusID.setText("En servicio");
                badgeID.setStyle("-fx-background-color: #27ff46");
            } else {
                statusID.setText("En translado");
                badgeID.setStyle("-fx-background-color: #FF9149");
            }

            endTimeID.setText("--:--");
        } else {
            statusID.setText("Finalizado");
            badgeID.setStyle("-fx-background-color: #ff2026");
            endTimeID.setText(record.getEndTime());
        }

        editID.setOnMouseClicked(e -> Platform.runLater(() -> new RecordFormController(record)));
        deleteID.setOnMouseClicked(e -> Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Dialogo de confirmación");
            alert.setHeaderText(null);
            alert.setContentText("¿Estas seguro de que quieres borrar el registro " + codeID.getText()
                    + "? No se podra revertir la acción.");

            ButtonType yesButton = new ButtonType("Sí");
            ButtonType noButton = new ButtonType("No");
            alert.getButtonTypes().setAll(yesButton, noButton);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == yesButton){
                try {
                    record.delete();
                    ((VBox) this.getParent()).getChildren().remove(this);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            } else {
                alert.close();
            }
        }));
    }

    public Record getRecord() {
        return record;
    }

    public void refresh() {
        init();
    }
}
