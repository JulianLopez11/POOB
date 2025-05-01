package src.domain;
public class POOBkemonException extends Exception {
    public static final String POKEMON_NOT_FOUND = "The Pokemon Has Not Been Found" ;
    public static final String CANNOT_CHANGE_POKEMON = "The Pokemon Can Not Been Changed" ;
    public static final String POKEMON_EXAUSTED = "The Pokemon Cant Attack" ;
    public static final String NO_TRAINERS_FOUND = "No trainers Registered" ;
    public POOBkemonException(String message) {
        super(message);
    }
}