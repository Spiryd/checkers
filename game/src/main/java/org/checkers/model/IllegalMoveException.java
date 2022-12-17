package org.checkers.model;

public class IllegalMoveException extends Exception{
    public IllegalMoveException(){
        System.out.println("Illegal move");
    }
}
