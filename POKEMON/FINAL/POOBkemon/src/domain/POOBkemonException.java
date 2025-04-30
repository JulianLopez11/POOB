package src.domain;
public class POOBkemonException extends Exception {
    private static final String POKEMON_NOT_FOUND = "The Pokemon Has Not been Found" ;
    private static final String CANNOT_CHANGE_POKEMON = "The Pokemon Can Not Been Changed" ;
    private static final String POKEMON_EXAUSTED = "The Pokemon Cant Attack" ;
    public POOBkemonException(String message) {
        super(message);
    }
}
