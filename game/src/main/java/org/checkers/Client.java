package org.checkers;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class Client extends Frame implements ActionListener, Runnable {
    Label msg;
    Label output;
    Button send;

    TextField input;
    Socket socket = null;
    PrintWriter out = null;
    BufferedReader in = null;

    private int player;

    public final static int PLAYER1 = 1;
    public final static int PLAYER2 = 2;

    public final static int ACTIVE = 0;
    public final static int NONACTIVE = 1;
    private  static int actualPlayer = PLAYER1;

    private static int showing = ACTIVE;

    Client() {
        setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 40));
        msg = new Label("Status");
        input = new TextField(20);
        output = new Label();
        output.setBackground(Color.white);
        send = new Button("Send");
        send.addActionListener(this);
        setLayout(new GridLayout(4, 1));
        add(msg);
        add(input);
        add(send);
        add(output);
    }

    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == send) {
            send();
        }
    }

    private void send(){
        // Wysylanie do serwera
        out.println(input.getText());
        msg.setText("OppositeTurn");
        send.setEnabled(false);
        input.setText("");
        input.requestFocus();
        showing = ACTIVE;
        actualPlayer = player;
    }

    private void receive(){
        try {
            // Odbieranie z serwera
            String str = in.readLine();
            output.setText(str);
            msg.setText("My turn");
            send.setEnabled(true);
            input.setText("");
            input.requestFocus();
        }
        catch (IOException e) {
            System.out.println("Read failed"); System.exit(1);}
    }

    public void listenSocket() {
        try {
            socket = new Socket("localhost", 2137);
            // Inicjalizacja wysylania do serwera
            out = new PrintWriter(socket.getOutputStream(), true);
            // Inicjalizacja odbierania z serwera
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
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
            player = Integer.parseInt(in.readLine());
            if (player== PLAYER1) {
                msg.setText("My Turn");
            } else {
                msg.setText("Opposite turn");
                send.setEnabled(false);
            }
        } catch (IOException e) {
            System.out.println("Read failed");
            System.exit(1);
        }
    }

    public static void main(String[] args) {
        Client frame = new Client();
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        frame.pack();
        frame.setVisible(true);
        frame.listenSocket();
        frame.receiveInitFromServer();
        frame.startThread();
    }

    private void startThread() {
        Thread gTh = new Thread(this);
        gTh.start();
    }

    @Override
    public void run() {
        if (player==PLAYER1) {
            f(PLAYER1);
        }
        else{
            f(PLAYER2);
        }

    }

    void f(int iPlayer){
        while(true) {
            synchronized (this) {
                if (actualPlayer== iPlayer) {
                    try {
                        wait(10);
                    } catch (InterruptedException e) {
                    }
                }
                if (showing ==ACTIVE){
                    receive();
                    showing =NONACTIVE;
                }
                notifyAll();
            }
        }
    }

}