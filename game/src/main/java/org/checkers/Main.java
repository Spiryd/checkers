package org.checkers;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import org.checkers.State.Activity;
import org.checkers.State.Player;
import org.checkers.ui.Checker;
import org.checkers.ui.Kwadrat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Objects;

public class Main extends Application implements Runnable{

    Socket socket = null;
    PrintWriter out = null;
    BufferedReader in = null;

    Player player;

    Player turn = Player.ONE;

    Activity activity = Activity.ACTIVE;

    Kwadrat[][] Pola;
    Checker[] red_checkers;
    Checker[] white_checkers;

    static int arg = 0;

    @Override
    public void start(Stage stage) {
        initUI(stage);
    }

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
            System.out.println(message);
            out.println(message);
            mouseEvent.consume();
        };

        switch(arg) {
            case 1:
                Pola = new Kwadrat[8][8];
                red_checkers = new Checker[12];
                white_checkers = new Checker[12];
                break;
            case 2:
                Pola = new Kwadrat[10][10];
                red_checkers = new Checker[20];
                white_checkers = new Checker[20];
                SquareSize = dimensions/10;
                red_position = 4;
                white_position = 6;
                break;
            case 3:
                Pola = new Kwadrat[12][12];
                red_checkers = new Checker[24];
                white_checkers = new Checker[24];
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
                Pola[column][row] = new Kwadrat(j, i, SquareSize);
                if ((row+column) % 2 == 0) {
                    Pola[column][row].setFill(Color.GREY);
                    Panel.getChildren().add(Pola[column][row]);
                }
                else {
                    Pola[column][row].setFill(Color.BLACK);
                    Panel.getChildren().add(Pola[column][row]);
                    if(row < red_position) {
                        red_checkers[checkerid] = new Checker(j+(SquareSize/2), i+(SquareSize/2), SquareSize*0.4, row, column);
                        red_checkers[checkerid].setFill(Color.RED);
                        if (player == Player.TWO) {
                            red_checkers[checkerid].addEventFilter(MouseEvent.MOUSE_PRESSED, filer);
                            red_checkers[checkerid].addEventFilter(MouseEvent.MOUSE_RELEASED, filer);
                        }
                        Panel.getChildren().add(red_checkers[checkerid]);
                        checkerid++;
                    }
                    if(row >= white_position) {
                        if (row == white_position) {
                            checkerid = 0;
                        }
                        white_checkers[checkerid] = new Checker(j+(SquareSize/2), i+(SquareSize/2), SquareSize*0.4, row, column);
                        white_checkers[checkerid].setFill(Color.WHITE);
                        if (player == Player.ONE) {
                            white_checkers[checkerid].addEventFilter(MouseEvent.MOUSE_PRESSED, filer);
                            white_checkers[checkerid].addEventFilter(MouseEvent.MOUSE_RELEASED, filer);
                        }
                        Panel.getChildren().add(white_checkers[checkerid]);
                        checkerid++;
                    }
                }
                Pola[column][row].toBack();
                column++;
            }
            column = 0;
            row++;
        }
        //red_checkers[0].Move(7,7);
        root.setCenter(Panel);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        arg = Integer.parseInt(args[0]);
        launch();
    }

    @Override
    public void run() {
        arg = 1;
        launch();
        while(true) {
            synchronized (this) {
                if (turn == player) {
                    receive();
                }
                if (activity == Activity.ACTIVE){
                    receive();
                    activity = Activity.NONACTIVE;
                }
                notifyAll();
            }
        }
    }

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
            Translator translator = new Translator();
            String str = in.readLine();
            System.out.println(str);
            int [] coords = translator.translateCoords(str);
            System.out.println(Arrays.toString(coords));
            if (this.player == Player.TWO) {
                this.red_checkers[findPiece(coords[0], coords[1])].Move(coords[2], coords[3]);
            }
            else {
                this.white_checkers[findPiece(coords[0], coords[1])].Move(coords[2], coords[3]);
            }
        }
        catch (IOException e) {
            System.out.println("Read failed"); System.exit(1);}
    }

    @Override
    public void init(){
        this.listenSocket();
        this.receiveInitFromServer();
        this.startThread();
    }

    private int findPiece(int x, int y){
        int id = -1;
        for (int i = 0; i < red_checkers.length; i++) {
            if(red_checkers[i].getColumn() == x && red_checkers[i].getRow() == y){
                id = i;
                break;
            }
        }
        return id;
    }
}