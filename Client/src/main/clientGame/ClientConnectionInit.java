package main.clientGame;

import main.GUI.applications.GameSelection;
import main.api.ClientInterface;
import main.api.ServerInterface;

import java.io.IOException;
import java.net.Socket;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Classe che serve per inizializzare la connessione in base a uno dei due metodi RMI  o  Socket
 * @author Andrea,Luca
 */
public class ClientConnectionInit {
    private int connection;
    private String userName, password;
    private Registry registry;
    private ServerInterface server;

    /**
     * costruttore della classe, invoca uno dei due metodi seguenti in base alla scelta di tipo di connessione
     * @param connection
     * @param userName
     * @param password
     * @throws IOException
     * @throws NotBoundException
     */
    public ClientConnectionInit(int connection, String userName, String password) throws IOException, NotBoundException {
        this.connection = connection;
        this.userName = userName;
        this.password = password;
        if(connection == 0)
            startRMIClent();
        else
            startSocketClient();
    }

    /**
     * metodo che crea la connessione verso il server tramite RMI, e passa un giocatore RMI al server, su cui
     * verranno richiamati i metodi
     * @throws IOException
     * @throws NotBoundException
     */
    public void startRMIClent() throws IOException, NotBoundException {
        registry = LocateRegistry.getRegistry(1099);
        server = (ServerInterface) registry.lookup("serverRMI");
        if(server.login(userName,password)){
            ClientInterface playerRMI = new ClientRMIImpl(userName,password);
            server.addPlayerRMI(playerRMI);
            launchGameSelection();
            }
        else{

        }

    }

    /**
     * metodo che crea la connessione verso il server tramite socket
     * @throws IOException
     */
    public void startSocketClient() throws IOException {
        ClientSocket clientSocket = new ClientSocket(new Socket("localhost", 4000));
        new Thread(clientSocket).start();
        launchGameSelection();
    }

    /**
     * metodo che lancia la finestra di selezione della partita
     */
    public void launchGameSelection(){
        GameSelection gameSelection = new GameSelection();
        gameSelection.launchGameSelection();
    }


}
