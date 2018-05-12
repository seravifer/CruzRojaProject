package utils;

import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.field.types.BaseDataType;
import com.j256.ormlite.support.DatabaseResults;
import javafx.beans.property.SimpleStringProperty;

import java.sql.SQLException;

public class SimpleStringPropertyType extends BaseDataType {

    private static final SimpleStringPropertyType singleTon = new SimpleStringPropertyType();

    private SimpleStringPropertyType() {
        super(SqlType.STRING, new Class<?>[]{String.class});
    }

    /**
     * Getter for singleton of class.
     * @return singleton
     */
    public static SimpleStringPropertyType getSingleton() {
        return singleTon;
    }

    /**
     * Parse a default String into a SimpleStringProperty.
     * @param fieldType Field Type
     * @param defaultStr Default string to parse
     * @return New SimpleStringProperty
     */
    @Override
    public Object parseDefaultString(FieldType fieldType, String defaultStr) {
        return new SimpleStringProperty(defaultStr);
    }

    /**
     * Converts database results into Sql argument
     * @param fieldType Field Type
     * @param results Results to convert
     * @param columnPos Column position
     * @return SQL argument from input
     */
    @Override
    public Object resultToSqlArg(FieldType fieldType, DatabaseResults results, int columnPos) throws SQLException {
        return results.getString(columnPos);
    }

    /**
     * Converts SQL argument into a SimpleStringProperty.
     * @param fieldType Field Type
     * @param sqlArg SQL argument to convert
     * @param columnPos Column Position
     * @return SimpleStringProperty from input
     */
    @Override
    public Object sqlArgToJava(FieldType fieldType, Object sqlArg, int columnPos) {
        return new SimpleStringProperty((String) sqlArg);
    }

    /**
     * Converts a SimpleStringProperty into a string to be store in the database.
     * @param fieldType Field Type
     * @param javaObject SimpleStringProperty to convert
     * @return string from input
     */
    @Override
    public Object javaToSqlArg(FieldType fieldType, Object javaObject) {
        SimpleStringProperty stringProperty = (SimpleStringProperty) javaObject;
        return stringProperty.getValue();
    }
}