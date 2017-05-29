package main.CLI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Classe che rappresenta l' esecuzione della partita, su cui il giocatore chiamerà i comandi per giocare
 * @author Andrea
 * @author Luca
 */
public class CLIGame {
    private CLIController cliController;
    private BufferedReader in;
    private String userName, password;


    public CLIGame(CLIController cliController){
        this.cliController = cliController;
        in = new BufferedReader(new InputStreamReader(System.in));
        game();
    }

    /**
     *
     */
    private void game() {

        //Ciclo con dentro lo switch incredibile
        while(true){

        }

    }



}
