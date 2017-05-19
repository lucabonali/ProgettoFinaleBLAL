package main.client;

import main.api.ServerInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Classe che serve per inizializzare la connessione in base a uno dei due metodi RMI  o  Socket
 * @author lampa,Luca
 */
public class MainClient {
    private int connection;
    private String userName, password;
    private Registry registry;
    private ServerInterface server;


    public MainClient(int connection, String userName, String password) {
        this.connection = connection;
        this.userName = userName;
        this.password = password;
    }

    public void startClient() throws IOException, NotBoundException {
        if (connection == 0) {
            registry = LocateRegistry.getRegistry(1099);
            server = (ServerInterface) registry.lookup("serverRMI");
            //true se login va a buon fine, false altrimenti
            System.out.println(server.login("andrea", "lol"));
        } else {
            ClientSocket clientSocket = new ClientSocket(new Socket("localhost", 4000));
            new Thread(clientSocket).start();
        }
    }

}
