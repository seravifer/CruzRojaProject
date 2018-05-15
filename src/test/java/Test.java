import javafx.util.converter.LocalTimeStringConverter;

import java.time.format.FormatStyle;
import java.util.Locale;

public class Test {
    public static void main(String[] args) {
        LocalTimeStringConverter localTimeStringConverter =
                new LocalTimeStringConverter(FormatStyle.SHORT, Locale.GERMAN);
        System.out.println(localTimeStringConverter.fromString(16
                + ":" + 30));
    }
}
