package effects;

/**
 * @author Luca
 * @author Andrea
 *
 * è una classe di factory che non fa altro che, in base
 * al codice (recuperato dal db), generarmi l'istanza dell'effetto
 * corretta.
 */
public class EffectsCreator {
    /**
     * metodo statico che in base al codice mi crea l'effetto
     * corretto tra tutti quelli che implementano l'interfaccia
     * Effect
     * @param cod codice
     * @return l'effetto
     */
    public static Effect createEffect(String cod) {
        //ritorna l'effetto giusto
        //da implementare
        return null;
    }
}
