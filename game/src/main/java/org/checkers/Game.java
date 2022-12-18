package org.checkers;

import org.checkers.State.Player;

import java.io.*;
import java.net.Socket;

public class Game implements Runnable{

    private Socket firstPlayer;
    private Socket secondPlayer;

    private static Player turn = Player.ONE;


    public Game(Socket firstPlayer, Socket secondPlayer){
        this.firstPlayer = firstPlayer;
        this.secondPlayer= secondPlayer;


    }
    @Override
    public void run() {

        try{
            //Inicjalizacja pobieranie od socketa dla player1
            InputStream inputF = firstPlayer.getInputStream();
            BufferedReader inF = new BufferedReader(new InputStreamReader(inputF));

            //Inicjalizacja pobieranie od socketa dla player2
            InputStream inputS = secondPlayer.getInputStream();
            BufferedReader inS = new BufferedReader(new InputStreamReader(inputS));

            //Inicjalizacja Wysylania do socketa dla player1
            OutputStream outputF = firstPlayer.getOutputStream();
            PrintWriter outF = new PrintWriter(outputF, true);

            //Inicjalizacja Wysylania do socketa dla player2
            OutputStream outputS = secondPlayer.getOutputStream();
            PrintWriter outS = new PrintWriter(outputS, true);

            outF.println("1");
            outS.println("2");

            String initialCoords;
            String nextCoords;
            do {
                if (turn == Player.TWO) {
                    // Odbieranie od socketa
                    initialCoords = inS.readLine();
                    nextCoords = inS.readLine();
                    // Wypisywanie na serwerze
                    System.out.println(initialCoords);
                    System.out.println(nextCoords);
                    // Wysylanie do socket
                    turn = Player.ONE;
                }
                if (turn == Player.ONE) {
                    // Odbieranie od socketa
                    initialCoords = inF.readLine();
                    nextCoords = inF.readLine();
                    // Wypisywanie na serwerze
                    System.out.println(initialCoords);
                    System.out.println(nextCoords);
                    // Wysylanie do socketa
                    turn = Player.TWO;
                }
            } while (true);

        } catch (IOException ex) {
            System.err.println("ex");
        }
    }

    private void sendMove(DataOutputStream out, String text) throws IOException {
        out.writeChars(text);
    }
}
