package main.api.exceptions;

/**
 * @author Luca
 * @author Andrea
 *
 * Eccezione usata e lanciata dal gioco in caso di situazioni non corrette,
 * per esempio se di tenta di effettuare in uno spazio azione non occupabile.
 */
public class LorenzoException extends Exception {

    public LorenzoException() {
        this("ERROR");
    }

    public LorenzoException(String codException) {
        super(codException);
    }
}
