package Controller;

import Controller.Component.RecordComponent;
import Model.Record;
import Service.DataBase;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.QueryBuilder;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class RecordsController extends AnchorPane {

    @FXML
    private VBox recordsID;

    @FXML
    private JFXButton addID;

    @FXML
    private SVGPath settingsID;

    public RecordsController() throws SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/records.fxml"));
        fxmlLoader.setController(this);
        Parent parent = null;
        try {
            parent = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(parent);
        SuperController.getInstance().setScene(scene);

        init();
    }

    private void init() throws SQLException {
        Dao<Record, Integer> recordDao = DaoManager.createDao(DataBase.get(), Record.class);

        QueryBuilder<Record, Integer> queryBuilder = recordDao.queryBuilder().limit((long) 5);
        List<Record> records = recordDao.query(queryBuilder.prepare());

        for (Record record : records) {
            recordsID.getChildren().add(0, new RecordComponent(record));
        }

        addID.setOnAction((e) -> new RecordFormController());
        recordsID.setOnMouseClicked((e)-> {});
    }
}