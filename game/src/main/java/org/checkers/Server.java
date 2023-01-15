package org.checkers;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner; 

/**
 * Server to play checkers using sockets.
 */
public class Server {
    /**
     * Server initiation.
     * gets game type
     * @param args the input arguments
     */
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(2137)) {

            System.out.println("Server is listening on port 2317");

            while (true) {
                Socket firstClient = serverSocket.accept();
                System.out.println("First client connected");
                System.out.println("Waiting for the second player");

                Socket secondClient = serverSocket.accept();
                System.out.println("Second client connected");

                Scanner skaner = new Scanner(System.in);
                boolean inputCheck = true;
                int variant = 0;
                while (inputCheck) {
                    System.out.println("Choose a game variant: '8' for an 8x8 board, '10' for a 10x10 board and '12' for a 12x12 board.");
                    String wariant = skaner.nextLine();
                    try {
                        variant = Integer.parseInt(wariant);
                        if (!((variant == 8) || (variant == 10) || (variant == 12))) {
                            throw new NumberFormatException();
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Wrong input");
                        continue;
                    }
                    inputCheck = false;
                }

                Game game = new Game(firstClient, secondClient, variant);
                Thread gTh = new Thread(game);
                gTh.start();
            }

        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
