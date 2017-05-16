package main.game.socket;

import main.api.MessageLogin;
import main.api.PlayerInterface;
import main.game.AbstractServer;
import main.game.Game;
import main.MainServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Luca
 * @author Andrea
 *
 * classe che mi identifica il server Socket che implementa la stessa interfaccia del server RMI
 * ma qui i metodi verranno chiamati in seguito a messaggi provenienti dal client e codificati.
 */
public class SocketServer extends AbstractServer implements Runnable {
    ServerSocket server = null;
    int port = 4000;

    public SocketServer() throws RemoteException{
        new Thread(this).start();
    }


    @Override
    public PlayerInterface startGame(String username) throws RemoteException {
        List<Game> games = new ArrayList<>(MainServer.gamesMap.values());
        for (Game g : games) {
            if(!g.isFull()){
                PlayerSocket playerSocket = new PlayerSocket(username);
                playerSocket.setGame(g);
                g.addPlayer(playerSocket);
                return playerSocket;
            }
        }
        return null;
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
                in = new ObjectInputStream(socket.getInputStream());
                MessageLogin msgLogin = (MessageLogin) in.readObject();
                out = new ObjectOutputStream(socket.getOutputStream());
                boolean resp = login(msgLogin.getUsername(), msgLogin.getPassword());
                System.out.println(resp);
                out.writeBoolean(resp);
                out.flush();
                boolean isAssociated = false;
                while (!isAssociated) {
                    msgLogin = (MessageLogin) in.readObject();
                    switch (msgLogin.getType()) {
                        case LOGIN:
                            out.writeBoolean(login(msgLogin.getUsername(), msgLogin.getPassword()));
                            break;
                        case START_GAME:
                            PlayerSocket player = (PlayerSocket) startGame(msgLogin.getUsername());
                            if (player != null) {
                                player.setSocketConnection(socket, in, out);
                                new Thread(player).start();
                                out.writeBoolean(true);
                                isAssociated = true;
                            }
                            else {
                                out.writeBoolean(false);
                            }
                            break;
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
