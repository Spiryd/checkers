package org.checkers;

import org.checkers.State.Player;
import org.checkers.model.Board;
import org.checkers.util.Translator;

import java.io.*;
import java.net.Socket;
import java.util.Objects;

public class Game implements Runnable{

    private final Socket firstPlayer;
    private final Socket secondPlayer;


    private static Player turn = Player.ONE;


    public Game(Socket firstPlayer, Socket secondPlayer){
        this.firstPlayer = firstPlayer;
        this.secondPlayer= secondPlayer;


    }
    @Override
    public void run() {

        try{
            Board board = new Board(8);

            InputStream inputF = firstPlayer.getInputStream();
            BufferedReader inF = new BufferedReader(new InputStreamReader(inputF));
            InputStream inputS = secondPlayer.getInputStream();
            BufferedReader inS = new BufferedReader(new InputStreamReader(inputS));

            OutputStream outputF = firstPlayer.getOutputStream();
            PrintWriter outF = new PrintWriter(outputF, true);
            OutputStream outputS = secondPlayer.getOutputStream();
            PrintWriter outS = new PrintWriter(outputS, true);

            outF.println("1");
            outS.println("2");

            String initialCoordsMessage;
            String nextCoordsMessage;
            Translator translator = new Translator();
            int[] initialCoords;
            int[] nextCoords;
            do {
                String moveSignature;
                if (turn == Player.TWO) {
                    // Odbieranie od socketa
                    initialCoordsMessage = inS.readLine();
                    nextCoordsMessage = inS.readLine();
                    // Wypisywanie na serwerze
                    //System.out.println(initialCoordsMessage);
                    //System.out.println(nextCoordsMessage);
                    //translation
                    initialCoords = translator.translateCoords(initialCoordsMessage);
                    nextCoords = translator.translateCoords(nextCoordsMessage);
                    //System.out.println(Arrays.toString(initialCoords));
                    //System.out.println(Arrays.toString(nextCoords));
                    moveSignature = board.movePiece(initialCoords[0], initialCoords[1], nextCoords[0], nextCoords[1]);
                    // Wysylanie do socket
                    outF.println(initialCoordsMessage + nextCoordsMessage + "2;" + moveSignature);
                    outS.println(initialCoordsMessage + nextCoordsMessage + "2;" + moveSignature);
                    if (!Objects.equals(moveSignature, "-1")) {
                        turn = Player.ONE;
                    }
                }
                if (turn == Player.ONE) {
                    // Odbieranie od socketa
                    initialCoordsMessage = inF.readLine();
                    nextCoordsMessage = inF.readLine();
                    // Wypisywanie na serwerze
                    //System.out.println(initialCoordsMessage);
                    //System.out.println(nextCoordsMessage);
                    // Wysylanie do socketa
                    //translation
                    initialCoords = translator.translateCoords(initialCoordsMessage);
                    nextCoords = translator.translateCoords(nextCoordsMessage);
                    //System.out.println(Arrays.toString(initialCoords));
                    //System.out.println(Arrays.toString(nextCoords));
                    //send to model
                    moveSignature = board.movePiece(initialCoords[0], initialCoords[1], nextCoords[0], nextCoords[1]);
                    // Wysylanie do socket
                    outF.println(initialCoordsMessage + nextCoordsMessage + "1;" + moveSignature);
                    outS.println(initialCoordsMessage + nextCoordsMessage + "1;" + moveSignature);
                    if (!Objects.equals(moveSignature, "-1")) {
                        turn = Player.TWO;
                    }
                }
            } while (true);

        } catch (IOException ex) {
            System.err.println("ex");
        }
    }
}
