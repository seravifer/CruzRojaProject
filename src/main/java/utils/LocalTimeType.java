package utils;

import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.field.types.BaseDateType;
import com.j256.ormlite.support.DatabaseResults;

import java.sql.SQLException;
import java.time.LocalTime;

public class LocalTimeType extends BaseDateType {

    private static final LocalTimeType singleTon = new LocalTimeType();

    public static LocalTimeType getSingleton() {
        return singleTon;
    }

    private LocalTimeType() {
        super(SqlType.STRING, new Class<?>[]{String.class});
    }

    protected LocalTimeType(SqlType sqlType, Class<?>[] classes) {
        super(sqlType, classes);
    }

    @Override
    public Object parseDefaultString(FieldType fieldType, String defaultStr) {
        return LocalTime.parse(defaultStr);
    }

    @Override
    public Object resultToSqlArg(FieldType fieldType, DatabaseResults results, int columnPos) throws SQLException {
        return results.getString(columnPos);
    }

    @Override
    public Object sqlArgToJava(FieldType fieldType, Object sqlArg, int columnPos) {
        return LocalTime.parse((String) sqlArg);
    }

    @Override
    public Object javaToSqlArg(FieldType fieldType, Object javaObject) {
        LocalTime date = (LocalTime) javaObject;
        if (date != null) return date.getHour() + ":" + date.getMinute();
        else return null;
    }

}
