package main.CLI;


import main.client.AbstractClient;
import main.client.ClientRMI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.RemoteException;
import java.util.Scanner;

/**
 * Classe che farà da interfaccia utente da linea di comando
 * @author Andrea
 * @author Luca
 */
public class CLILauncher {
    private static BufferedReader in ;
    private static String connection;
    private static CLIController cliController;
    private static AbstractClient abstractClient;

    public static void main(String[] args) throws IOException {
        System.out.println("-------------- LORENZO IL MAGNIFICO --------------");
        System.out.println("Select a connection method: (true = RMI) (false = socket)");
        boolean b = true;
        while(b) {
            in = new BufferedReader(new InputStreamReader(System.in));
            connection = in.readLine();
            if (connection.equals("true")) {
                cliController = new CLIController();
                b = false;
            } else if (connection.equals("false")) {
                cliController = new CLIController();
                b = false;
            } else
                System.out.println("Insert a correct field, please.");
        }
        login();

        CLIGame cliGame = new CLIGame(cliController);
    }

    private static void login() {
        String userName = null, password = null;
            System.out.println("--------------- LORENZO IL MAGNIFICO ---------------");
            System.out.println(" - Insert username and password : ");
            boolean correct = true;
            do {
                try {
                    System.out.println(" - Username :");
                    userName = in.readLine();
                    System.out.println(" - Password :");
                    password = in.readLine();
                } catch (IOException e) {
                    correct = false;
                    System.out.println("Please, insert a correct Username");
                }
            }while(!correct);

        try {
            AbstractClient client = AbstractClient.createInstance(false, userName, password);
        } catch (RemoteException e) {
            e.printStackTrace();
        }


    }


}
