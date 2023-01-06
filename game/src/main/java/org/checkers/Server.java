package org.checkers;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Server to play checkers using sockets.
 */
public class Server {
    /**
     * Server initiation.
     *
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

                Game game = new Game(firstClient, secondClient);
                Thread gTh = new Thread(game);
                gTh.start();
            }

        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
