package main.CLI;


import main.client.AbstractClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.NotBoundException;
import java.util.Map;

/**
 * Classe che farà da interfaccia utente da linea di comando
 * @author Andrea
 * @author Luca
 */
public class CLILauncher {

    public static final String RED = "\u001B[31m";
    public static final String RESET = "\u001B[0m";

    private static BufferedReader in ;
    private static CLIController cliController;
    private AbstractClient abstractClient;



    /**
     * metodo principale del CLILauncher, si occupa di far selezionare all' utente un metodo di
     * connessione e chiama tutti gli altri metodi della partita
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        System.out.println(RED + "-------------- LORENZO IL MAGNIFICO --------------" +RESET);
        boolean b = true;
        in = new BufferedReader(new InputStreamReader(System.in));
        boolean conn = false;
        while(b) {
            System.out.println("Select a connection method: (true = RMI) (false = socket)");
            String connection = in.readLine();
            switch (connection) {
                case "true":
                    conn = true;
                    b = false;
                    break;
                case "false":
                    conn = false;
                    b = false;
                    break;
                default:
                    System.out.println("Insert a correct field, please.");
                    break;
            }
        }
        cliController = new CLIController();
        login(conn);
    }


    /**
     * metodo che si occupa dell' invio dei dati di login
     * @param connection
     */
    private static void login(boolean connection) {
        String userName, password;
        System.out.println(RED + "-------------- LORENZO IL MAGNIFICO --------------" +RESET);
        System.out.println(" - Insert username and password : ");
        boolean correct = true;
        AbstractClient client = null;
        do {
            try {
                System.out.println(" - Username :");
                userName = in.readLine();
                System.out.println(" - Password :");
                password = in.readLine();
                client = AbstractClient.createInstance(connection, userName, password);
                correct = client.login();
            }
            catch (IOException e) {
                correct = false;
                System.out.println("Please, insert a correct field.");
            }
            catch (NotBoundException e) {
                e.printStackTrace();
            }
        }while(!correct);
        client.setInterfaceController(cliController);
        new Thread(cliController).start();
    }





}
