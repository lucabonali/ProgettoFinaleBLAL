package game;

import board.PersonalBoard;

import java.net.*;


public class ServerSocketPlayer extends Thread {

    private Game game;
    ServerSocket server = null;
    Socket socketClient = null;
    PlayerSocket playerSocket;


    int port = 4000;

    public ServerSocketPlayer(Game game) throws Exception {
        server = new ServerSocket(port);
        System.out.println("Waiting for connection in port:" + port);
        this.start();
    }

    public void run() {
        while (true) {
            try {
                System.out.println("......");
                socketClient = server.accept();
                System.out.println("Connection accepted from: " + socketClient.getInetAddress()); // potremmo togliere le sysout
                playerRequest();
            } catch (Exception e) {

            }
        }
    }

    /**
     * Metodo che controlla se aggiungere il giocatore quando si è connesso tramite socket
     */
    private void playerRequest() {
        int idPlayer = game.getNumPlayers()+1;
        if(idPlayer>4) {
            gameFull();
        }
        else {
            playerSocket = new PlayerSocket(socketClient, idPlayer, new PersonalBoard(idPlayer,game.getBoard()));
            game.addPlayer(idPlayer, playerSocket);
        }
    }

    private void gameFull() {
        //Da implementare, notifica al giocatore che la partita è "piena"
    }
}

