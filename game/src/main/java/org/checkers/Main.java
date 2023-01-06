package org.checkers;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import org.checkers.State.Player;
import org.checkers.ui.Checker;
import org.checkers.ui.Kwadrat;
import org.checkers.util.Translator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

/**
 * Client for playing checkers.
 */
public class Main extends Application implements Runnable{

    /**
     * The Socket.
     */
    Socket socket = null;
    /**
     * The Out.
     */
    PrintWriter out = null;
    /**
     * The In.
     */
    BufferedReader in = null;

    /**
     * The Player.
     */
    Player player;

    /**
     * The Pola.
     */
    Kwadrat[][] Pola;
    /**
     * The Red checkers.
     */
    ArrayList<Checker> red_checkers;
    /**
     * The White checkers.
     */
    ArrayList<Checker>  white_checkers;

    /**
     * The type of game to be played.
     */
    static int arg = 0;

    @Override
    public void start(Stage stage) {
        //System.out.println("3");
        this.initUI(stage);
        //System.out.println(Arrays.toString(this.white_checkers));
        //System.out.println(Arrays.toString(this.red_checkers));
        this.startThread();
        //System.out.println(Arrays.toString(this.white_checkers));
        //System.out.println(Arrays.toString(this.red_checkers));
    }

    /**
     * Initiates the GUI.
     *
     * @param stage the stage
     */
    public void initUI(Stage stage) {
        double dimensions = 1000;
        double SquareSize = dimensions/8;
        int red_position = 3;
        int white_position = 5;

        double finalSquareSize = SquareSize;
        EventHandler<MouseEvent> filer = mouseEvent -> {
            String message = "";
            int x = (int) ((mouseEvent.getX() - (mouseEvent.getX() % finalSquareSize))/ finalSquareSize);
            int y = (int) ((mouseEvent.getY() - (mouseEvent.getY() % finalSquareSize))/ finalSquareSize);
            message += Integer.toString(x);
            message += ";";
            message += Integer.toString(y);
            message += ";";
            //System.out.println(message);
            out.println(message);
            mouseEvent.consume();
        };

        switch(arg) {
            case 1:
                this.Pola = new Kwadrat[8][8];
                this.red_checkers = new ArrayList<>();
                this.white_checkers = new ArrayList<>();
                break;
            case 2:
                this.Pola = new Kwadrat[10][10];
                this.red_checkers = new ArrayList<>();
                this.white_checkers = new ArrayList<>();
                SquareSize = dimensions/10;
                red_position = 4;
                white_position = 6;
                break;
            case 3:
                this.Pola = new Kwadrat[12][12];
                this.red_checkers = new ArrayList<>();
                this.white_checkers = new ArrayList<>();
                SquareSize = dimensions/12;
                red_position = 4;
                white_position = 8;
                break;
            default:
                System.out.println("Nieprawidlowe dane");
                System.exit(0);
        }
        Pane Panel = new Pane();
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, dimensions, dimensions, Color.BLACK);
        int row = 0;
        int column = 0;
        int checkerid = 0;
        for (double i = 0.0; i < dimensions; i += SquareSize) {
            for (double j = 0.0; j < dimensions; j += SquareSize) {
                this.Pola[column][row] = new Kwadrat(j, i, SquareSize);
                if ((row+column) % 2 == 0) {
                    this.Pola[column][row].setFill(Color.GREY);
                    Panel.getChildren().add(Pola[column][row]);
                }
                else {
                    this.Pola[column][row].setFill(Color.BLACK);
                    Panel.getChildren().add(Pola[column][row]);
                    if(row < red_position) {
                        this.red_checkers.add(new Checker(j+(SquareSize/2), i+(SquareSize/2), SquareSize*0.4, row, column));
                        this.red_checkers.get(checkerid).setFill(Color.RED);
                        if (player == Player.TWO) {
                            this.red_checkers.get(checkerid).addEventFilter(MouseEvent.MOUSE_PRESSED, filer);
                            this.red_checkers.get(checkerid).addEventFilter(MouseEvent.MOUSE_RELEASED, filer);
                        }
                        Panel.getChildren().add(this.red_checkers.get(checkerid));
                        checkerid++;
                    }
                    if(row >= white_position) {
                        this.white_checkers.add(new Checker(j+(SquareSize/2), i+(SquareSize/2), SquareSize*0.4, row, column));
                        this.white_checkers.get(checkerid).setFill(Color.WHITE);
                        if (player == Player.ONE) {
                            this.white_checkers.get(checkerid).addEventFilter(MouseEvent.MOUSE_PRESSED, filer);
                            this.white_checkers.get(checkerid).addEventFilter(MouseEvent.MOUSE_RELEASED, filer);
                        }
                        Panel.getChildren().add(this.white_checkers.get(checkerid));
                        checkerid++;
                        //System.out.println(Arrays.toString(this.white_checkers));
                    }
                }
                this.Pola[column][row].toBack();
                column++;
            }
            column = 0;
            row++;
            if (row == white_position) {
                checkerid = 0;
            }
        }
        //System.out.println(Arrays.toString(this.white_checkers));
        //System.out.println(Arrays.toString(this.red_checkers));
        //red_checkers[0].Move(7,7);
        root.setCenter(Panel);
        stage.setScene(scene);
        stage.setTitle(String.valueOf(player));
        stage.show();
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        //System.out.println("1");
        arg = Integer.parseInt(args[0]);
        launch();
    }

    @Override
    public void run() {
        System.out.println("4");
        arg = 1;
        //launch();
        while(true) {
            this.receive();
        }
    }

    /**
     * Connects to the server.
     */
    public void listenSocket() {
        try {
            this.socket = new Socket("localhost", 2137);
            this.out = new PrintWriter(socket.getOutputStream(), true);
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (UnknownHostException e) {
            System.out.println("Unknown host: localhost");
            System.exit(1);
        } catch (IOException e) {
            System.out.println("No I/O");
            System.exit(1);
        }
    }

    /**
     * "handshake" with the server
     */
    private void receiveInitFromServer() {
        try {
            if (Objects.equals(in.readLine(), "1")){
                player = Player.ONE;
            }
            else {
                player = Player.TWO;
            }
            if (player == Player.ONE) {
                System.out.println("My Turn");
            } else {
                System.out.println("Opposite turn");
            }
        } catch (IOException e) {
            System.out.println("Read failed");
            System.exit(1);
        }
    }

    private void startThread() {
        Thread thread = new Thread(this);
        thread.start();
    }

    private void receive(){
        try {
            //System.out.println(Arrays.toString(this.white_checkers));
            //System.out.println(Arrays.toString(this.red_checkers));
            Translator translator = new Translator();
            String message = in.readLine();
            //System.out.println(str);
            int [] decodedMessage = translator.translateCoords(message);
            System.out.println(Arrays.toString(decodedMessage));
            if(decodedMessage[5] != -1) {
                if (decodedMessage[4] == 2) {
                    this.red_checkers.get(this.findPiece(decodedMessage[0], decodedMessage[1], decodedMessage[4])).Move(decodedMessage[2], decodedMessage[3]);
                    if (decodedMessage[5] == 1) {
                        this.white_checkers.get(this.findPiece((decodedMessage[2] + decodedMessage[0])/2, (decodedMessage[3] + decodedMessage[1])/2, 1)).Move(100,100);
                    }
                } else {
                    this.white_checkers.get(this.findPiece(decodedMessage[0], decodedMessage[1], decodedMessage[4])).Move(decodedMessage[2], decodedMessage[3]);
                    if (decodedMessage[5] == 1) {
                        this.red_checkers.get(this.findPiece((decodedMessage[2] + decodedMessage[0])/2, (decodedMessage[3] + decodedMessage[1])/2, 2)).Move(100, 100);
                    }
                }
            }
        }
        catch (IOException e) {
            System.out.println("Read failed"); System.exit(1);}
    }

    @Override
    public void init(){
        //System.out.println("2");
        this.listenSocket();
        this.receiveInitFromServer();
    }

    private int findPiece(int x, int y, int pieceKind){
        //System.out.println(Arrays.toString(this.white_checkers));
        //System.out.println(Arrays.toString(this.red_checkers));
        //System.out.println(this.white_checkers.length);
        //System.out.println(this.red_checkers.length);
        int id = -1;
        if (pieceKind == 2){
            for (int i = 0; i < this.red_checkers.size(); i++) {
                //System.out.println(i);
                if(this.red_checkers.get(i).getColumn() == x && this.red_checkers.get(i).getRow() == y){
                    id = i;
                    break;
                }
            }
        }
        else {
            for (int i = 0; i < this.white_checkers.size(); i++) {
                //System.out.println(i);
                if(white_checkers.get(i).getColumn() == x && this.white_checkers.get(i).getRow() == y){
                    id = i;
                    break;
                }
            }
        }
        return id;
    }
}