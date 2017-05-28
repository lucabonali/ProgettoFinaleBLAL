package main.game_server.socket;

import main.api.ClientInterface;
import main.api.PlayerInterface;
import main.api.messages.SocketProtocol;
import main.game_server.AbstractServer;
import main.model.Game;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.List;


/**
 * @author Luca
 * @author Andrea
 *
 * classe che mi identifica il server Socket che implementa la stessa interfaccia del server RMI
 * ma qui i metodi verranno chiamati in seguito a messaggi provenienti dal client e codificati.
 */
public class SocketServer extends AbstractServer implements Runnable {
    private List<PlayerSocket> playerSocketList;
    ServerSocket server = null;
    int port = 4000;

    public SocketServer() throws RemoteException{
        new Thread(this).start();
    }


    @Override
    public PlayerInterface startGame(String username, int gameMode, ClientInterface client) throws RemoteException {
        Game game = getFreeGame(gameMode); //la prima partita libera trovata, in caso ne crea una nuova
        PlayerSocket playerSocket = new PlayerSocket(username);
        playerSocket.setGame(game);
        game.addPlayer(playerSocket);
        return playerSocket;
    }

    public void run(){
        try {
            server = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Waiting for connection in port:" + port);
        while (true) {
            try {
                System.out.println("......");
                Socket socketClient = server.accept();
                System.out.println("Connection accepted from: " + socketClient.getInetAddress()); // potremmo togliere le sysout
                PlayerSocketRequest playerRequest = new PlayerSocketRequest(socketClient);
                new Thread(playerRequest).start();
            } catch (Exception e) {

            }
        }
    }

    private class PlayerSocketRequest implements Runnable{
        private Socket socket;
        private ObjectInputStream in;
        private ObjectOutputStream out;

        PlayerSocketRequest(Socket socket){
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                int gameMode;
                in = new ObjectInputStream(socket.getInputStream());
                SocketProtocol msg = (SocketProtocol) in.readObject();
                String username = (String) in.readObject();
                String password = (String) in.readObject();
                out = new ObjectOutputStream(socket.getOutputStream());
                boolean resp = login(username, password);
                System.out.println(resp);
                out.writeBoolean(resp);
                out.flush();
                boolean isAssociated = false;
                try{
                    while (!isAssociated) {
                        msg = (SocketProtocol) in.readObject();
                        switch (msg) {
                            case LOGIN:
                                username = (String) in.readObject();
                                password = (String) in.readObject();
                                out.writeObject(login(username, password));
                                break;
                            case START_GAME:
                                username = (String) in.readObject();
                                gameMode = in.readInt();
                                PlayerSocket player = (PlayerSocket) startGame(username, gameMode, null);
                                if (player != null) {
                                    player.setSocketConnection(socket, in, out);
                                    new Thread(player).start();
                                    isAssociated = true;
                                }
                                else {
                                }
                                break;
                        }
                    }
                }
                catch (IOException e) {
                    System.out.println("errore1");
                }
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("errore2");
            }
        }
    }
}
