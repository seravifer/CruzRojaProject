package utils;

public final class Utils {

    public static String nullToString(Object o) {
        if (o == null) return "";
        else return o.toString();
    }

    public static String clearString(Object o) {
        if (o == null) return null;
        else return o.toString();
    }
}
