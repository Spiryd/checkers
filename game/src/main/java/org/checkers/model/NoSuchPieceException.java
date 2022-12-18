package org.checkers.model;

public class NoSuchPieceException extends Exception{
    public NoSuchPieceException(){
        System.err.println("No such piece");
        System.err.println("Something went wrong");
        System.exit(1);
    }
}
