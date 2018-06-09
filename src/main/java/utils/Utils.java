package utils;

import javafx.util.StringConverter;
import javafx.util.converter.LocalTimeStringConverter;
import model.Record;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

public final class Utils {

    public static StringConverter<LocalTime> timeConverter
            = new LocalTimeStringConverter(FormatStyle.SHORT, Locale.FRENCH);
    public static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/YYYY");

    public static String nullToString(Object o) {
        if (o == null) return "";
        else return o.toString();
    }

    public static String nullFromString(Object o) {
        if (o == null) return null;
        else return o.toString();
    }

    public static String emptyStringToNull(String s) {
        if (s != null) {
            String ns = s.trim();
            if (ns.equals("")) return null;
            else return ns;
        }

        return null;
    }

    public static String generateCode(Record record) {
        return "#" + String.valueOf(record.getDate().getYear()).substring(2, 4) + "/" +
                String.format("%05d", record.getCode());
    }

}
