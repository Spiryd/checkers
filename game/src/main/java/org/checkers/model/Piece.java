package org.checkers.model;


public class Piece {
    private int posX;
    private int posY;
    private Color color;
    private State state;

    public Piece(int posX, int posY, Color color){
        this.posX = posX;
        this.posY = posY;
        this.color = color;
        this.state = State.MAN;
    }

    public void setCoords(int posX, int posY){
        this.posX = posX;
        this.posY = posY;
    }

    public void promote(){
        this.state = State.KING;
    }

    public State getState(){
        return this.state;
    }

    public int getPosX(){
        return this.posX;
    }

    public Color getColor() {
        return this.color;
    }

    public int getPosY(){
        return this.posY;
    }
}
