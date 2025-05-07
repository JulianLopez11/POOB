package domain;

public class CityException extends Exception {
    public static final String NO_FILES = "";
    public CityException(String message) {
        super(message);
    }
}
