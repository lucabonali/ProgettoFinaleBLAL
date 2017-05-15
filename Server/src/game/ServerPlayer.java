package game;

import api.PlayerClientInterface;
import api.PlayerInterface;
import api.ServerPlayerInterface;
import exceptions.LorenzoException;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Luca, Andrea on 15/05/2017.
 * Questa classe serve per mettersi in attesa dei giocatori che si connettono tramite socket, stabilendo la connessione
 */

public class ServerPlayer extends UnicastRemoteObject implements ServerPlayerInterface, Runnable {

    private Map<Integer,Game> gameMap;
    ServerSocket server = null;
    Socket socketClient = null;
    PlayerSocket playerSocket;


    int port = 4000;

    public ServerPlayer() throws Exception {
        gameMap = new HashMap<>();
        createGames();
        new Thread(this).start();
    }

    private void createGames() throws LorenzoException {
        for(int i = 0; i<2 ; i++){
            gameMap.put(i,new Game());
        }
    }

    public void run() {
        try {
            server = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Waiting for connection in port:" + port);
        while (true) {
            try {
                System.out.println("......");
                socketClient = server.accept();
                System.out.println("Connection accepted from: " + socketClient.getInetAddress()); // potremmo togliere le sysout
                new PlayerSocketRequest(socketClient).start();
            } catch (Exception e) {

            }
        }
    }

    @Override
    public PlayerInterface playerRMIRequest(int idGame, PlayerClientInterface playerClientInterface) throws RemoteException {
        Game game = gameMap.get(idGame);
        if(!game.isFull()) {
            PlayerInterface player = new PlayerRmi(game,playerClientInterface);
            game.addPlayer(player);
            return player;
        }
        return null;
    }

    @Override
    public List<Integer> getGamesMap() throws RemoteException {
        return new ArrayList<Integer>(gameMap.keySet());
    }

    private class PlayerSocketRequest extends Thread {
        private Socket socketClient = null;
        private DataInputStream in;
        private DataOutputStream out;
        private int idGame;

        public PlayerSocketRequest(Socket socketClient){
            this.socketClient = socketClient;
        }

        @Override
        public void run(){
            try {
                in = new DataInputStream(socketClient.getInputStream());
                out = new DataOutputStream(socketClient.getOutputStream());
                Game game;
                do{
                    idGame = in.readInt();
                    game = gameMap.get(idGame);
                    if(game.isFull()) {
                        out.writeBoolean(false);
                    }
                }while(game.isFull());
                PlayerSocket player = new PlayerSocket(socketClient,game);
                new Thread(player).start();
                game.addPlayer(player);
            }
            catch (IOException e){

            }
        }

    }

}

