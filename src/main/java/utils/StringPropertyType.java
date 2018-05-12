package utils;

import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.field.types.StringType;
import com.j256.ormlite.support.DatabaseResults;
import javafx.beans.property.SimpleStringProperty;

import java.sql.SQLException;

public class StringPropertyType extends StringType {

    private static final StringPropertyType INSTANCE = new StringPropertyType();

    private StringPropertyType() {
        super(SqlType.STRING, new Class<?>[] { StringPropertyType.class });
    }

    public static StringPropertyType getSingleton() {
        return INSTANCE;
    }

    @Override
    public Object javaToSqlArg(FieldType fieldType, Object javaObject) {
        SimpleStringProperty property = (SimpleStringProperty) javaObject;
        if (property.getValue().equals("")) return null;
        else return property.getValue();
    }

    @Override
    public Object resultToJava(FieldType fieldType, DatabaseResults results, int columnPos) throws SQLException {
        // we override this here because the base class does the null comparison
        Object value = resultToSqlArg(fieldType, results, columnPos);
        return sqlArgToJava(fieldType, value, columnPos);
    }

    @Override
    public boolean isStreamType() {
        // this forces the null check to be ignored
        return true;
    }

    @Override
    public Object sqlArgToJava(FieldType fieldType, Object sqlArg, int columnPos) {
        if (sqlArg == null) return new SimpleStringProperty("");
        else return new SimpleStringProperty((String) sqlArg);
    }

}