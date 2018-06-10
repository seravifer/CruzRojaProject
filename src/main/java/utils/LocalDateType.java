package utils;

import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.field.types.BaseDateType;
import com.j256.ormlite.support.DatabaseResults;

import java.sql.SQLException;
import java.time.LocalDate;

public class LocalDateType extends BaseDateType {

    private static final LocalDateType singleTon = new LocalDateType();

    private LocalDateType() {
        super(SqlType.STRING, new Class<?>[]{String.class});
    }

    protected LocalDateType(SqlType sqlType, Class<?>[] classes) {
        super(sqlType, classes);
    }

    public static LocalDateType getSingleton() {
        return singleTon;
    }

    @Override
    public Object parseDefaultString(FieldType fieldType, String defaultStr) {
        return LocalDate.parse(defaultStr);
    }

    @Override
    public Object resultToSqlArg(FieldType fieldType, DatabaseResults results, int columnPos) throws SQLException {
        return results.getString(columnPos);
    }

    @Override
    public Object sqlArgToJava(FieldType fieldType, Object sqlArg, int columnPos) {
        return LocalDate.parse((String) sqlArg);
    }

    @Override
    public Object javaToSqlArg(FieldType fieldType, Object javaObject) {
        LocalDate date = (LocalDate) javaObject;
        if (date != null) return date.toString();
        else return null;
    }

}
