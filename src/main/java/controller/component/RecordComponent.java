package controller.component;

import com.j256.ormlite.stmt.QueryBuilder;
import controller.RecordFormController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
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
    private AnchorPane rootID;

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
    private Label placeID;

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
        assemblyID.setText(record.getAssembly().getName());
        dateID.setText(record.getDate());
        startTimeID.setText(record.getStartTime());
        areaID.setText(record.getArea().getName());
        serviceID.setText(record.getService().getName());
        assistance_hID.setText(record.getAssistance_h() + "");
        assistance_mID.setText(record.getAssistance_m() + "");
        evacuated_hID.setText(record.getEvacuated_h() + "");
        evacuated_mID.setText(record.getEvacuated_m() + "");

        if (record.getResource() == null) {
            //((VBox)resourceID.getParent()).getChildren().remove(resourceID);
            resourceID.setText(""); // TODO eleminar si no existe pero añadir si existe
        } else resourceID.setText(record.getResource().getCode());

        placeID.setText(ifNull(record.getAddress()));
        notesID.setText(ifNull(record.getNotes()));

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

        rootID.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                Platform.runLater(() -> new RecordFormController(record));
            }
        });

        editID.setOnMouseClicked(e -> Platform.runLater(() -> new RecordFormController(record)));
        deleteID.setOnMouseClicked(e -> Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Dialogo de confirmación");
            alert.setHeaderText(null);
            alert.setContentText("¿Estas seguro de que quieres borrar el registro " + codeID.getText()
                    + "? No se podra revertir los cambios.");

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

    private String ifNull(String s) {
        if (s == null) return "---";
        else return s;
    }

    public void refresh() {
        try {
            record.refresh();
            init();
            if (record.getEndTime() != null) {
                ((VBox)this.getParent()).getChildren().remove(this);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
