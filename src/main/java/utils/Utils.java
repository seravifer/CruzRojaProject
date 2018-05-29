package utils;

import javafx.util.StringConverter;
import javafx.util.converter.LocalTimeStringConverter;

import java.time.LocalTime;
import java.time.format.FormatStyle;
import java.util.Locale;

public final class Utils {

    public static StringConverter<LocalTime> timeConverter =
            new LocalTimeStringConverter(FormatStyle.SHORT, Locale.FRENCH);

    public static String nullToString(Object o) {
        if (o == null) return "";
        else return o.toString();
    }

    public static String clearString(Object o) {
        if (o == null) return null;
        else return o.toString();
    }

    public static String generateCode(String date, int code) {
        return null; // TODO refactoring
    }

}
