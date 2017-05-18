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
 * @author lampa,Luca
 */
public class MainClient {

    public void startClient() throws IOException, NotBoundException {

        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        int cmd;
        Registry registry;
        ServerInterface server;
        do{
            System.out.println("scegli la connessione:");
            System.out.println("0 -> RMI");
            System.out.println("1 -> SOCKET");
            cmd = Integer.parseInt(bf.readLine());
            switch (cmd) {
                case 0:
                    registry = LocateRegistry.getRegistry(1099);
                    server = (ServerInterface) registry.lookup("serverRMI");
                    //true se login va a buon fine, false altrimenti
                    System.out.println(server.login("andrea", "lol"));
                    break;
                case 1:
                    ClientSocket clientSocket = new ClientSocket(new Socket("localhost", 4000));
                    new Thread(clientSocket).start();
                    break;
                default:
                    break;
            }
        }while (cmd<0 || cmd >1);
    }

}
