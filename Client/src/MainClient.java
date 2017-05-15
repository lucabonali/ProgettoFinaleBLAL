import api.PlayerInterface;
import api.ServerInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

/**
 * @author lampa
 */
public class MainClient {
    public static void main(String[] args) throws IOException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(1099);
        ServerInterface server = (ServerInterface) registry.lookup("server");
        List<Integer> listGames = server.getGamesMap();
        System.out.println("Lista:");
        for (Integer i : listGames) {
            System.out.println(i);
        }
        PlayerInterface playerInterface = server.playerRMIRequest(0, new ClientImpl());
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            System.out.println("inserisci stringa:");
            String s = bf.readLine();
            playerInterface.setString(s);
        }
    }

}
