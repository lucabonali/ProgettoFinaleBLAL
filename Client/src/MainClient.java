import api.PlayerInterface;
import api.ServerInterface;

import java.io.*;
import java.net.Socket;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

/**
 * @author lampa
 */
public class MainClient {
    public static void main(String[] args) throws IOException, NotBoundException {

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
                    server = (ServerInterface) registry.lookup("server");
                    List<Integer> listGames = server.getGamesMap();
                    System.out.println("Lista:");
                    for (Integer i : listGames) {
                        System.out.println(i);
                    }
                    PlayerInterface playerInterface = server.playerRMIRequest(0, new ClientImpl());
                    while (true) {
                        System.out.println("inserisci stringa:");
                        String s = bf.readLine();
                        if(s.equals("quit"))
                            break;
                    }
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
