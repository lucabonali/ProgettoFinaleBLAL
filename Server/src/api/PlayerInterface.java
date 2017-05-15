package api;


import java.io.IOException;
import java.rmi.Remote;

/**
 * @author Luca
 * @author Andrea
 *
 * interfaccia implementata nel server che avrà tutti i metodi comuni tra i due "tipi di giocatori" e vengono invocati su richiesta del client
 */
public interface PlayerInterface extends Remote {

    void setString(String name) throws IOException;

    void writeToClient(String name) throws IOException;
}
