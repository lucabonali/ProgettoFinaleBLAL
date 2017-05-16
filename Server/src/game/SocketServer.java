package game;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.RemoteException;

/**
 * Created by Luca on 16/05/2017.
 */
public class SocketServer implements Runnable {

    ServerSocket server = null;
    int port = 4000;

    public SocketServer() throws RemoteException{
        new Thread(this).start();
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
                PlayerSocket player = new PlayerSocket(socketClient);
                new Thread(player).start();
            } catch (Exception e) {

            }
        }
    }
}
