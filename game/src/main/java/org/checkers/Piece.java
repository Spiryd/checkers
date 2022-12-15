package org.checkers;

enum State{
    MAN,
    KING
}

public class Piece {
    private int posX;
    private int posY;

    private State state = State.MAN;

    public Piece(int posX, int posY){
        this.posX = posX;
        this.posY = posY;
    }
}
