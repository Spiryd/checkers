package org.checkers.model;

public class NoSuchPieceException extends Exception{
    public NoSuchPieceException(){
        System.out.println("No such piece");
    }
}
